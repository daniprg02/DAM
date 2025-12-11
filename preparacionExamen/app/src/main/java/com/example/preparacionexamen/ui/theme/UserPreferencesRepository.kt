package com.example.preparacionexamen.ui.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// --- Creación del DataStore a Nivel de Archivo ---
// `preferencesDataStore` es una función de extensión que crea una única instancia de DataStore
// para toda la aplicación. Al declararlo aquí, nos aseguramos de que no se creen múltiples
// instancias, lo cual podría causar errores. El `name` "user_preferences" será el nombre
// del archivo físico donde se guardarán los datos.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

// --- UserPreferencesRepository: La "Caja Fuerte" de Preferencias ---
// Esta clase centraliza toda la lógica de lectura y escritura con DataStore.
// Actúa como intermediario entre nuestra UI y el sistema de almacenamiento de datos.
class UserPreferencesRepository(context: Context) {

    // Obtenemos la instancia única del DataStore que creamos arriba.
    private val dataStore = context.dataStore

    // --- Claves de Preferencias ---
    // Es una buena práctica definir todas las "llaves" que usaremos para guardar datos
    // en un objeto privado. Esto evita errores tipográficos y mantiene el código organizado.
    private object PreferencesKeys {
        val DARK_MODE = booleanPreferencesKey("dark_mode") // Llave para un valor Booleano (true/false).
        val TASK_TEXT_COLOR = stringPreferencesKey("task_text_color") // Llave para un valor de texto (String).
        val TASK_LIST = stringSetPreferencesKey("task_list") // Llave para un conjunto de textos (Set<String>).
    }

    // --- Flujo de Lectura para el Modo Oscuro ---
    // `dataStore.data` es un Flow que emite las preferencias cada vez que cambian.
    // Usamos `map` para transformar los datos crudos y obtener solo el valor que nos interesa.
    val darkMode: Flow<Boolean> = dataStore.data.map {
        // `it[PreferencesKeys.DARK_MODE]` intenta leer el valor booleano.
        // `?: false` es el operador Elvis: si el valor no existe (la primera vez que se abre la app),
        // se devuelve `false` como valor por defecto.
        it[PreferencesKeys.DARK_MODE] ?: false
    }

    // --- Flujo de Lectura para el Color del Texto ---
    val taskTextColor: Flow<String> = dataStore.data.map {
        // Leemos el color, que guardamos como un texto (String) hexadecimal (ej: "FF000000").
        // Si no existe, devolvemos el código hexadecimal del color negro por defecto.
        it[PreferencesKeys.TASK_TEXT_COLOR] ?: "FF000000"
    }

    // --- Flujo de Lectura para la Lista de Tareas ---
    val tasks: Flow<List<String>> = dataStore.data.map {
        // Leemos el conjunto de tareas. Si no existe, devolvemos un conjunto vacío.
        // `.toList()` convierte el conjunto (Set) en una lista (List), que es más fácil de usar en la UI.
        it[PreferencesKeys.TASK_LIST]?.toList() ?: emptyList()
    }

    // --- Función de Escritura para el Modo Oscuro ---
    // Las funciones que escriben en DataStore deben ser `suspend` porque son operaciones asíncronas.
    suspend fun saveDarkMode(isDarkMode: Boolean) {
        // `dataStore.edit` abre una transacción para modificar los datos de forma segura.
        dataStore.edit {
            it[PreferencesKeys.DARK_MODE] = isDarkMode // Guardamos el nuevo valor en su llave correspondiente.
        }
    }

    // --- Función de Escritura para el Color del Texto ---
    suspend fun saveTaskTextColor(colorHex: String) {
        dataStore.edit {
            it[PreferencesKeys.TASK_TEXT_COLOR] = colorHex
        }
    }

    // --- Función de Escritura para la Lista de Tareas ---
    suspend fun saveTasks(tasks: List<String>) {
        dataStore.edit {
            // DataStore no puede guardar listas directamente, pero sí conjuntos (Sets).
            // Por eso, convertimos la lista a un conjunto antes de guardarla.
            it[PreferencesKeys.TASK_LIST] = tasks.toSet()
        }
    }
}
