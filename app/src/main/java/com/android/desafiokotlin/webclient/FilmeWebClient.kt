package com.android.desafiokotlin.webclient

import android.util.Log
import com.android.desafiokotlin.webclient.model.FilmeResposta
import com.android.desafiokotlin.webclient.services.FilmeService

private const val TAG = "FilmeWebClient"

class FilmeWebClient {

    private val filmeService: FilmeService = RetrofitInicializador().filmeService

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
}