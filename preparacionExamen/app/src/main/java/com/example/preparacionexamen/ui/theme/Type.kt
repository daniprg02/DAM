package com.example.preparacionexamen.ui.theme

import androidx.compose.material3.Typography

// --- Definición de la Tipografía de la App ---

// Se crea un objeto `Typography`.
// En este caso, no estamos personalizando ninguna fuente, por lo que simplemente
// creamos una instancia vacía `Typography()`.
// Esto le dice a MaterialTheme que use la tipografía por defecto de Material 3
// (las fuentes Roboto o Roboto Flex, dependiendo de la versión de Android).

// Si quisiéramos usar fuentes personalizadas, las definiríamos aquí. Por ejemplo:
// val Typography = Typography(
//     bodyLarge = TextStyle(
//         fontFamily = FontFamily.Cursive,
//         fontWeight = FontWeight.Normal,
//         fontSize = 16.sp
//     )
// )
val Typography = Typography()
