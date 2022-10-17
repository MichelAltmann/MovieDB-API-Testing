package com.android.desafiokotlin.webclient.services

import com.android.desafiokotlin.webclient.model.FilmeResposta
import org.intellij.lang.annotations.Language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmeSearch {

    @GET("search/movie")
    suspend fun buscaFilme(
        @Query("api_key") key : String = "9106a44c761c36bbb02f24c16958a56a",
        @Query("page") page : Int,
        @Query("language") language: String = "pt-BR",
        @Query("query") filter : String
    ): Response<FilmeResposta>
}