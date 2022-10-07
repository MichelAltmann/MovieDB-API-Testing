package com.android.desafiokotlin.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class FavoritoMigrations {
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL("CREATE TABLE IF NOT EXISTS `Filme_novo` (" +
                    "`id` INTEGER," +
                    " `original_language` TEXT," +
                    " `title` TEXT," +
                    " `release_date` TEXT," +
                    " `backdrop_path` TEXT," +
                    " `poster_path` TEXT," +
                    " `overview` TEXT," +
                    " `popularity` REAL," +
                    " `original_title` TEXT," +
                    " `vote_average` REAL," +
                    " `vote_count` REAL," +
                    " `selected` INTEGER," +
                    " PRIMARY KEY(`id`))");

            database.execSQL("DROP TABLE Filme");

            database.execSQL("ALTER TABLE Filme_novo RENAME TO Filme");
        }
    }
    val TODAS_MIGRATIONS : List<Migration> = listOf(MIGRATION_1_2)
}