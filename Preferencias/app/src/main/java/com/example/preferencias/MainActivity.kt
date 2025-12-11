//package com.example.preferencias
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.preferencias.SettingsPreferences
//import kotlinx.coroutines.launch
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                Surface {
//                    SettingsScreen()
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun SettingsScreen() {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//
//    // Leemos el valor guardado
//    val colorMode = SettingsPreferences.getColorMode(context).collectAsState(initial = false)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Spacer(modifier = Modifier.height(80.dp))
//        Text(
//            text = if (colorMode.value) "Texto verde ACTIVADO" else "Texto amarillo ACTIVADO",
//            color = if (colorMode.value) Color.Green else Color.Yellow,
//            style = MaterialTheme.typography.headlineMedium
//        )
//
//        Switch(
//            checked = colorMode.value,
//            onCheckedChange = { checked ->
//                scope.launch {
//                    SettingsPreferences.setColorMode(context, checked)
//                }
//            }
//        )
//    }
//}
package com.example.preferencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    ColorSelectorScreen()
                }
            }
        }
    }
    @Preview
    @Composable
    fun ColorSelectorScreen() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        // Leemos el valor guardado
        val selectedColor = ColorPreferences.getColor(context).collectAsState(initial = "Rojo")

        val options = listOf("Rojo", "Verde", "Azul")

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(80.dp))
            Text("Elige un color:", style = MaterialTheme.typography.titleLarge)

            options.forEach { color ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedColor.value == color),
                        onClick = {
                            scope.launch {
                                ColorPreferences.setColor(context, color)
                            }
                        }
                    )
                    Spacer(Modifier.Companion.width(8.dp))
                    Text(text = color)
                }
            }

            Spacer(Modifier.Companion.height(24.dp))

            Text(
                text = "Color seleccionado: ${selectedColor.value}",
                color = when (selectedColor.value) {
                    "Rojo" -> Color.Red
                    "Verde" -> Color.Green
                    "Azul" -> Color.Blue
                    else -> Color.Black
                },
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }

}