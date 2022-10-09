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
    fun buscaTodos() : List<Filme>

    @Query("SELECT * FROM Filme WHERE id = :id")
    fun buscaFilme(id: Int?) : Filme

    @Insert(onConflict = REPLACE)
    fun salva(filme : List<Filme>)

    @Delete
    fun remove(filme : List<Filme>)

}
