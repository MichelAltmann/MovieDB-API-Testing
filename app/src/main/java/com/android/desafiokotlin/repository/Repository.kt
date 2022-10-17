package com.android.desafiokotlin.repository

import com.android.desafiokotlin.database.FilmeDAO
import com.android.desafiokotlin.model.Filme

class Repository (private val filmeDAO: FilmeDAO){

    suspend fun buscaTodos() : List<Filme>{
        return filmeDAO.buscaTodos()
    }

    suspend fun remove(filmes: java.util.ArrayList<Filme>) {
        return filmeDAO.remove(filmes)
    }

    suspend fun salva(filmes: java.util.ArrayList<Filme>) {
        return filmeDAO.salva(filmes)
    }

    suspend fun buscaFilme(id: Int?): Any? {
        return filmeDAO.buscaFilme(id)
    }

    suspend fun removeSingular(filme: Filme) {
        return filmeDAO.removeSingular(filme)
    }

    suspend fun salvaSingular(filme: Filme) {
        return filmeDAO.salvaSingular(filme)
    }

}