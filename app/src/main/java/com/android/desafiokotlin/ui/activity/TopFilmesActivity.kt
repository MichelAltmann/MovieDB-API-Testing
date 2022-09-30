package com.android.desafiokotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter
import com.android.desafiokotlin.webclient.FilmeWebClient
import kotlinx.coroutines.launch

class TopFilmesActivity : AppCompatActivity() {

    private val arrayList: ArrayList<Filme> = arrayListOf()
    private val context: Context = this@TopFilmesActivity
    private val dataSource by lazy {
        FilmeWebClient()
    }
    private lateinit var recyclerView: RecyclerView
    private var page = 1
    private val adapter = ListaFilmesAdapter(this, arrayList)
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Filmes"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_filmes)
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                recyclerView = findViewById<RecyclerView>(R.id.activity_top_filmes_recyclerview)
                buscaFilmesComPaginacao()
                configuraRecyclerView()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        removeScrollListenerAdapter()
        addScrollListenerAdapter()
    }

    override fun onPause() {
        super.onPause()
        removeScrollListenerAdapter()
    }

    private fun addScrollListenerAdapter() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount = layoutManager.childCount
                    val totalItemVisible = visibleItemCount + pastVisibleItems
                    val totalItemCount = layoutManager.itemCount
                    if (totalItemVisible >= totalItemCount) {
                        lifecycleScope.launch {
                            removeScrollListenerAdapter()
                            buscaFilmesComPaginacao()
                        }
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun removeScrollListenerAdapter() {
        if (::scrollListener.isInitialized) {
            recyclerView.removeOnScrollListener(scrollListener)
        }
    }

    private suspend fun buscaFilmesComPaginacao() {
        try {
            val response = dataSource.buscaTodos(page)
            if (response != null) {
                arrayList.addAll(response.results!!)
                adapter.atualiza(arrayList)
                page++
                removeScrollListenerAdapter()
                addScrollListenerAdapter()
            }

        } catch (e: Exception) {
            Log.e("onCreate: ", "Api error $e")
        }
    }


    private fun configuraRecyclerView() {
        recyclerView
        recyclerView.adapter = adapter
        adapter.itemClickListener = {
            val intent = Intent(context, DetalhesFilmeActivity::class.java)
            intent.putExtra("filme", it)
            startActivity(intent)
        }
    }
}