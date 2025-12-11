package com.example.preparacionexamen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// --- Esquemas de Color Predeterminados ---
// Se definen los esquemas de color básicos para el modo claro y oscuro.
// `lightColorScheme()` y `darkColorScheme()` crean paletas de colores estándar de Material 3.
// Podríamos personalizarlos aquí si quisiéramos (ej: `lightColorScheme(primary = Color.Red)`).
private val LightColors = lightColorScheme()
private val DarkColors = darkColorScheme()

// --- AppTheme: El Composable que Aplica el Tema ---
// Este es el componente que envuelve toda nuestra aplicación para darle un estilo consistente.
@Composable
fun AppTheme(themeController: ThemeController, content: @Composable () -> Unit) {

    // --- Lógica de Selección de Color ---
    // Comprueba el estado `darkMode` de nuestro `themeController` para decidir qué
    // esquema de color usar (el claro o el oscuro).
    val colorScheme = if (themeController.darkMode) DarkColors else LightColors

    // --- Componente Principal del Tema ---
    // MaterialTheme es el componente de Compose que aplica el estilo a todos sus hijos.
    MaterialTheme(
        colorScheme = colorScheme,      // Aplica la paleta de colores decidida arriba.
        typography = Typography,      // Aplica la tipografía definida en `Type.kt`.
        shapes = Shapes,            // Aplica las formas de componentes definidas en `Shape.kt`.
        content = content             // Aquí se renderiza el contenido de la aplicación (el resto de la UI).
    )
}
