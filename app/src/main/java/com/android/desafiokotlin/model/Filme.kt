package com.android.desafiokotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Filme (
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var nome: String,
    var descricao: String,
    var imagem: String? = null
)

