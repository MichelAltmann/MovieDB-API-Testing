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



            var filme = intent.getSerializableExtra("filme") as Filme?
            if (filme != null){
                Glide.with(imagem)
                    .load("https://image.tmdb.org/t/p/w500${filme.backdrop_path}")
                    .into(imagem)
                nome.text = filme.title
            }
    }
}