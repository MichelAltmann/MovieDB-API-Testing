package com.android.desafiokotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.android.desafiokotlin.model.Filme
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmeDao {

    @Insert(onConflict = REPLACE)
    suspend fun salva(filme: Filme)

    @Insert (onConflict = REPLACE)
    suspend fun salva(filme: List<Filme>)

    @Query ("SELECT * FROM Filme")
    fun buscaTodos() : Flow<List<Filme>>

    @Query ("SELECT * FROM Filme WHERE id = :id")
    fun buscaPorId(id: String) : Flow<Filme>
}