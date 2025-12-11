package com.example.preparacionexamen.ui.theme

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.preparacionexamen.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasPantalla(nombre: String, alias: String, themeController: ThemeController) {

    // --- ESTADOS DE LA PANTALLA ---
    var showDialog by remember { mutableStateOf(false) } // Controla si se muestra el diálogo de borrado.
    var taskToDelete by remember { mutableStateOf<String?>(null) } // Almacena la tarea que se va a borrar.
    val expanded = remember { mutableStateOf(false) } // Controla si se muestra el menú desplegable.
    val textoBuscar = remember { mutableStateOf("") } // Almacena el texto del campo de búsqueda.
    val textoAñadir = remember { mutableStateOf("") } // Almacena el texto del campo para añadir tareas.

    // --- INICIALIZACIÓN Y CONEXIÓN CON DATASTORE ---
    val context = LocalContext.current // Obtiene el contexto actual, necesario para muchas APIs de Android.
    val scope = rememberCoroutineScope() // Crea un ámbito de corrutinas ligado al ciclo de vida del Composable.
    val userPreferencesRepository = remember { UserPreferencesRepository(context) } // Crea y recuerda el repositorio de preferencias.
    // Se suscribe al Flow de tareas del DataStore. Cada vez que las tareas cambien en el DataStore,
    // `listaTareas` se actualizará automáticamente, redibujando la UI.
    val listaTareas by userPreferencesRepository.tasks.collectAsState(initial = emptyList())

    // --- BLOQUE 1: PERMISOS DE NOTIFICACIÓN ---
    // rememberLauncherForActivityResult prepara un "lanzador" para pedir un permiso.
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Aquí se gestiona la respuesta del usuario al diálogo de permisos.
        if (!isGranted) { /* Opcional: Mostrar mensaje si el permiso es denegado. */ }
    }
    // LaunchedEffect se ejecuta una vez cuando la pantalla se crea (gracias a `Unit`).
    LaunchedEffect(Unit) {
        // Si la versión de Android es 13 (TIRAMISU) o superior, es obligatorio pedir permiso.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS) // Lanza el diálogo para pedir permiso.
        }
    }

    // --- BLOQUE 2: TEMPORIZADOR DE INACTIVIDAD ---
    // LaunchedEffect se re-ejecutará cada vez que el tamaño de `listaTareas` cambie.
    LaunchedEffect(listaTareas.size) {
        delay(15000) // Pausa la corrutina durante 15 segundos.
        // Después de la pausa, si el efecto no ha sido cancelado (porque la lista cambió),
        // se muestra la notificación de inactividad.
        showInactivityNotification(context, "inactivity_channel")
    }

    // --- BLOQUE 3: SENSOR DE AGITACIÓN ---
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // remember { ... } crea un objeto `SensorEventListener` que sobrevive a las recomposiciones.
    val shakeListener = remember { object : SensorEventListener {
        private var lastUpdate: Long = 0
        private var last_x: Float = 0f
        private var last_y: Float = 0f
        private var last_z: Float = 0f
        private val SHAKE_THRESHOLD = 1200 // Umbral de sensibilidad de la agitación.

        // onSensorChanged se llama cada vez que el sensor detecta un cambio.
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val curTime = System.currentTimeMillis()
                if ((curTime - lastUpdate) > 100) { // Procesa los datos cada 100ms para no sobrecargar.
                    val diffTime = (curTime - lastUpdate)
                    lastUpdate = curTime
                    val (x, y, z) = event.values
                    val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000
                    if (speed > SHAKE_THRESHOLD) { // Si la velocidad supera el umbral...
                        val randomColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
                        themeController.setTaskTextColor(randomColor) //...cambia el color y lo guarda en DataStore.
                    }
                    last_x = x
                    last_y = y
                    last_z = z
                }
            }
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }}

    // DisposableEffect gestiona el ciclo de vida del listener del sensor.
    DisposableEffect(Unit) {
        sensorManager.registerListener(shakeListener, accelerometer, SensorManager.SENSOR_DELAY_UI) // Registra el listener al entrar en la pantalla.
        onDispose { sensorManager.unregisterListener(shakeListener) } // Lo des-registra al salir para ahorrar batería.
    }
    
    // --- BLOQUE 4: DIÁLOGO DE ALERTA PARA BORRAR ---
    if (showDialog) { // Si `showDialog` es true, se muestra el diálogo.
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Permite cerrar el diálogo pulsando fuera.
            title = { Text("Confirmar borrado") },
            text = { Text("¿Estás seguro de que quieres borrar la tarea: '${taskToDelete}'?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { // Se usa una corrutina para operaciones de guardado.
                        taskToDelete?.let { // Si la tarea a borrar no es nula...
                            val updatedList = listaTareas - it // Crea una nueva lista sin esa tarea.
                            userPreferencesRepository.saveTasks(updatedList) // Guarda la nueva lista en DataStore.
                        }
                        showDialog = false // Cierra el diálogo.
                    }
                }) { Text("Aceptar") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }

    // --- BLOQUE 5: ESTRUCTURA Y UI DE LA PANTALLA ---
    Scaffold(
        topBar = { /* ... Barra superior con menú ... */ },
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Hola $nombre, alias $alias", fontSize = 20.sp)

            Spacer(Modifier.height(20.dp))

            // --- Barra de Búsqueda y Añadir Tarea ---
            OutlinedTextField(value = textoBuscar.value, onValueChange = { textoBuscar.value = it }, /* ... */ )
            Row { /* ... */
                OutlinedTextField(value = textoAñadir.value, onValueChange = { textoAñadir.value = it }, /* ... */)
                Button(onClick = {
                    if (textoAñadir.value.isNotBlank()) {
                        scope.launch { // Se lanza una corrutina para guardar.
                            val updatedList = listaTareas + textoAñadir.value // Crea una nueva lista con la nueva tarea.
                            userPreferencesRepository.saveTasks(updatedList) // Guarda la nueva lista en DataStore.
                            textoAñadir.value = "" // Limpia el campo de texto.
                        }
                    }
                }) { Text("Añadir") }
            }

            Spacer(Modifier.height(20.dp))

            // --- Lista de Tareas ---
            val tareasFiltradas = listaTareas.filter { it.contains(textoBuscar.value, ignoreCase = true) }
            LazyColumn {
                items(tareasFiltradas) { tarea ->
                    Card(/* ... */) {
                        Row(/* ... */) {
                            Text(text = tarea, color = themeController.taskTextColor, /* ... */)
                            IconButton(onClick = { 
                                taskToDelete = tarea // Guarda la tarea que se va a borrar.
                                showDialog = true // Muestra el diálogo de confirmación.
                            }) { Icon(Icons.Default.Delete, "Borrar", tint = Color.Red) }
                        }
                    }
                }
            }
        }
    }
}

// --- BLOQUE 6: FUNCIÓN AUXILIAR PARA NOTIFICACIONES ---
fun showInactivityNotification(context: Context, channelId: String) {
    // A partir de Android 8.0 (Oreo), es obligatorio crear un canal de notificación.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Canal de Inactividad"
        val importance = NotificationManager.IMPORTANCE_HIGH // Prioridad alta para que aparezca como pop-up.
        val channel = NotificationChannel(channelId, name, importance)
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // --- Construcción de la Notificación ---
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("¿Necesitas ayuda?")
        .setContentText("Recuerda añadir una nueva tarea.")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true) // La notificación desaparece al pulsarla.

    // --- Muestra de la Notificación ---
    with(NotificationManagerCompat.from(context)) {
        // Comprueba si tenemos permiso antes de intentar mostrar la notificación.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notify(1, builder.build()) // Muestra la notificación con un ID único.
        }
    }
}
