package com.example.to_do

// Importaciones de todas las librerías que necesitamos
import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_do.data.AppDatabase
import com.example.to_do.data.Tarea
import com.example.to_do.ui.theme.ToDoTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Clase principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pedimos permisos para notificaciones y escritura (para exportar)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }

        crearCanalNotificaciones()

        setContent {
            val contexto = this
            val ds = remember { DataStoreManager(contexto) }
            val scope = rememberCoroutineScope()
            var modoOscuro by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                ds.leerModoOscuro.collectLatest { modo -> modoOscuro = modo }
            }

            ToDoTheme(modoOscuro = modoOscuro) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AplicacionTodo(
                        modoOscuro = modoOscuro,
                        cambiarModo = { nuevo -> scope.launch { ds.guardarModoOscuro(nuevo) } },
                        ds = ds
                    )
                }
            }
        }
    }

    private fun crearCanalNotificaciones() {
        val canal = NotificationChannel(
            "canal_inactividad",
            "Recordatorios",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(canal)
    }
}

// Navegación principal
@Composable
fun AplicacionTodo(modoOscuro: Boolean, cambiarModo: (Boolean) -> Unit, ds: DataStoreManager) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login") {
        composable("login") {
            PantallaLogin(nav)
        }
        composable("tareas/{nombre}/{alias}") {
            val nombre = it.arguments?.getString("nombre") ?: ""
            val alias = it.arguments?.getString("alias") ?: ""
            PantallaTareas(nombre, alias, modoOscuro, cambiarModo, ds)
        }
    }
}

// Pantalla de Login (sin cambios)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLogin(nav: NavController) {
    var nombre by remember { mutableStateOf("") }
    var alias by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(60.dp))
        Image(painter = painterResource(id = R.drawable.todo), contentDescription = "Logo ToDo", modifier = Modifier.size(140.dp))
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = alias, onValueChange = { alias = it }, label = { Text("Alias") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { if (nombre.isNotEmpty() && alias.isNotEmpty()) nav.navigate("tareas/$nombre/$alias") },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotEmpty() && alias.isNotEmpty()
        ) {
            Text("Continuar")
        }
    }
}

// --- PANTALLA DE TAREAS (CON AJUSTES RESTAURADOS Y CORREGIDA) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(nombre: String, alias: String, modoOscuro: Boolean, cambiarModo: (Boolean) -> Unit, ds: DataStoreManager) {
    val contexto = LocalContext.current
    val scope = rememberCoroutineScope()

    val db = remember { AppDatabase.getDatabase(contexto) }
    val dao = remember { db.tareaDao() }

    // --- ESTADOS DE LA PANTALLA ---
    var nuevaTareaTexto by remember { mutableStateOf("") }
    var textoBusqueda by remember { mutableStateOf("") }
    var tareaAEliminar by remember { mutableStateOf<Tarea?>(null) }
    val listaTareas by dao.obtenerTareas(textoBusqueda).collectAsState(initial = emptyList())

    // --- SOLUCIÓN AL ERROR DE COMPOSABLE INVOCATION ---
    // 1. Leemos el color del tema en una variable normal, dentro del contexto @Composable.
    val colorPorDefecto = MaterialTheme.colorScheme.onBackground

    // --- ESTADOS PARA LOS AJUSTES ---
    var menuAbierto by remember { mutableStateOf(false) }
    // 2. Usamos la variable normal como valor inicial.
    val colorGuardado by ds.leerColorTexto.collectAsState(initial = colorPorDefecto.toArgb())
    var colorTexto by remember(colorGuardado) { mutableStateOf(Color(colorGuardado)) }

    // --- ESTADOS PARA EL DATEPICKER ---
    var fechaSeleccionada by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    var mostrarDatePicker by remember { mutableStateOf(false) }

    // --- DISEÑO DE LA PANTALLA ---
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Cabecera con saludo y menú de ajustes
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Hola, $nombre ($alias)", style = MaterialTheme.typography.headlineSmall)

            Box {
                IconButton(onClick = { menuAbierto = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Ajustes")
                }
                DropdownMenu(expanded = menuAbierto, onDismissRequest = { menuAbierto = false }) {
                    // Opción para Modo Oscuro
                    Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("Modo Oscuro")
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(checked = modoOscuro, onCheckedChange = cambiarModo)
                    }
                    Divider()
                    // Opciones de colores
                    DropdownMenuItem(text = { Text("Rojo") }, onClick = {
                        scope.launch { ds.guardarColorTexto(Color.Red.toArgb()) }
                        menuAbierto = false
                    })
                    DropdownMenuItem(text = { Text("Verde") }, onClick = {
                        scope.launch { ds.guardarColorTexto(Color.Green.toArgb()) }
                        menuAbierto = false
                    })
                    DropdownMenuItem(text = { Text("Azul") }, onClick = {
                        scope.launch { ds.guardarColorTexto(Color.Blue.toArgb()) }
                        menuAbierto = false
                    })
                    DropdownMenuItem(text = { Text("Default") }, onClick = {
                        // 3. Usamos la variable normal dentro del onClick.
                        scope.launch { ds.guardarColorTexto(colorPorDefecto.toArgb()) }
                        menuAbierto = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            label = { Text("Buscar tarea...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Fila para añadir nueva tarea
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = nuevaTareaTexto,
                onValueChange = { nuevaTareaTexto = it },
                label = { Text("Nueva tarea") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { mostrarDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        }
        if (fechaSeleccionada.isNotEmpty()) {
            Text("Fecha: $fechaSeleccionada", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para añadir la tarea
        Button(
            onClick = {
                if (nuevaTareaTexto.isNotEmpty()) {
                    scope.launch {
                        val fechaAGuardar = if (fechaSeleccionada.isNotEmpty()) fechaSeleccionada else SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                        dao.insertar(Tarea(descripcion = nuevaTareaTexto, fecha = fechaAGuardar))
                        nuevaTareaTexto = ""
                        fechaSeleccionada = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Añadir") }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de tareas (LazyColumn)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(listaTareas, key = { it.id }) {
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = it.descripcion, style = MaterialTheme.typography.bodyLarge, color = colorTexto)
                            if(it.fecha.isNotEmpty()) {
                                Text(text = it.fecha, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        IconButton(onClick = { tareaAEliminar = it }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para exportar tareas
        Button(onClick = { exportarTareas(contexto, listaTareas) }, modifier = Modifier.fillMaxWidth()) { Text("Exportar tareas") }
    }

    // Diálogo de confirmación para eliminar
    if (tareaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { tareaAEliminar = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Seguro que quieres eliminar la tarea '${tareaAEliminar?.descripcion}'?") },
            confirmButton = {
                Button(onClick = { scope.launch { dao.borrar(tareaAEliminar!!); tareaAEliminar = null } }) { Text("Aceptar") }
            },
            dismissButton = { Button(onClick = { tareaAEliminar = null }) { Text("Cancelar") } }
        )
    }

    // Diálogo del DatePicker
    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        fechaSeleccionada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                    }
                    mostrarDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = { Button(onClick = { mostrarDatePicker = false }) { Text("Cancelar") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

// Función para exportar las tareas a un archivo de texto
fun exportarTareas(context: Context, tareas: List<Tarea>) {
    val nombreArchivo = "tareas.txt"
    val contenido = StringBuilder()
    tareas.forEach {
        contenido.append("Tarea: ${it.descripcion}\n")
        contenido.append("Fecha: ${it.fecha}\n")
        contenido.append("-------------------\n")
    }

    try {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, nombreArchivo)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            context.contentResolver.openOutputStream(uri).use { it?.write(contenido.toString().toByteArray()) }
            Toast.makeText(context, "Tareas exportadas a Descargas", Toast.LENGTH_LONG).show()
        } else {
            throw Exception("URI nulo al guardar archivo")
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al exportar: ${e.message}", Toast.LENGTH_LONG).show()
    }
}