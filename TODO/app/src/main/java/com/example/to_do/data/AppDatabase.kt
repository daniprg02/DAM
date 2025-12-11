package com.example.to_do.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Anota la clase, especifica las entidades y la versión
@Database(entities = [Tarea::class], version = 1)
// 2. Hazla abstracta y que herede de RoomDatabase
abstract class AppDatabase : RoomDatabase() {
    // 3. Declara una función abstracta para cada DAO que tengas
    abstract fun tareaDao(): TareaDao

    companion object {
        // La anotación @Volatile asegura que el valor de INSTANCE sea siempre el más actualizado
        // y visible para todos los hilos de ejecución.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si INSTANCE no es nula, la devolvemos.
            // Si es nula, creamos la base de datos.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tareas_database" // Nombre del archivo de la base de datos
                ).build()
                INSTANCE = instance
                // Devolvemos la instancia recién creada
                instance
            }
        }
    }
}