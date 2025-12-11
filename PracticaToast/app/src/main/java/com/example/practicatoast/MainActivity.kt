package com.example.practicatoast

import android.R.attr.text
import android.app.ProgressDialog.show
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicatoast.ui.theme.PracticaToastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    ToastPractica()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ToastPractica() {

    //variables

    val context = LocalContext.current

    // Estado de variables
    var texto by remember { mutableStateOf(TextFieldValue("")) }
    var duracionLarga by remember { mutableStateOf(true) }
    var contador by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(color = Color.White),


    ) {
        //Box
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Magenta),
            contentAlignment = Alignment.Center
        ){
        //Text
        Text(
            text = "Toast en Android",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .background(color = Color.Magenta)
                .padding(15.dp),
        )
        }


        Spacer(modifier = Modifier.padding(10.dp))
        Text(text= "Texto")

        //TextField

        TextField(
            value = texto,
            onValueChange = {texto = it},
            placeholder = ({ Text("Mensaje") }),
            modifier = Modifier
                .fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(24.dp))

        //Text(titulo, etiquetas y contador)
        Text(text= "Duración")

        //Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            //RadioButton + Text
            RadioButton(
                selected = duracionLarga,
                onClick = { duracionLarga = true },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Cyan)
            )
            Text(text="Duración larga")
            RadioButton(
                selected = !duracionLarga,
                onClick = { duracionLarga = false },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Cyan)
            )
            Text(text="Duración corta")
            
        }
        Spacer(modifier = Modifier.height(24.dp))
        //Button (toast)
        Button(
            onClick = {
                val mensaje = texto.text
                val duracion = if (duracionLarga) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                Toast.makeText(context, mensaje, duracion).show()
                contador++
            },
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text("Mostrar tostadas")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Tostadas mostradas $contador", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
    }


}