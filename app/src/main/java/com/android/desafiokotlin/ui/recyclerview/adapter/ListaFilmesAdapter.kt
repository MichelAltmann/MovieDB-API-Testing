package com.android.desafiokotlin.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.model.Filme
import com.bumptech.glide.Glide

class ListaFilmesAdapter(
    private val context: Context,
    filmes: List<Filme> = emptyList()
) : RecyclerView.Adapter<ListaFilmesAdapter.ViewHolder>() {

    private lateinit var mListener : OnItemClickListener
    private val filmes = filmes.toMutableList()

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){

        mListener = listener

    }

    inner class ViewHolder(private val view: View, listener: OnItemClickListener): RecyclerView.ViewHolder(view){

        init {

            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)

            }

        }


        fun vincula(filme: Filme) {
            val nome = itemView.findViewById<TextView>(R.id.item_filme_nome)
            val descricao = itemView.findViewById<TextView>(R.id.item_filme_data)
            val imagem = itemView.findViewById<ImageView>(R.id.item_filme_imagem)

           Glide.with(imagem)
               .load("https://image.tmdb.org/t/p/w500${filme.backdrop_path}")
               .into(imagem)

            nome.text = filme.title
            descricao.text = filme.release_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_filme, parent, false)
        return ViewHolder(view,mListener)
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

}
