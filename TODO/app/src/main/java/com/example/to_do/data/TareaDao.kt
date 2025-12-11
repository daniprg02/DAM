package com.example.to_do.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Insert
    suspend fun insertar(tarea: Tarea)

    @Delete
    suspend fun borrar(tarea: Tarea)

    @Query("SELECT * FROM tareas WHERE descripcion LIKE '%' || :query || '%' ORDER BY id DESC")
    fun obtenerTareas(query: String): Flow<List<Tarea>>
}
