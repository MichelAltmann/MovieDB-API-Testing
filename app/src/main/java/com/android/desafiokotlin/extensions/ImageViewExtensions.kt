package com.android.desafiokotlin.extensions

import android.widget.ImageView
import coil.load
import com.android.desafiokotlin.R

fun ImageView.  tentaCarregarImagem(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
) {
    load(url) {
        error(com.google.android.material.R.drawable.mtrl_ic_error)
        fallback(fallback)
    }
}