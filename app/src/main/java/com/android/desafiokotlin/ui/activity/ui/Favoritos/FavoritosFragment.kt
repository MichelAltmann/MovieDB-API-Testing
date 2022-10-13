package com.android.desafiokotlin.ui.activity.ui.Favoritos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.desafiokotlin.R
import com.android.desafiokotlin.database.FavoritoDatabase
import com.android.desafiokotlin.database.FilmeDAO
import com.android.desafiokotlin.databinding.FragmentFavoritosBinding
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.ui.activity.DetalhesFilmeActivity
import com.android.desafiokotlin.ui.recyclerview.adapter.ListaFilmesAdapter

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Variáveis responsáveis pela animação
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }
    private var clicked = false

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
        binding.navHostActivityMainNenhumFavorito.isVisible = filmesFavoritos.isEmpty()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
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
        var selecionado = 0
        adapter.onLongItemClickListener = { isSelected: Boolean ->
            selecionado++
            if (isSelected) {
                if (selecionado == 1) modoSelecao()
                binding.navHostActivityMainFabDeletaFavorito.setOnClickListener {
                    filmesFavoritos = adapter.pegaFavoritos()
                    dao.remove(filmesFavoritos)
                    adapter.atualiza(dao.buscaTodos())
                    selecionado = 0
                    modoSelecao()
                }
                binding.navHostActivityMainFabSelecionaFavoritos.setOnClickListener {
                    adapter.selecionaFavoritos()
                }
                binding.navHostActivityMainFabCancelarSelecao.setOnClickListener {
                    adapter.cancelaSelecao()
                    modoSelecao()
                    selecionado = 0
                }
            } else if (!isSelected) {
                selecionado = 0
                modoSelecao()
            }
        }
    }

    //  Funções para criação da animação
    private fun modoSelecao() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked : Boolean) {
        if (!clicked){
            apareceFabs()
        } else{
            escondeFabs()
        }
    }

    private fun escondeFabs() {
        binding.navHostActivityMainFabCancelarSelecao.startAnimation(toBottom)
        binding.navHostActivityMainFabSelecionaFavoritos.startAnimation(toBottom)
        binding.navHostActivityMainFabDeletaFavorito.startAnimation(toBottom)
        binding.navHostActivityMainFabCancelarSelecao.isClickable = false
        binding.navHostActivityMainFabSelecionaFavoritos.isClickable = false
        binding.navHostActivityMainFabDeletaFavorito.isClickable = false
    }

    private fun apareceFabs() {
        binding.navHostActivityMainFabCancelarSelecao.startAnimation(fromBottom)
        binding.navHostActivityMainFabSelecionaFavoritos.startAnimation(fromBottom)
        binding.navHostActivityMainFabDeletaFavorito.startAnimation(fromBottom)
        binding.navHostActivityMainFabCancelarSelecao.isClickable = true
        binding.navHostActivityMainFabSelecionaFavoritos.isClickable = true
        binding.navHostActivityMainFabDeletaFavorito.isClickable = true
    }

    private fun setVisibility(clicked : Boolean) {
        if(!clicked){
            binding.navHostActivityMainFabCancelarSelecao.visibility = View.VISIBLE
            binding.navHostActivityMainFabSelecionaFavoritos.visibility = View.VISIBLE
            binding.navHostActivityMainFabDeletaFavorito.visibility = View.VISIBLE
        } else {
            binding.navHostActivityMainFabCancelarSelecao.visibility = View.GONE
            binding.navHostActivityMainFabSelecionaFavoritos.visibility = View.GONE
            binding.navHostActivityMainFabDeletaFavorito.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}