package com.android.desafiokotlin.ui.activity.ui.Filmes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.webclient.FilmeWebClient
import com.android.desafiokotlin.webclient.model.FilmeResposta
import kotlinx.coroutines.launch

class FilmesViewModel : ViewModel() {

    private val _filmeResposta = MutableLiveData<FilmeResposta>()
    val filmeResposta : LiveData<FilmeResposta>
        get() = _filmeResposta

    private var currentPage = 1

    fun getBuscaFilmesApi() = viewModelScope.launch{
        try {
            val response = FilmeWebClient().buscaTodos(currentPage)
            response?.apply {
                _filmeResposta.postValue(this)
                currentPage++
            }


        } catch (e: Exception) {
            Log.e("onCreate: ", "Api error $e")
        }
    }
}