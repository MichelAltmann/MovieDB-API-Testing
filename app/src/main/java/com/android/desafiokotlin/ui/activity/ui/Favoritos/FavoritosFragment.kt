package com.android.desafiokotlin.ui.activity.ui.Favoritos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.database.FavoritoDatabase
import com.android.desafiokotlin.database.FilmeDAO
import com.android.desafiokotlin.databinding.FragmentFavoritosBinding
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.ui.activity.DetalhesFilmeActivity
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var filmesFavoritos : ArrayList<Filme> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ListaFilmesAdapter
    private lateinit var dao : FilmeDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dao = FavoritoDatabase.getInstance(inflater.context).favoritoDAO()
        adapter = ListaFilmesAdapter(inflater.context, "favoritos" ,filmesFavoritos)
        val notificationsViewModel =
            ViewModelProvider(this).get(FavoritosViewModel::class.java)
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            filmesFavoritos = dao.buscaTodos() as ArrayList<Filme>
            recyclerView = binding.activityTopFilmesRecyclerview
            configuraRecyclerView()
            configuraSeleçãoDeFavoritos()
            adapter.atualiza(filmesFavoritos)
    }

        private fun configuraRecyclerView() {
            recyclerView.adapter = adapter
            adapter.itemClickListener = {
                val intent = Intent(context, DetalhesFilmeActivity::class.java)
                intent.putExtra("filme", it)
                startActivity(intent)
            }
        }

    private fun configuraSeleçãoDeFavoritos() {
        adapter.onLongItemClickListener = { isSelected: Boolean ->
            val fab: FloatingActionButton = binding.navHostActivityMainFabDeletaFavorito
            if (isSelected) {
                fab.isClickable = true
                fab.visibility = View.VISIBLE
                fab.setOnClickListener {
                    Toast.makeText(context, "Feitoooo", Toast.LENGTH_SHORT).show()
                    filmesFavoritos = adapter.pegaFavoritos()
                    dao.remove(filmesFavoritos)
                    adapter.atualiza(dao.buscaTodos())
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