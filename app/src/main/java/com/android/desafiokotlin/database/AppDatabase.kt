package com.android.desafiokotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.desafiokotlin.database.dao.FilmeDao
import com.android.desafiokotlin.model.Filme

@Database(
    version = 1,
    entities = [Filme::class],
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun filmeDao(): FilmeDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "DesafioKotlin.db"
            ).addMigrations(MIGRATION_1_2).build()
        }
    }

}