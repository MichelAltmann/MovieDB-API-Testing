package com.android.desafiokotlin.webclient.services

import com.android.desafiokotlin.webclient.model.FilmeResposta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmeService {
    @GET("movie/popular")
    suspend fun buscaTodos(
        @Query("api_key") key: String? = "9106a44c761c36bbb02f24c16958a56a",
        @Query("page") page: Int,
        @Query("language") langague: String = "pt-BR"
    ): Response<FilmeResposta>
}