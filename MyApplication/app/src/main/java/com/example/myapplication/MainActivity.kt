package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formulario()
        }


    }
}

@Preview(showBackground = true)
@Composable
fun Formulario() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        //Alinear central
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {
        //Poner una imagen
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .padding(20.dp)
                .size(130.dp)


        )
        //Espaciado entre elementos
        Spacer(modifier = Modifier.height(0.dp))

        Text(
            text = ("Inscripción Carrera Popular 2025"),
            color = Color.Blue

        )
        Spacer(modifier = Modifier.height(20.dp))
        /*
        Box(
          modifier = Modifier
            .border(width = 2.dp, color = Color.Gray)
            .padding(80.dp)
        )
        */
        Card(
            modifier = Modifier
                .padding(20.dp)
                .background(Color.White)
                .fillMaxWidth(0.9f),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                //Declaramos variables que pueden ser mutables
                var nombre by remember { mutableStateOf("") }
                var edad by remember { mutableStateOf("") }
                var aceptar by remember { mutableStateOf(false) }
                var notificaciones by remember { mutableStateOf(false) }
                val miColor = Color(0xFF673AB7)

                TextField(
                    value = nombre,
                    //El text field tiene que tener onValueChange, no puede tener String directos
                    onValueChange = { nombre = it },
                    label = { Text("Nombre y Apellidos") },
                    modifier = Modifier
                        .background(Color.Gray)

                )
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    modifier = Modifier
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = aceptar,
                        onCheckedChange = { aceptar = it }
                    )
                    Text(" Acepto las normas del evento")
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Deseo recibir notificaciones")
                    Spacer(modifier = Modifier.width(20.dp))
                    Switch(
                        checked = notificaciones,
                        onCheckedChange = { notificaciones = it },
                    )

                }
                Spacer(modifier = Modifier.width(30.dp))
                HorizontalDivider(thickness = 2.dp, color = Color.Black)

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button (
                        onClick = {},
                        //Redondea bordes
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()

                    )
                    {
                        Text("Enviar")
                    }
                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        "Gracias por inscribirte",
                        color = Color.Blue
                    )
                }

            }


        }


    }


}