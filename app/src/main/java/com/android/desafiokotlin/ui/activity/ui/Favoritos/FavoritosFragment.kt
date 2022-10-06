package com.android.desafiokotlin.ui.activity.ui.Favoritos

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.databinding.FragmentFavoritosBinding
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.ui.activity.DetalhesFilmeActivity
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var filmesFavoritos : ArrayList<Filme> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ListaFilmesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = ListaFilmesAdapter(inflater.context, filmesFavoritos)
        val notificationsViewModel =
            ViewModelProvider(this).get(FavoritosViewModel::class.java)
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("Favoritos") {requestKey, bundle ->
            filmesFavoritos = bundle.getSerializable("filmes") as ArrayList<Filme>
            Log.i(TAG, "onViewCreated: " + filmesFavoritos[1].title)
            recyclerView = binding.activityTopFilmesRecyclerview
            configuraRecyclerView()
            adapter.atualiza(filmesFavoritos)
        }

    }

        private fun configuraRecyclerView() {
            recyclerView.adapter = adapter
            adapter.itemClickListener = {
                val intent = Intent(context, DetalhesFilmeActivity::class.java)
                intent.putExtra("filme", it)
                startActivity(intent)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}