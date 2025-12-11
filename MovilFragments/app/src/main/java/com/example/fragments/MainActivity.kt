package com.example.jugadoresdropmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            menuJugadores()
        }
    }

    @Composable
    fun menuJugadores() {
        var expanded by remember { mutableStateOf(false) }
        val jugadores = listOf("Messi", "Cristiano", "Mbappe", "Neymar", "Modric")
        val seleccionados = remember { mutableStateListOf<String>() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Caja superior que abre el menú
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Seleccionados: ${seleccionados.size}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Mostrar texto de seleccionados
            if (seleccionados.isNotEmpty()) {
                Text(
                    text = "Seleccionados: ${seleccionados.joinToString()}",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Menú desplegable con checkboxes
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
            ) {
                jugadores.forEach { jugador ->
                    val checked = jugador in seleccionados
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = {
                                        if (it) seleccionados.add(jugador)
                                        else seleccionados.remove(jugador)
                                    }
                                )
                                Text(jugador)
                            }
                        },
                        onClick = {
                            if (checked) seleccionados.remove(jugador)
                            else seleccionados.add(jugador)
                        }
                    )
                }
            }
        }
    }

    @Preview(
        showBackground = true,
        showSystemUi = true,
        device = "spec:width=411dp,height=891dp,dpi=420"
    )
    @Composable
    fun GreetingPreview() {
        menuJugadores()
    }
}
