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
import com.android.desafiokotlin.repository.Repository
import com.android.desafiokotlin.ui.activity.ui.Filmes.FilmesFragment
import com.bumptech.glide.Glide

class ListaFilmesAdapter(
    private val context: Context,
    private val tela: String,
    private var favoritos: ArrayList<Filme> = ArrayList(),
    filmes: List<Filme> = emptyList()
) : RecyclerView.Adapter<ListaFilmesAdapter.ViewHolder>() {

    lateinit var itemClickListener : (filme : Filme) -> Unit
    lateinit var onLongItemClickListener : (Boolean) -> Unit
    private val filmes = filmes.toMutableList()
    private val dao = FavoritoDatabase.getInstance(context).favoritoDAO()
    lateinit var favoritoListener : (filme : Filme) -> Boolean
    private var isSelectedMode : Boolean = false
    private val filmesSelecionados : ArrayList<Filme> = ArrayList()

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun vincula(filme: Filme) {

            val (nome, dataLancamento, imagem) = inicializaCampos()

            if (tela.equals("favoritos")){
                nome.visibility = View.GONE
                dataLancamento.visibility = View.GONE
            }
            configuraModoSelecao(filme)

            preencheCampos(imagem, filme, nome, dataLancamento)

        }

        fun preencheCampos(
            imagem: ImageView,
            filme: Filme,
            nome: TextView,
            dataLancamento: TextView
        ) {
            Glide.with(imagem)
                .load("https://image.tmdb.org/t/p/w500${filme.poster_path}")
                .placeholder(loadCircularProgress(imagem.context))
                .into(imagem)
            nome.text = filme.title
            dataLancamento.text = filme.release_date
        }

        private fun configuraModoSelecao(filme: Filme) {
            itemView.rootView.setOnLongClickListener {
                isSelectedMode = true
                onLongItemClickListener.invoke(isSelectedMode)

                selecionaItem()

                populaFilmesSelecionados()

                terminaSelecao()

                notifyItemChanged(adapterPosition)
                true
            }

            itemView.rootView.setOnClickListener {
                if (isSelectedMode) {

                    selecionaItem()

                    populaFilmesSelecionados()

                    terminaSelecao()
                } else {
                    itemClickListener.invoke(filme)
                }

                notifyItemChanged(adapterPosition)
            }
        }

        private fun inicializaCampos(): Triple<TextView, TextView, ImageView> {
            val nome = itemView.findViewById<TextView>(R.id.item_filme_nome)
            val dataLancamento = itemView.findViewById<TextView>(R.id.item_filme_data)
            val imagem = itemView.findViewById<ImageView>(R.id.item_filme_imagem)
            return Triple(nome, dataLancamento, imagem)
        }

        private fun selecionaItem() {
            filmes[adapterPosition].selected = !(filmes[adapterPosition].selected ?: false)
        }

        private fun terminaSelecao() {
            if (filmesSelecionados.size == 0) {
                isSelectedMode = false
                onLongItemClickListener.invoke(isSelectedMode)
            }
        }

        private fun populaFilmesSelecionados() {
            if (filmesSelecionados.contains(filmes.get(adapterPosition))) {
                filmesSelecionados.remove(filmes.get(adapterPosition))
            } else {
                filmesSelecionados.add(filmes.get(adapterPosition))
            }
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
        if (favoritos.contains(filmes[position])){
            imgFav.isVisible = favoritos[position].id != null
        } else {
            imgFav.isVisible = false
        }
    }

    fun pegaFavoritos(): ArrayList<Filme> {
        var listaFilmes : ArrayList<Filme> = arrayListOf()

        limpaSelecao()
        for (i in 0..filmesSelecionados.size - 1){
            listaFilmes.add(filmesSelecionados[i])
        }
        isSelectedMode = false
        filmesSelecionados.clear()
        notifyDataSetChanged()
        return listaFilmes
    }

    private fun limpaSelecao() {
        for (i in 0..filmes.size - 1) {
            filmes[i].selected = false
        }
    }

    fun selecionaFavoritos() {
        filmesSelecionados.clear()
        adicionaSelecao()
        notifyDataSetChanged()
    }

    private fun adicionaSelecao() {
        for (i in 0..filmes.size - 1) {
            filmes[i].selected = true
            filmesSelecionados.add(filmes[i])
        }
    }

    fun cancelaSelecao() {
        limpaSelecao()
        filmesSelecionados.clear()
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

    fun colocaCoracao(filmesFavoritos : ArrayList<Filme>) {
        favoritos.clear()
        favoritos.addAll(filmesFavoritos)
        Log.i(TAG, "colocaCoracao: asfasfasfga")
        notifyDataSetChanged()
    }

}


