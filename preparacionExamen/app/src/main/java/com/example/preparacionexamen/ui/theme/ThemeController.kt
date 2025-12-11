package com.example.preparacionexamen.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// --- Definición de Colores Constantes ---
// Se definen como `val` a nivel de archivo para que sean accesibles desde cualquier parte de la app.
val AppRed = Color(0xFFFF5555)
val AppBlue = Color(0xFF5590FF)
val AppGreen = Color(0xFF55FF88)
val AppYellow = Color(0xFFFFEE55)

// --- ThemeController: El Director de Orquesta del Tema ---
// Esta clase actúa como intermediario entre la UI y el repositorio de datos (DataStore).
// No almacena el estado directamente, sino que lo lee del repositorio y le ordena cuándo guardar los cambios.
class ThemeController(
    private val userPreferencesRepository: UserPreferencesRepository, // La "caja fuerte" de preferencias.
    private val scope: CoroutineScope // Un ámbito de corrutinas para lanzar operaciones de guardado.
) {
    // --- Propiedad Reactiva para el Modo Oscuro ---
    // `@Composable get()` crea una propiedad que solo se puede leer dentro de un Composable.
    // `collectAsState` se suscribe al Flow de `darkMode` del repositorio. Cada vez que el valor
    // en DataStore cambie, esta propiedad se actualizará y la UI se redibujará automáticamente.
    val darkMode @Composable get() = userPreferencesRepository.darkMode.collectAsState(initial = false).value

    // --- Propiedad Reactiva para el Color del Texto ---
    val taskTextColor @Composable get() = userPreferencesRepository.taskTextColor
        .map { colorHex ->
            // El DataStore nos da el color como un texto hexadecimal ("FF000000").
            // Usamos `map` para transformar ese texto en un objeto `Color` de Compose.
            Color(android.graphics.Color.parseColor("#$colorHex"))
        }
        .collectAsState(initial = Color.Black).value // Nos suscribimos y definimos el negro como color inicial.

    // --- Función para Guardar el Modo Oscuro ---
    fun setDarkMode(enabled: Boolean) {
        // Lanzamos una corrutina para no bloquear el hilo principal de la UI.
        scope.launch {
            // Le pedimos al repositorio que guarde el nuevo estado.
            userPreferencesRepository.saveDarkMode(enabled)
        }
    }

    // --- Función para Guardar el Color del Texto ---
    fun setTaskTextColor(color: Color) {
        scope.launch {
            // DataStore no puede guardar objetos `Color`, pero sí texto (String).
            // Convertimos el objeto `Color` a su representación hexadecimal en texto.
            val colorHex = Integer.toHexString(color.toArgb()).substring(2).uppercase()
            userPreferencesRepository.saveTaskTextColor(colorHex)
        }
    }
}

// --- Función de Ayuda para Crear y Recordar el ThemeController ---
// Esta función simplifica la creación del ThemeController en la UI.
@Composable
fun rememberThemeController(): ThemeController {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    // `remember` asegura que el ThemeController y su repositorio se creen una sola vez
    // y sobrevivan a las recomposiciones de la pantalla.
    return remember {
        ThemeController(
            userPreferencesRepository = UserPreferencesRepository(context),
            scope = scope
        )
    }
}
