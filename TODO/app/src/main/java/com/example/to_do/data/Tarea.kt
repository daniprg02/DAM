package com.example.to_do.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    //definimos la PK y que se autogenere
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descripcion: String,
    val fecha: String
)