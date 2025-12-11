package com.example.jpcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jpcompose.ui.theme.JpComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JpComposeTheme {
                ConstraintLayoutEjercicio()
            }
        }
    }
}

@Composable
fun ConstraintLayoutEjercicio() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(40.dp)
    ) {
        val (box1, box2, box3, box4) = createRefs()

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Yellow)
                .border(BorderStroke(5.dp, Color.Black))
                .constrainAs(box1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)

                }
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Green)
                .border(BorderStroke(5.dp, Color.Black))
                .constrainAs(box2) {
                    start.linkTo(box1.end, margin = 50.dp)

                }
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Red)
                .border(BorderStroke(5.dp, Color.Black))
                .constrainAs(box3) {
                    top.linkTo(box2.bottom, margin = 50.dp)
                    start.linkTo(box2.start)
                }
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Magenta)
                .border(BorderStroke(5.dp, Color.Black))
                .constrainAs(box4) {
                    end.linkTo(parent.end)
                    top.linkTo(box3.bottom, margin = 30.dp)
                }
        )


    }
}


@Composable
fun ConstraintLayoutDemo() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(25.dp)
    ) {
        // Crear referencias para los elementos
        val (texto1, texto2, boton) = createRefs()

        Text(
            text = "Texto 1",
            fontSize = 25.sp,
            color = Color.Blue,
            modifier = Modifier.constrainAs(texto1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Texto 2",
            fontSize = 25.sp,
            color = Color.Red,
            modifier = Modifier.constrainAs(texto2) {
                top.linkTo(texto1.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(boton) {
                top.linkTo(texto2.bottom, margin = 20.dp)
                start.linkTo(texto2.start)
                end.linkTo(texto2.end)
            }
        ) {
            Text(text = "Pulsar")
        }
    }
}

@Composable fun CheckBoxDemo() {
    //Estado inicial del checkbox var checked by remember { mutableStateOf(true) } Row( verticalAlignment = Alignment.CenterVertically ) { Text(text = "Acepto") Checkbox( checked = checked, onCheckedChange = { checked = it } ) Text( text = if (checked) "Checkbox activo" else "Checkbox desactivado" ) } }



@Composable
fun SwitchDemo() {
    var checked by remember { mutableStateOf(true) }
    Row (verticalAlignment =
        Alignment.CenterVertically) {
        Switch(
            checked = checked,
            onCheckedChange = { checked = it })
        Text (text =
            if (checked) "Activado" else "desactivado")
    }
}


@Composable
    fun DividerExample(){
    Column(

    ){

    }
}


@Composable
fun botonConDemo(){
    Button(onClick = {}){
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = " Imagen corazon"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Me gusta")
    }
}


@Composable
fun ConstraintLayoutPreview() {
    JpComposeTheme {
        ConstraintLayoutEjercicio()
    }
}}
