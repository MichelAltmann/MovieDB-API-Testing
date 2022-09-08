package com.android.desafiokotlin.webclient.services

import com.android.desafiokotlin.webclient.model.FilmeResposta
import retrofit2.http.GET

interface FilmeService {
    @GET("filmes")
    suspend fun buscaTodos(): List<FilmeResposta>
}