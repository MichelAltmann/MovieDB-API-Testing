package com.android.desafiokotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter
import com.android.desafiokotlin.webclient.FilmeWebClient
import java.io.Serializable

class TopFilmesActivity : AppCompatActivity() {

    private val arrayList: ArrayList<Filme> = arrayListOf()
    private val context: Context = this@TopFilmesActivity
    private val dataSource by lazy {
        FilmeWebClient()
    }

    private val adapter = ListaFilmesAdapter(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_filmes)
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {
                    val response = dataSource.buscaTodos()
                    if (response != null) {
                        arrayList.addAll(response.results!!)
                    }

                } catch (e: Exception) {
                    Log.e("onCreate: ", "Api error $e")
                }
                configuraRecyclerView()
            }
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_top_filmes_recyclerview)
        recyclerView.adapter = adapter
        adapter.atualiza(arrayList)
        adapter.setOnItemClickListener(object : ListaFilmesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(
                    context,
                    "You clicked on $position ${arrayList[position].title}",
                    Toast.LENGTH_SHORT
                ).show()

                var filme: Filme = arrayList[position]


                val intent = Intent(context, DetalhesFilmeActivity::class.java)
                intent.putExtra("filme", filme as Serializable)
                startActivity(intent)
            }
        })
    }
}