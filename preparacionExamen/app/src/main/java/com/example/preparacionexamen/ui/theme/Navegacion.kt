package com.example.preparacionexamen.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- NavegarEntrePantallas: El Cerebro de la Navegación ---
// Este composable se encarga de gestionar qué pantalla se muestra en cada momento.
@Composable
fun NavegarEntrePantallas(themeController: ThemeController) {

    // --- Creación del Controlador de Navegación ---
    // rememberNavController() crea y recuerda un NavController.
    // Este objeto es el que nos permite movernos entre pantallas (ej: navController.navigate("tareas")).
    val navController = rememberNavController()

    // --- NavHost: El Contenedor de Pantallas ---
    // NavHost es el componente que muestra la pantalla actual según la ruta en la que nos encontremos.
    // startDestination = "login" define que la primera pantalla que se mostrará será la de login.
    NavHost(navController = navController, startDestination = "login") {

        // --- Definición de la Ruta "login" ---
        // composable("login") define una pantalla para la ruta "login".
        // Cuando naveguemos a "login", se mostrará el contenido de este bloque.
        composable("login") {
            // Mostramos la pantalla de login, pasándole el navController para que
            // pueda navegar a otras pantallas, y el themeController para los ajustes de tema.
            LoginPantalla(navController, themeController)
        }

        // --- Definición de la Ruta "tareas" con Argumentos ---
        // Aquí definimos la ruta para la pantalla de tareas.
        // "tareas/{nombre}/{alias}" significa que esta ruta espera recibir dos argumentos:
        // el nombre y el alias del usuario.
        composable("tareas/{nombre}/{alias}") { backStackEntry ->
            // --- Extracción de los Argumentos ---
            // Obtenemos los valores de "nombre" y "alias" que se pasaron en la URL de navegación.
            // Si por alguna razón no llegan, usamos un valor por defecto ("").
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val alias = backStackEntry.arguments?.getString("alias") ?: ""

            // Mostramos la pantalla de tareas, pasándole los datos del usuario y el themeController.
            TareasPantalla(nombre, alias, themeController)
        }
    }
}
