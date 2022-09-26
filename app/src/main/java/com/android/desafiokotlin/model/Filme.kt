package com.android.desafiokotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Filme(
    @PrimaryKey
    val id: Int?,
    val original_language: String?,
    val title: String?,
    val release_date: String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val overview: String?,
    val popularity: Number?,
    val original_title: String?,
    val vote_average: Number?,
    val vote_count: Int?
) : Serializable {
    val filme: Filme
        get() = Filme(
            id ?: 0,
            original_language ?: "",
            title ?: "",
            release_date ?: "",
            backdrop_path ?: "",
            poster_path ?: "",
            overview ?: "",
            popularity ?: 0,
            original_title ?: "",
            vote_average ?: 0,
            vote_count ?: 0
        )
}

