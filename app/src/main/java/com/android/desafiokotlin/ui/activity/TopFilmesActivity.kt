package com.android.desafiokotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.database.AppDatabase
import com.android.desafiokotlin.database.repository.FilmeRepository
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter
import com.android.desafiokotlin.webclient.FilmeWebClient
import kotlinx.coroutines.launch

class TopFilmesActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListaFilmesAdapter(this)
    }

    private val repository by lazy {
        FilmeRepository(
            AppDatabase.instancia(this).filmeDao(), FilmeWebClient()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_filmes)
        adapter
        configuraRecyclerView()
        lifecycleScope.launch {
            launch {
                buscaFilmes()
            }
            repeatOnLifecycle(Lifecycle.State.STARTED){
                buscaFilmes()
            }
        }
    }

    private fun buscaFilmes() {
        repository.buscaTodos()

    }


    private fun configuraRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_top_filmes_recyclerview)
        recyclerView.adapter = adapter
//        adapter.noClique = { filme ->
//
//        }
    }
}