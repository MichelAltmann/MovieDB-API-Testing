package com.android.desafiokotlin.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.android.desafiokotlin.R
import com.android.desafiokotlin.database.FavoritoDatabase
import com.android.desafiokotlin.model.Filme
import com.bumptech.glide.Glide

class DetalhesFilmeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_filme)

        var bar : ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#232323")))

        val imagemBackground = findViewById<ImageView>(R.id.activity_detalhes_imagem_do_filme)
        val imagemPoster = findViewById<ImageView>(R.id.activity_detalhes_filme_poster)
        val nome = findViewById<TextView>(R.id.activity_detalhes_nome_do_filme)
        val linguaOriginal = findViewById<TextView>(R.id.activity_detalhes_lingua_original)
        val tituloOriginal = findViewById<TextView>(R.id.activity_detalhes_titulo_original)
        val sinopse = findViewById<TextView>(R.id.activity_detalhes_sinopse)
        val dataDeLancamento = findViewById<TextView>(R.id.activity_detalhes_data_lancamento)
        val popularidade = findViewById<TextView>(R.id.activity_detalhes_popularidade)
        val mediaVotos = findViewById<TextView>(R.id.activity_detalhes_media_de_votos)
        val votos = findViewById<TextView>(R.id.activity_detalhes_quantidade_de_votos)

//        val item : MenuItem = findViewById(R.id.menu_formulario_favorito)


        val filme = intent.getSerializableExtra("filme") as Filme?

//        if (FavoritoDatabase.getInstance(this).favoritoDAO().buscaFilme(filme!!.id) != null){
//            item.icon = resources.getDrawable(R.drawable.ic_baseline_favoritered_24)
//        } else {
//            item.icon = resources.getDrawable(R.drawable.ic_baseline_favorite_border_24)
//        }

        preechendoDadosNaTela(
            filme,
            imagemBackground,
            imagemPoster,
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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.action_menu, menu)
////        val item : MenuItem = findViewById(R.id.menu_formulario_favorito)
////        if (FavoritoDatabase.getInstance(this).favoritoDAO().buscaFilme(filme!!.id) != null){
////            item.icon = resources.getDrawable(R.drawable.ic_baseline_favoritered_24)
////        }
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//            if (item.itemId == R.id.menu_formulario_favorito){
////                if (FavoritoDatabase.getInstance(this).favoritoDAO().buscaFilme(filme!!.id) != null){
////                    item.icon = resources.getDrawable(R.drawable.ic_baseline_favorite_border_24)
////                }
//            }
//        return super.onOptionsItemSelected(item)
//    }

    private fun preechendoDadosNaTela(
        filme: Filme?,
        imagemBackground: ImageView,
        imagemPoster: ImageView,
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
            title = "Detalhes " + filme.title

            Glide.with(imagemBackground).load("https://image.tmdb.org/t/p/w500${filme.backdrop_path}")
                .into(imagemBackground)
            Glide.with(imagemPoster).load("https://image.tmdb.org/t/p/w500${filme.poster_path}")
                .into(imagemPoster)
            nome.text = filme.title

            overviewCheck(filme, sinopse)
            dataCheck(filme, dataDeLancamento)
            linguaOriginalCheck(filme, linguaOriginal)
            tituloOriginalCheck(filme, tituloOriginal)
            mediaVotosCheck(filme, mediaVotos)
            quantidadeDeVotosCheck(filme, votos)

        }
    }

    private fun quantidadeDeVotosCheck(
        filme: Filme,
        votos: TextView
    ) {
        if (filme.vote_count != 0F) {
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
        if (filme.vote_average != 0F) {
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
        if (filme.popularity != 0F) {
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
            when {
                filme.original_language.equals("en") -> linguaOriginal.text = "Inglês"
                filme.original_language.equals("pt") -> linguaOriginal.text = "Português"
                filme.original_language.equals("es") -> linguaOriginal.text = "Espanhol"
                filme.original_language.equals("ja") -> linguaOriginal.text = "Japonês"
                filme.original_language.equals("ko") -> linguaOriginal.text = "Koreano"
                filme.original_language.equals("fr") -> linguaOriginal.text = "Francês"
                else -> {
                    linguaOriginal.text = filme.original_language
                }
            }
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