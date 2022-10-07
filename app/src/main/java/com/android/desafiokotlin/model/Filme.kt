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
    val popularity: Float?,
    val original_title: String?,
    val vote_average: Float?,
    val vote_count: Float?,
    var selected: Boolean?
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
            popularity ?: 0F,
            original_title ?: "",
            vote_average ?: 0F,
            vote_count ?: 0F,
        selected ?: false
        )
}

