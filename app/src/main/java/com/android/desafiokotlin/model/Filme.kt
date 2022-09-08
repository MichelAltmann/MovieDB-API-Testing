package com.android.desafiokotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Filme (
    @PrimaryKey
    val id: Int?,
    val title: String?,
    val release_date: String?,
    val backdrop_path: String?
){
    val filme: Filme
        get() = Filme(
            id ?: 0,
            title ?: "",
            release_date ?: "",
            backdrop_path ?: ""
        )
}

