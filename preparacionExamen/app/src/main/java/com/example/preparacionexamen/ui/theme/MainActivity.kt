package com.example.preparacionexamen.ui.theme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// --- MainActivity: La Puerta de Entrada de la Aplicación ---
// Es la primera pantalla que el sistema Android carga al iniciar la app.
class MainActivity : ComponentActivity() {

    // --- onCreate: El Corazón de la Actividad ---
    // Este método se llama cuando la actividad se crea por primera vez.
    // Es donde configuramos todo lo que la pantalla necesita para funcionar.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge(): Permite que la aplicación se dibuje a pantalla completa,
        // ocupando también el espacio de las barras de estado y navegación.
        enableEdgeToEdge()

        // --- setContent: Definiendo la Interfaz de Usuario con Compose ---
        // Aquí le decimos a la actividad que su diseño se construirá con Jetpack Compose.
        setContent {
            // --- Inicialización del ThemeController ---
            // Creamos y recordamos una instancia de nuestro ThemeController.
            // Este controlador se encargará de gestionar el tema de la app (modo oscuro, colores, etc.)
            // y persistirá los cambios gracias a DataStore.
            val themeController = rememberThemeController()

            // --- Aplicando el Tema Personalizado ---
            // Envolvemos toda nuestra interfaz de usuario con AppTheme.
            // Le pasamos el controlador para que el tema se aplique correctamente.
            AppTheme(themeController) {
                // --- Configurando la Navegación ---
                // Llamamos al composable que contiene la lógica de navegación (NavHost).
                // A partir de aquí, NavHost decidirá qué pantalla mostrar (Login, Tareas, etc.).
                NavegarEntrePantallas(themeController)
            }
        }
    }
}
