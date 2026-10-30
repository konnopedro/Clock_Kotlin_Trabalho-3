package com.example.tungclock.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "alarmes")
data class Alarme(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val horario: String,
    val nome: String,
    val dias: String,
    var ativo: Boolean
)

@Entity(tableName = "fusos_horarios")
data class FusoHorario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cidade: String,
    val pais: String,
    val zona: String,
    @ColumnInfo(name = "utc_offset")
    val offset: Int
)

