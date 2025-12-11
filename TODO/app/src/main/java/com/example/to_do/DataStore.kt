package com.example.to_do

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crea el almacén de datos de la app
val Context.dataStore by preferencesDataStore(name = "ajustes_app")

// Clase para guardar y leer configuraciones
class DataStoreManager(private val contexto: Context) {

    // Claves para identificar cada dato guardado
    private val COLOR_KEY = intPreferencesKey("color_texto")
    private val MODO_OSCURO_KEY = booleanPreferencesKey("modo_oscuro")

    // Guarda el color del texto
    suspend fun guardarColorTexto(color: Int) {
        contexto.dataStore.edit { prefs ->
            prefs[COLOR_KEY] = color
        }
    }

    // Lee el color del texto (negro por defecto)
    val leerColorTexto: Flow<Int> = contexto.dataStore.data.map { prefs ->
        prefs[COLOR_KEY] ?: 0xFF000000.toInt()
    }

    // Guarda si el modo oscuro está activado
    suspend fun guardarModoOscuro(activo: Boolean) {
        contexto.dataStore.edit { prefs ->
            prefs[MODO_OSCURO_KEY] = activo
        }
    }

    // Lee si el modo oscuro está activado (false por defecto)
    val leerModoOscuro: Flow<Boolean> = contexto.dataStore.data.map { prefs ->
        prefs[MODO_OSCURO_KEY] ?: false
    }
}