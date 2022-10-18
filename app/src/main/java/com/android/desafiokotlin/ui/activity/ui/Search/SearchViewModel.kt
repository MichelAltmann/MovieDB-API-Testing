package com.android.desafiokotlin.ui.activity.ui.Search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.webclient.FilmeWebClient
import com.android.desafiokotlin.webclient.model.FilmeResposta
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _filmeResposta = MutableLiveData<ArrayList<Filme>>()
    val filmeResposta: LiveData<ArrayList<Filme>>
        get() = _filmeResposta

    private val arrayList: ArrayList<Filme> = arrayListOf()

    private var currentPage = 1
    var filtro = " "
        set(value) {
            field = value
            currentPage = 1
            arrayList.clear()
            getBuscaFilmesApi()
        }

    private var jobBuscarFilme : Job? = null

    fun getBuscaFilmesApi() {
        jobBuscarFilme?.cancel()
        jobBuscarFilme = viewModelScope.launch {
//            delay(100)
            try {
                val response = FilmeWebClient().buscaFilter(currentPage, filtro)
                response?.apply {
                    arrayList.addAll(this.results!!)
                    _filmeResposta.postValue(arrayList)
                    currentPage++
                }

            } catch (e: Exception) {
                Log.e("onCreate: ", "Api error $e")
            }
        }
    }
}