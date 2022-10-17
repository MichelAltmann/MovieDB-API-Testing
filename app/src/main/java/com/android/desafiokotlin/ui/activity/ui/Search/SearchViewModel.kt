package com.android.desafiokotlin.ui.activity.ui.Search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.desafiokotlin.webclient.FilmeWebClient
import com.android.desafiokotlin.webclient.model.FilmeResposta
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _filmeResposta = MutableLiveData<FilmeResposta>()
    val filmeResposta : LiveData<FilmeResposta>
        get() = _filmeResposta


    private var currentPage = 1
    var filtro = ""

    fun getBuscaFilmesApi() = viewModelScope.launch{
        try {
            val response = FilmeWebClient().buscaFilter(currentPage, filtro)
            response?.apply {
                _filmeResposta.postValue(this)
                currentPage++
            }

        } catch (e: Exception) {
            Log.e("onCreate: ", "Api error $e")
        }
    }

}