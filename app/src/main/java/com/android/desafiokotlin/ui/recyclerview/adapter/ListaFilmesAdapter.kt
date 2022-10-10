package com.android.desafiokotlin.ui.recyclerview.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.desafiokotlin.R
import com.android.desafiokotlin.database.FavoritoDatabase
import com.android.desafiokotlin.model.Filme
import com.bumptech.glide.Glide

class ListaFilmesAdapter(
    private val context: Context,
    private val tela: String,
    filmes: List<Filme> = emptyList()
) : RecyclerView.Adapter<ListaFilmesAdapter.ViewHolder>() {

    lateinit var itemClickListener : (filme : Filme) -> Unit
    lateinit var onLongItemClickListener : (Boolean) -> Unit
    private val filmes = filmes.toMutableList()
    private val filmesFavoritos = FavoritoDatabase.getInstance(context).favoritoDAO().buscaTodos()
    private val dao = FavoritoDatabase.getInstance(context).favoritoDAO()
    private var isSelectedMode : Boolean = false
    private val filmesSelecionados : ArrayList<Filme> = ArrayList()

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun vincula(filme: Filme) {
            var nome : TextView
            var dataLancamento : TextView
            var imagem : ImageView

                 nome = itemView.findViewById(R.id.item_filme_nome)
                 dataLancamento = itemView.findViewById(R.id.item_filme_data)
                 imagem = itemView.findViewById(R.id.item_filme_imagem)
            if (tela.equals("favoritos")){
                nome.visibility = View.GONE
                dataLancamento.visibility = View.GONE
            }
            itemView.rootView.setOnLongClickListener{
                isSelectedMode = true
                onLongItemClickListener.invoke(isSelectedMode)

                filmes[adapterPosition].selected = !(filmes[adapterPosition].selected ?: false)

                if (filmesSelecionados.contains(filmes.get(adapterPosition))){
                    filmesSelecionados.remove(filmes.get(adapterPosition))
                } else {
                    filmesSelecionados.add(filmes.get(adapterPosition))
                }



                if (filmesSelecionados.size == 0){
                    isSelectedMode = false
                    onLongItemClickListener.invoke(isSelectedMode)
                }
                notifyItemChanged(adapterPosition)
                true
            }

            itemView.rootView.setOnClickListener{
                if (isSelectedMode){

                    filmes[adapterPosition].selected = !(filmes[adapterPosition].selected ?: false)

                    if (filmesSelecionados.contains(filmes.get(adapterPosition))){
                        filmesSelecionados.remove(filmes.get(adapterPosition))
                    } else {
                        filmesSelecionados.add(filmes.get(adapterPosition))
                    }

                    if (filmesSelecionados.size == 0){
                        isSelectedMode = false
                        onLongItemClickListener.invoke(isSelectedMode)
                    }
                } else{
                    itemClickListener.invoke(filme)
                }
                notifyItemChanged(adapterPosition)
            }

           Glide.with(imagem)
               .load("https://image.tmdb.org/t/p/w500${filme.poster_path}")
               .placeholder(loadCircularProgress(imagem.context))
               .into(imagem)
            nome.text = filme.title
            dataLancamento.text = filme.release_date

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_filme, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = filmes[position]
        holder.vincula(filme)
        holder.itemView.isSelected = filme.selected == true

            var imgFav = holder.itemView.findViewById<ImageView>(R.id.item_filme_favorito)
            imgFav.isVisible = dao.buscaFilme(filmes[position].id) != null
    }

    fun pegaFavoritos(): ArrayList<Filme> {
        var listaFilmes : ArrayList<Filme> = arrayListOf()
        for(i in 0..filmes.size - 1){
            filmes[i].selected = false
        }
        for (i in 0..filmesSelecionados.size - 1){
            listaFilmes.add(filmesSelecionados[i])
        }
        isSelectedMode = false
        filmesSelecionados.clear()
        notifyDataSetChanged()
        return listaFilmes
    }

    fun selecionaFavoritos() {
        filmesSelecionados.clear()
        for(i in 0..filmes.size - 1){
            filmes[i].selected = true
            filmesSelecionados.add(filmes[i])
        }
        notifyDataSetChanged()
    }

    fun cancelaSelecao() {
        for (i in 0..filmes.size - 1){
            filmes[i].selected = false
            filmesSelecionados.clear()
        }
        isSelectedMode = false
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filmes.size
    }

    fun atualiza(filmes: List<Filme>){
        this.filmes.clear()
        this.filmes.addAll(filmes)
        notifyDataSetChanged()
    }

    private fun loadCircularProgress(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        val colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            ContextCompat.getColor(context, R.color.nav_bar_background),
            BlendModeCompat.SRC_ATOP
        )

        circularProgressDrawable.colorFilter = colorFilter
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 60f
        circularProgressDrawable.start()

        return circularProgressDrawable
    }

}


