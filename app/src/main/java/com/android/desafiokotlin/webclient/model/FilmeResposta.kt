package com.android.desafiokotlin.webclient.model

import com.android.desafiokotlin.model.Filme

class FilmeResposta(
//    @SerializedName("page") val pag: Int? para trocar o nome da variável
    val page: Int?,
    val results: List<Filme>?,
    val total_pages: Int?,
    val total_results: Int?
) {
    val resposta: FilmeResposta get() = FilmeResposta(
        page ?: 0,
        results ?: listOf(),
        total_pages ?: 0,
        total_results ?: 0
    )
}