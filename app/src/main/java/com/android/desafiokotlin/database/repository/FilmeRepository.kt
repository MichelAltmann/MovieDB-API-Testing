package com.android.desafiokotlin.database.repository

import com.android.desafiokotlin.database.dao.FilmeDao
import com.android.desafiokotlin.model.Filme
import com.android.desafiokotlin.webclient.FilmeWebClient
import kotlinx.coroutines.flow.Flow

class FilmeRepository(
    private val dao: FilmeDao,
    private val webClient: FilmeWebClient
) {
    fun buscaTodos(): Flow<List<Filme>> {
        return dao.buscaTodos()
    }
}