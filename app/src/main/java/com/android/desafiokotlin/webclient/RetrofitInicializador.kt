package com.android.desafiokotlin.webclient

import com.android.desafiokotlin.webclient.services.FilmeService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInicializador {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val filmeService = retrofit.create(FilmeService::class.java)
}