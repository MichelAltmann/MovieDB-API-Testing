package com.android.desafiokotlin.webclient

import android.util.Log
import com.android.desafiokotlin.webclient.model.FilmeResposta
import com.android.desafiokotlin.webclient.services.FilmeSearch
import com.android.desafiokotlin.webclient.services.FilmeService
import java.util.logging.Filter

private const val TAG = "FilmeWebClient"

class FilmeWebClient {

    private val filmeService: FilmeService = RetrofitInicializador().filmeService
    private val filmeFilter : FilmeSearch = RetrofitInicializador().filmeSearch

    suspend fun buscaTodos(page: Int): FilmeResposta? {
        return try {
            val response = filmeService.buscaTodos(page = page)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "BuscaTodos: ", e)
            null
        }
    }

    suspend fun buscaFilter(page: Int, filter : String) : FilmeResposta?{
        return try {
            val response = filmeFilter.buscaFilme(page = page, filter = filter)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "BuscaFiltro: ", e)
            null
        }
    }
}