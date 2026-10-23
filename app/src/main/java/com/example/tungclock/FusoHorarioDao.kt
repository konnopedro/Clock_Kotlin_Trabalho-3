
package com.example.tungclock

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FusoHorarioDao {
    @Insert
    suspend fun inserir(fuso: FusoHorario): Long

    @Query("SELECT * FROM fusos_horarios ORDER BY id ASC")
    suspend fun buscarTodos(): List<FusoHorario>

    @Query("SELECT * FROM fusos_horarios WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): FusoHorario?

    @Update
    suspend fun atualizar(fuso: FusoHorario)

    @Delete
    suspend fun excluir(fuso: FusoHorario)
}
