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
    filmes: List<Filme> = emptyList()
) : RecyclerView.Adapter<ListaFilmesAdapter.ViewHolder>() {

    lateinit var itemClickListener : (filme : Filme) -> Unit
    private val filmes = filmes.toMutableList()


    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){


        fun vincula(filme: Filme) {
            val nome = itemView.findViewById<TextView>(R.id.item_filme_nome)
            val descricao = itemView.findViewById<TextView>(R.id.item_filme_data)
            val imagem = itemView.findViewById<ImageView>(R.id.item_filme_imagem)

            itemView.rootView.setOnClickListener{
                itemClickListener.invoke(filme)
            }

           Glide.with(imagem)
               .load("https://image.tmdb.org/t/p/w500${filme.poster_path}")
               .placeholder(loadCircularProgress(imagem.context))
               .into(imagem)

            nome.text = filme.title
            descricao.text = filme.release_date
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
            ContextCompat.getColor(context, R.color.background),
            BlendModeCompat.SRC_ATOP
        )

        circularProgressDrawable.colorFilter = colorFilter
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 60f
        circularProgressDrawable.start()

        return circularProgressDrawable
    }

}
