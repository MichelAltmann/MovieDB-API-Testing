package com.android.desafiokotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.desafiokotlin.database.converter.PopularityConverter
import com.android.desafiokotlin.model.Filme

@Database(
    entities = [Filme::class],
    version = 2,
    exportSchema = false
)
//@TypeConverter({ PopularityConverter.class,VoteAverageConverter.class,VoteCountConverter})
abstract class FavoritoDatabase : RoomDatabase() {

    abstract fun favoritoDAO(): FilmeDAO


    companion object {
        private lateinit var db: FavoritoDatabase
        val MIGRATION_1_2 = object : Migration(1, 2) {

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

        fun getInstance(context: Context) : FavoritoDatabase {
            if (::db.isInitialized) return db
            db = Room.databaseBuilder(
                context,
                FavoritoDatabase::class.java,
                "desafiokotlin.db")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build()
            return db
        }
    }


}