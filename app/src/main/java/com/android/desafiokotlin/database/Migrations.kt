package com.android.desafiokotlin.database

import android.annotation.SuppressLint
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

val MIGRATION_1_2 = object : Migration(1, 2) {


    @SuppressLint("Range")
    override fun migrate(database: SupportSQLiteDatabase) {
        val tabelaNova = "Filme_novo"
        val tabelaAtual = "Filme"

        // crirar uma tabela com todos o dados

        database.execSQL(
            """CREATE TABLE IF NOT EXISTS $tabelaNova (
        `id` TEXT PRIMARY KEY NOT NULL, 
        `nome` TEXT NOT NULL, 
        `descricao` TEXT NOT NULL, 
        `imagem` TEXT)"""
        )

        // copiar dados da tabela atual para a tabela

        database.execSQL(
            """INSERT INTO $tabelaNova (id, nome, descricao, imagem) 
        SELECT id, nome, descricao, imagem FROM $tabelaAtual
    """
        )

        // gerar ids diferentes e novos para a tabela nova

        val cursor = database.query("SELECT * FROM $tabelaNova")
        while (cursor.moveToNext()) {
            val id = cursor.getString(
                cursor.getColumnIndex("id")
            )
            database.execSQL(
                """
        UPDATE $tabelaNova 
            SET id = '${UUID.randomUUID()}' 
            WHERE id = $id"""
            )
        }

        // remover a tabela atual

        database.execSQL("DROP TABLE $tabelaAtual")

        // renomear a tabela nova para o nome da atual

        database.execSQL("ALTER TABLE $tabelaNova RENAME TO $tabelaAtual")

    }
}