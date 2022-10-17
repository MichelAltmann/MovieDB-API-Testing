package com.android.desafiokotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.android.desafiokotlin.model.Filme

@Dao
interface FilmeDAO {

    @Query("SELECT * FROM Filme")
    suspend fun buscaTodos() : List<Filme>

    @Query("SELECT * FROM Filme WHERE id = :id")
    suspend fun buscaFilme(id: Int?) : Filme?

    @Insert(onConflict = REPLACE)
    suspend fun salva(filme : List<Filme>)

    @Insert(onConflict = REPLACE)
    suspend fun salvaSingular(filme: Filme)

    @Delete
    suspend fun remove(filme : List<Filme>)

    @Delete
    suspend fun removeSingular(filme: Filme)

}
