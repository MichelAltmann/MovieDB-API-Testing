package com.android.desafiokotlin.webclient

import com.android.desafiokotlin.webclient.services.FilmeSearch
import com.android.desafiokotlin.webclient.services.FilmeService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInicializador {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val filmeService = retrofit.create(FilmeService::class.java)
    val filmeSearch = retrofit.create(FilmeSearch::class.java)
}