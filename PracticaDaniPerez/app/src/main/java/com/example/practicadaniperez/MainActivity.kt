package com.example.practicadaniperez

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContent define la interfaz de usuario de la actividad usando Jetpack Compose.
        setContent {
            // Llama al Composable principal que gestiona la navegación.
            HealthyApp()
        }
    }
}

/* ---------- TEMA PERSONALIZADO (Material Design 3) ---------- */
@Composable
fun HealthyTheme(content: @Composable () -> Unit) {
    val customTypography = Typography(
        titleLarge = MaterialTheme.typography.titleLarge.copy(letterSpacing = 0.5.sp),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(lineHeight = 20.sp)
    )

    val customShapes = Shapes(
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(24.dp)
    )

    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = customTypography,
        shapes = customShapes,
        content = content
    )
}
@Preview
@Composable
fun HealthyApp() {
    // COMPLETAR AQUÍ:

    // COMPLETAR AQUÍ: Definir NavHost con dos pantallas:
    // - "list" → ActivityListScreen
    // - "settings" → SettingsScreen
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            ActivityListScreen(onNavigateSettings = { navController.navigate("settings") })
        }

        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityListScreen(onNavigateSettings: () -> Unit) {
    val context = LocalContext.current
    // COMPLETAR AQUÍ: Estado para texto de búsqueda
    val textoBuscar = remember { mutableStateOf("") }
    // COMPLETAR AQUÍ: Lista inicial de actividades saludables
    var listaInicial = remember { mutableStateListOf<String?>(
        "caminar",
        "andar",
        "comer bien",
        "hacer ejercicio"
    ) }

    var actividadSeleccionada by remember { mutableStateOf<String?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    // COMPLETAR AQUÍ: Variable para mostrar el diálogo (String?)
    var showDialog by remember { mutableStateOf(false) }


    Scaffold(
        // COMPLETAR AQUÍ: TopAppBar con título y botón de configuración
        // Usa MaterialTheme.typography.titleLarge para el título
        topBar = {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {


                Row(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                "HealthyTracker",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Configuración",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onNavigateSettings() })

                }

                    // COMPLETAR AQUÍ: FAB que añade una actividad aleatoria y lanza notificación
                    FloatingActionButton(
                        onClick = {  },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Añadir elemento"
                        )
                    }


            }
        }



    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 🔍 COMPLETAR AQUÍ: Barra de búsqueda (OutlinedTextField)
            // Usa shape = MaterialTheme.shapes.medium
            OutlinedTextField(
                value = textoBuscar.value,
                onValueChange = { textoBuscar.value = it },
                label = { Text("Buscar actividad")},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(horizontal = 50.dp)
            )
            Spacer(Modifier.height(20.dp))
            // 📋 COMPLETAR AQUÍ: Lista (LazyColumn) filtrada según el texto de búsqueda
            // Cada elemento debe abrir un diálogo con más información
            val tareasFiltradas = listaInicial.filter { it?.contains(textoBuscar.value, ignoreCase = true)
                ?: false }
            val actividadesFiltradas = tareasFiltradas.filter {
                it?.contains(textoBuscar.value, ignoreCase = true) ?:false
            }

            LazyColumn {
                items(actividadesFiltradas) { actividad ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                actividadSeleccionada = actividad
                                mostrarDialogo = true
                            }
                    ) {
                        if (actividad != null) {
                            Text(
                                actividad,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ){
                            IconButton(onClick = {
                                listaInicial.remove(actividad) // Guarda la tarea que se va a borrar.
                            }) { Icon(Icons.Default.Delete, "Borrar", tint = Color.Red) }
                        }
                    }
                }
            }

            if (mostrarDialogo && actividadSeleccionada != null) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = { Text("Información") },
                    text = { Text("Actividad: $actividadSeleccionada") },
                    confirmButton = {
                        TextButton(onClick = { mostrarDialogo = false }) {
                            Text("Cerrar")
                        }
                    }
                )
            }
        }
    }
}



@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(false) }

    Scaffold(

        // COMPLETAR AQUÍ: TopAppBar con botón para volver atrás

    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Preferencias de notificación",
                style = MaterialTheme.typography.titleMedium
            )

            // COMPLETAR AQUÍ: Switch para activar/desactivar notificaciones
            // Al activarlo, debe llamar a showNotification()
        }
    }
}
fun showNotification(context: Context, message: String) {
    val channelId = "healthy_channel"
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Canal visible",
            NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("HealthyTracker")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    manager.notify(System.currentTimeMillis().toInt(), builder.build())
}

/*
🔔 Función auxiliar para mostrar notificaciones
fun showNotification(context: Context, message: String) {
    val channelId = "healthy_channel"
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // COMPLETAR AQUÍ: Crear canal de notificación {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Canal visible"
        val descriptionText = "Muestra notificaciones de prueba"
        val importance = NotificationManager.IMPORTANCE_HIGH //  ALTA PRIORIDAD
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
            enableVibration(true)
            enableLights(true)
        }
        manager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notificación de prueba")
            .setContentText("Esta notificación debería verse en la barra superior.")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // IMPORTANTE
            .setAutoCancel(true)

            manager.notify(1, builder.build())
    }
}

*/
