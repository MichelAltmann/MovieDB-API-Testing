package com.android.desafiokotlin.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.extensions.tentaCarregarImagem
import com.android.desafiokotlin.model.Filme

class ListaFilmesAdapter(
    private val context: Context,
    var noClique: (filme: Filme) -> Unit = {},
    filmes: List<Filme> = emptyList()
) : RecyclerView.Adapter<ListaFilmesAdapter.ViewHolder>() {

    private val filmes = filmes.toMutableList()

    class ViewHolder(private var view: View,) : RecyclerView.ViewHolder(view) {


        fun vincula(filme: Filme) {

            val nome = itemView.findViewById<TextView>(R.id.item_filme_nome)
            val descricao = itemView.findViewById<TextView>(R.id.item_filme_descricao)
            val imagem = itemView.findViewById<ImageView>(R.id.item_filme_imagem)

            imagem.visibility = if (filme.imagem.isNullOrBlank()){
                GONE
            } else {
                imagem.tentaCarregarImagem(filme.imagem)
                VISIBLE
            }

            nome.text = filme.nome
            descricao.text = filme.descricao
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
}
