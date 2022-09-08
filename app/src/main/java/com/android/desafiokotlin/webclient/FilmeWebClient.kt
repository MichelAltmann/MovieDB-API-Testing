package com.android.desafiokotlin.webclient

import android.util.Log
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.webclient.services.FilmeService

private const val TAG = "FilmeWebClient"

class FilmeWebClient {

    private val filmeService: FilmeService = RetrofitInicializador().filmeService

    suspend fun buscaTodos(): List<Filme>? {
        return try {
            val filmeResposta = filmeService.buscaTodos()
                filmeResposta.map { filmeResposta ->
                filmeResposta.filme
                }
        } catch (e: Exception) {
            Log.e(TAG, "BuscaTodos: ",e)
            null
        }
    }
}