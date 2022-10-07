package com.android.desafiokotlin.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.desafiokotlin.R
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

                if (filmesSelecionados.contains(filmes.get(adapterPosition))){
                    filmes.get(adapterPosition).selected = false
                    filmesSelecionados.remove(filmes.get(adapterPosition))
                } else {
                    filmes.get(adapterPosition).selected = true
                    filmesSelecionados.add(filmes.get(adapterPosition))
                }

                if (filmesSelecionados.size == 0){
                    isSelectedMode = false
                    onLongItemClickListener.invoke(isSelectedMode)
                }
                notifyDataSetChanged()
                true
            }

            itemView.rootView.setOnClickListener{
                if (isSelectedMode){
                    if (filmesSelecionados.contains(filmes.get(adapterPosition))){
                        filmes.get(adapterPosition).selected = false
                        filmesSelecionados.remove(filmes.get(adapterPosition))
                    } else {
                        filmes.get(adapterPosition).selected = true
                        filmesSelecionados.add(filmes.get(adapterPosition))
                    }

                    if (filmesSelecionados.size == 0){
                        isSelectedMode = false
                        onLongItemClickListener.invoke(isSelectedMode)
                    }
                } else{
                    itemClickListener.invoke(filme)
                }
                notifyDataSetChanged()
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
        if (filme.selected == true){
            holder.itemView.isSelected = true
        } else if (filme.selected == false){
            holder.itemView.isSelected = false
        }
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


