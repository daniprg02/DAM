package com.example.preparacionexamen.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.preparacionexamen.R

// --- LoginPantalla: La Pantalla de Inicio de Sesión ---
@OptIn(ExperimentalMaterial3Api::class) // Anotación para usar componentes de Material 3 que son experimentales.
@Composable
fun LoginPantalla(navController: NavController, themeController: ThemeController) {

    // --- Estados de la Pantalla ---
    // remember { mutableStateOf("") } crea una variable de estado que "recuerda" su valor
    // incluso cuando la pantalla se redibuja. Se usa para guardar lo que el usuario escribe.
    val nombre = remember { mutableStateOf("") }
    val alias = remember { mutableStateOf("") }

    // --- Scaffold: La Estructura Básica de la Pantalla ---
    // Proporciona una estructura estándar de Material Design (barra superior, contenido, etc.).
    Scaffold(
        topBar = {
            // --- TopAppBar: La Barra Superior ---
            TopAppBar(title = { Text("Inicio de sesión en Tareas") })
        }
    ) { paddingValues ->

        // --- Contenido Principal de la Pantalla ---
        // Column organiza sus elementos hijos uno debajo del otro.
        Column(
            verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente.
            horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente.
            modifier = Modifier
                .padding(paddingValues) // Aplica el padding necesario para no solaparse con la TopAppBar.
                .padding(40.dp) // Añade un padding extra alrededor de la columna.
                .fillMaxSize() // Hace que la columna ocupe todo el espacio disponible.
        ) {
            // --- Imagen del Logo ---
            Image(
                painter = painterResource(id = R.drawable.logo), // Carga la imagen desde los recursos drawable.
                contentDescription = "Logo", // Texto para accesibilidad.
                modifier = Modifier.size(130.dp) // Fija el tamaño de la imagen.
            )

            Spacer(modifier = Modifier.height(15.dp)) // Añade un espacio vertical.

            // --- Campo de Texto para el Nombre ---
            OutlinedTextField(
                value = nombre.value, // El valor del campo es la variable de estado `nombre`.
                onValueChange = { nombre.value = it }, // Cuando el usuario escribe, actualiza la variable.
                label = { Text("Nombre") }, // Etiqueta que se muestra sobre el campo.
                modifier = Modifier.fillMaxWidth() // Hace que el campo ocupe todo el ancho.
            )

            // --- Campo de Texto para el Alias ---
            OutlinedTextField(
                value = alias.value,
                onValueChange = { alias.value = it },
                label = { Text("Alias") },
                modifier = Modifier.fillMaxWidth()
            )

            // --- Botón de Acceso ---
            Button(
                onClick = {
                    // --- Lógica de Navegación ---
                    // Al hacer clic, navega a la ruta "tareas", pasando los valores de nombre y alias.
                    // La ruta se construye dinámicamente, ej: "tareas/Daniel/Dan".
                    navController.navigate("tareas/${nombre.value}/${alias.value}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text("Acceder")
            }
        }
    }
}
