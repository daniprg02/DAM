package com.example.sqlitemoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.sqlitemoviles.ui.theme.AppDatabase
import com.example.sqlitemoviles.ui.theme.Usuario
import com.example.sqlitemoviles.ui.theme.UsuarioDao
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RoomExampleScreen()
                }
            }
        }
    }
}

@Composable
fun RoomExampleScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Crear instancia de la base de datos
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "usuarios_room.db"
        ).build()
    }

    val dao = db.usuarioDao()

    var nombre by remember { mutableStateOf("") }
    var listaUsuarios by remember { mutableStateOf(listOf<Usuario>()) }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Persistencia con ROOM (SQLite modernizado)",
            style = MaterialTheme.typography.titleLarge)

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Introduce un nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombre.isNotEmpty()) {
                    scope.launch {
                        dao.insertarUsuario(Usuario(nombre = nombre))
                        mensaje = "✅ Usuario guardado correctamente"
                        nombre = ""
                    }
                } else {
                    mensaje = "⚠️ Escribe un nombre"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar usuario")
        }

        Button(
            onClick = {
                scope.launch {
                    listaUsuarios = dao.obtenerUsuarios()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mostrar usuarios")
        }

        Text(text = mensaje, style = MaterialTheme.typography.bodyLarge)

        Column(modifier = Modifier.fillMaxWidth()) {
            listaUsuarios.forEach { user ->
                Text("👤 ${user.nombre}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
