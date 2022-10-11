package com.android.desafiokotlin.ui.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.desafiokotlin.R
import com.android.desafiokotlin.database.FavoritoDatabase
import com.android.desafiokotlin.model.Filme
import com.bumptech.glide.Glide
import kotlin.math.roundToInt


class DetalhesFilmeActivity : AppCompatActivity() {

    private val fromFilled : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_filled)}
    private val toFilled : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_filled)}
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_filme)

        val btnFavorito = findViewById<ImageView>(R.id.activity_detalhes_btnFavorito)
        val imagemBackground = findViewById<ImageView>(R.id.activity_detalhes_imagem_do_filme)
        val imagemPoster = findViewById<ImageView>(R.id.activity_detalhes_filme_poster)
        val nome = findViewById<TextView>(R.id.activity_detalhes_nome_do_filme)
        val linguaOriginal = findViewById<TextView>(R.id.activity_detalhes_lingua_original)
        val tituloOriginal = findViewById<TextView>(R.id.activity_detalhes_titulo_original)
        val sinopse = findViewById<TextView>(R.id.activity_detalhes_sinopse)
        val dataDeLancamento = findViewById<TextView>(R.id.activity_detalhes_data_lancamento)
//        val popularidade = findViewById<TextView>(R.id.activity_detalhes_popularidade)
        val votos = findViewById<TextView>(R.id.activity_detalhes_quantidade_de_votos)
        val rating = findViewById<RatingBar>(R.id.activity_detalhes_ratingBar)

        var bar : ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#232323")))
        val dao = FavoritoDatabase.getInstance(this).favoritoDAO()



//        val item : MenuItem = findViewById(R.id.menu_formulario_favorito)


        val filme = intent.getSerializableExtra("filme") as Filme?

        if (filme != null) {
            if (dao.buscaFilme(filme.id) != null){
                clique()
            }
        }

        btnFavorito.setOnClickListener {
            if (filme != null) {
                if (dao.buscaFilme(filme.id) != null){
                    dao.removeSingular(filme)
                    clique()
                    Toast.makeText(this, "Filme removido dos favoritos.", Toast.LENGTH_SHORT).show()
                } else {
                    dao.salvaSingular(filme)
                    clique()
                    Toast.makeText(this, "Filme adicionado aos favoritos.", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
//            popularidade,
            votos,
            rating
        )



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

    private fun clique() {
        setAnimation(clicked)
        setVisibility(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked : Boolean) {
        val btnFavorito = findViewById<ImageView>(R.id.activity_detalhes_btnFavorito)
        if (!clicked){
            btnFavorito.startAnimation(toFilled)
        } else{
            btnFavorito.startAnimation(fromFilled)
        }
    }

    private fun setVisibility(clicked : Boolean) {
        val btnFavorito = findViewById<ImageView>(R.id.activity_detalhes_btnFavorito)
        if(!clicked){
            btnFavorito.setImageResource(R.drawable.ic_baseline_favoritered_24)
        } else {
            btnFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun preechendoDadosNaTela(
        filme: Filme?,
        imagemBackground: ImageView,
        imagemPoster: ImageView,
        nome: TextView,
        sinopse: TextView,
        dataDeLancamento: TextView,
        linguaOriginal: TextView,
        tituloOriginal: TextView,
//        popularidade: TextView,
        votos: TextView,
        rating : RatingBar
    ) {
        if (filme != null) {
            title = "Detalhes " + filme.title

            Glide.with(imagemBackground).load("https://image.tmdb.org/t/p/w500${filme.backdrop_path}")
                .placeholder(loadCircularProgress(imagemBackground.context))
                .into(imagemBackground)

            Glide.with(imagemPoster).load("https://image.tmdb.org/t/p/w500${filme.poster_path}")
                .placeholder(loadCircularProgress(imagemPoster.context))
                .into(imagemPoster)
            nome.text = filme.title

            overviewCheck(filme, sinopse)
            dataCheck(filme, dataDeLancamento)
            linguaOriginalCheck(filme, linguaOriginal)
            tituloOriginalCheck(filme, tituloOriginal)
            mediaVotosCheck(filme, rating)
            quantidadeDeVotosCheck(filme, votos)

        }
    }

    private fun quantidadeDeVotosCheck(
        filme: Filme,
        votos: TextView
    ) {
        if (filme.vote_count != 0F) {
            votos.text = (filme.vote_count!!.roundToInt()).toString() + " Votos"
        } else {
            votos.visibility = View.GONE
        }
    }

    private fun mediaVotosCheck(
        filme: Filme,
        rating: RatingBar
    ) {
        if (filme.vote_average != 0F) {
            rating.rating = filme.vote_average!! / 2
        } else {
            rating.rating = 0F
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
                filme.original_language.equals("ru") -> linguaOriginal.text = "Russo"
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