package com.example.persistenciamemoriaexpublico



import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PublicDownloadExample()
                }
            }
        }
    }
}

@SuppressLint("ObsoleteSdkInt")
@Composable
fun PublicDownloadExample() {
    val context = LocalContext.current
    var texto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val nombreArchivo = "nota_publica.txt"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Guardar archivo en carpeta pública Descargas",
            style = MaterialTheme.typography.titleLarge)

        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Escribe un texto") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- BOTÓN GUARDAR ---
        Button(onClick = {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Android 10+ → Scoped Storage con MediaStore
                    val values = ContentValues().apply {
                        put(MediaStore.Downloads.DISPLAY_NAME, nombreArchivo)
                        put(MediaStore.Downloads.MIME_TYPE, "text/plain")
                        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }

                    val resolver = context.contentResolver
                    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)

                    uri?.let {
                        resolver.openOutputStream(it)?.use { output ->
                            output.write(texto.toByteArray())
                        }
                        mensaje = "Guardado en carpeta Descargas (Scoped Storage)"
                    } ?: run {
                        mensaje = "Error: no se pudo crear el archivo"
                    }
                } else {
                    // Android 9 o inferior → acceso directo en modo append
                    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    if (!dir.exists()) dir.mkdirs()
                    val archivo = File(dir, nombreArchivo)
                    FileOutputStream(archivo, true).use { output ->
                        if (archivo.length() > 0) {
                            output.write("\n".toByteArray())
                        }
                        output.write(texto.toByteArray())
                    }
                    mensaje = "Texto añadido en: ${archivo.absolutePath}"
                }
            } catch (e: Exception) {
                mensaje = "Error al guardar: ${e.message}"
            }
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar en Descargas")
        }

        // --- BOTÓN LEER ---
        Button(onClick = {
            try {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val archivo = File(dir, nombreArchivo)
                if (archivo.exists()) {
                    val contenido = archivo.bufferedReader().use { it.readText() }
                    mensaje = "Contenido leído:\n$contenido"
                } else {
                    mensaje = "No se encontró el archivo."
                }
            } catch (e: Exception) {
                mensaje = "Error al leer: ${e.message}"
            }
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Leer archivo de Descargas")
        }

        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
