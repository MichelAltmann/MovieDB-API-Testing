package com.android.desafiokotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter

class TopFilmesActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListaFilmesAdapter(this)
    }

    private val repository by lazy {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_filmes)

        configuraRecyclerView()
    }


    private fun configuraRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_top_filmes_recyclerview)
        recyclerView.adapter = adapter
        adapter.noClique = { filme ->

        }
    }
}