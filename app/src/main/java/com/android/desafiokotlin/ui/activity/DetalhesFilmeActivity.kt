package com.android.desafiokotlin.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.desafiokotlin.R
import com.android.desafiokotlin.model.Filme
import com.bumptech.glide.Glide

class DetalhesFilmeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_filme)


        val imagem = findViewById<ImageView>(R.id.activity_detalhes_imagem_do_filme)
        val nome = findViewById<TextView>(R.id.activity_detalhes_nome_do_filme)
        val linguaOriginal = findViewById<TextView>(R.id.activity_detalhes_lingua_original)
        val tituloOriginal = findViewById<TextView>(R.id.activity_detalhes_titulo_original)
        val sinopse = findViewById<TextView>(R.id.activity_detalhes_sinopse)
        val dataDeLancamento = findViewById<TextView>(R.id.activity_detalhes_data_lancamento)
        val popularidade = findViewById<TextView>(R.id.activity_detalhes_popularidade)
        val mediaVotos = findViewById<TextView>(R.id.activity_detalhes_media_de_votos)
        val votos = findViewById<TextView>(R.id.activity_detalhes_quantidade_de_votos)


            var filme = intent.getSerializableExtra("filme") as Filme?
        preechendoDadosNaTela(
            filme,
            imagem,
            nome,
            sinopse,
            dataDeLancamento,
            linguaOriginal,
            tituloOriginal,
            popularidade,
            mediaVotos,
            votos
        )
    }

    private fun preechendoDadosNaTela(
        filme: Filme?,
        imagem: ImageView,
        nome: TextView,
        sinopse: TextView,
        dataDeLancamento: TextView,
        linguaOriginal: TextView,
        tituloOriginal: TextView,
        popularidade: TextView,
        mediaVotos: TextView,
        votos: TextView
    ) {
        if (filme != null) {
            Glide.with(imagem)
                .load("https://image.tmdb.org/t/p/w500${filme.backdrop_path}")
                .into(imagem)
            nome.text = filme.title
            overviewCheck(filme, sinopse)
            dataCheck(filme, dataDeLancamento)
            linguaOriginalCheck(filme, linguaOriginal)
            tituloOriginalCheck(filme, tituloOriginal)
            popularidadeCheck(filme, popularidade)
            mediaVotosCheck(filme, mediaVotos)
            quantidadeDeVotosCheck(filme, votos)

        }
    }

    private fun quantidadeDeVotosCheck(
        filme: Filme,
        votos: TextView
    ) {
        if (filme.vote_count != 0) {
            votos.text = filme.vote_count.toString()
        } else {
            votos.visibility = View.GONE
            findViewById<TextView>(R.id.activity_detalhes_quantidade_de_votos_texto).visibility =
                View.GONE
        }
    }

    private fun mediaVotosCheck(
        filme: Filme,
        mediaVotos: TextView
    ) {
        if (filme.vote_average != 0) {
            mediaVotos.text = filme.vote_average.toString()
        } else {
            mediaVotos.visibility = View.GONE
            findViewById<TextView>(R.id.activity_detalhes_media_de_votos_texto).visibility =
                View.GONE
        }
    }

    private fun popularidadeCheck(
        filme: Filme,
        popularidade: TextView
    ) {
        if (filme.popularity != 0) {
            popularidade.text = filme.popularity.toString()
        } else {
            popularidade.visibility = View.GONE
            findViewById<TextView>(R.id.activity_detalhes_popularidade_texto).visibility =
                View.GONE
        }
    }

    private fun tituloOriginalCheck(
        filme: Filme,
        tituloOriginal: TextView
    ) {
        if (filme.original_title != "") {
            tituloOriginal.text = filme.original_title
        } else {
            tituloOriginal.visibility = View.GONE
            findViewById<TextView>(R.id.activity_detalhes_titulo_original_texto).visibility =
                View.GONE
        }
    }

    private fun linguaOriginalCheck(
        filme: Filme,
        linguaOriginal: TextView
    ) {
        if (filme.original_language != "") {
            linguaOriginal.text = filme.original_language
        } else {
            linguaOriginal.visibility = View.GONE
            findViewById<TextView>(R.id.activity_detalhes_lingua_original_texto).visibility =
                View.GONE
        }
    }

    private fun dataCheck(
        filme: Filme,
        dataDeLancamento: TextView
    ) {
        if (filme.release_date != "") {
            dataDeLancamento.text = filme.release_date
        } else {
            dataDeLancamento.visibility = View.GONE
            findViewById<TextView>(R.id.activity_detalhes_data_lancamento_texto).visibility =
                View.GONE
        }
    }

    private fun overviewCheck(
        filme: Filme,
        sinopse: TextView
    ) {
        if (filme.overview != "") {
            sinopse.text = filme.overview
        } else {
            sinopse.visibility = View.GONE
        }
    }
}