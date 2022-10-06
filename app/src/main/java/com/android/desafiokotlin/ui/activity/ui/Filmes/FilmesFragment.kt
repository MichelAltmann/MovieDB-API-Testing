package com.android.desafiokotlin.ui.activity.ui.Filmes

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.databinding.FragmentFilmesBinding
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.ui.activity.DetalhesFilmeActivity
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter
import com.android.desafiokotlin.webclient.FilmeWebClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class FilmesFragment : Fragment() {

    private var _binding: FragmentFilmesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val arrayList: ArrayList<Filme> = arrayListOf()
    private var filmesFavoritos : ArrayList<Filme> = arrayListOf()
    private val dataSource by lazy {
        FilmeWebClient()
    }
    private lateinit var recyclerView: RecyclerView
    private var page = 1
    private lateinit var adapter : ListaFilmesAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(FilmesViewModel::class.java)
        adapter = ListaFilmesAdapter(inflater.context, arrayList)
        _binding = FragmentFilmesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
                recyclerView = binding.activityTopFilmesRecyclerview
                buscaFilmesComPaginacao()
                configuraRecyclerView()
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

        adapter.onLongItemClickListener = { isSelected: Boolean ->
            val fab : FloatingActionButton = binding.navHostActivityMainFabFavorito
            if (isSelected){
                fab.isClickable = true
                fab.visibility = View.VISIBLE
                fab.setOnClickListener {
                    Toast.makeText(context, "Feitoooo", Toast.LENGTH_SHORT).show()
                    filmesFavoritos = adapter.clearSelections()
                    Log.i(TAG, "configuraRecyclerView: " + filmesFavoritos[1].title)
                    fab.isClickable = false
                    fab.visibility = View.GONE
                }
            } else if (!isSelected) {
                fab.isClickable = false
                fab.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}