//package com.example.preferencias
//
//import android.content.Context
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//// Instancia única de DataStore
//val Context.dataStore by preferencesDataStore("settings")
//
//object SettingsPreferences {
//    private val COLOR_MODE = booleanPreferencesKey("color_mode")
//
//    fun getColorMode(context: Context): Flow<Boolean> =
//        context.dataStore.data.map { prefs -> prefs[COLOR_MODE] ?: false }
//
//    suspend fun setColorMode(context: Context, value: Boolean) {
//        context.dataStore.edit { prefs ->
//            prefs[COLOR_MODE] = value
//        }
//    }
//}
package com.example.preferencias

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

object ColorPreferences {
    private val SELECTED_COLOR = stringPreferencesKey("selected_color")

    fun getColor(context: Context): Flow<String> =
        context.dataStore.data.map { prefs -> prefs[SELECTED_COLOR] ?: "Rojo" } // valor por defecto

    suspend fun setColor(context: Context, color: String) {
        context.dataStore.edit { prefs ->
            prefs[SELECTED_COLOR] = color
        }
    }
}

