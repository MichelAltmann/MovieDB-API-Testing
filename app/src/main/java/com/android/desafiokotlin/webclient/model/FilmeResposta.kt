package com.android.desafiokotlin.webclient.model

import com.android.desafiokotlin.model.Filme
import java.util.*

class FilmeResposta(
    val id: String?,
    val nome: String?,
    val descricao: String?,
    val imagem: String?
) {
    val filme: Filme get() = Filme(
        id = id ?: UUID.randomUUID().toString(),
        nome = nome ?: "",
        descricao = descricao ?: "",
        imagem = imagem ?: ""
    )
}