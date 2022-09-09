package com.android.desafiokotlin.ui.activity

import android.os.Bundle
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
            if (filme != null){
                Glide.with(imagem)
                    .load("https://image.tmdb.org/t/p/w500${filme.backdrop_path}")
                    .into(imagem)
                nome.text = filme.title
                linguaOriginal.text = filme.original_language
                tituloOriginal.text = filme.original_title
                sinopse.text = filme.overview
                dataDeLancamento.text = filme.release_date
                popularidade.text = filme.popularity.toString()
                mediaVotos.text = filme.vote_average.toString()
                votos.text = filme.vote_count.toString()
            }
    }
}