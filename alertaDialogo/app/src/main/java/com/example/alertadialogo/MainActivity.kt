package com.example.alertadialogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    SnackbarScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarScreen() {
    var texto by remember { mutableStateOf(TextFieldValue("")) }
    var duracionLarga by remember { mutableStateOf(true) }
    var snackbarCount by remember { mutableStateOf(0) }
    var indefinido by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text("Snackbar en Android", style = MaterialTheme.typography.headlineMedium)

            // Campo de texto
            OutlinedTextField(
                value = texto,
                onValueChange = {texto = it},
                label = { "Texto"  },
                modifier = Modifier.fillMaxWidth()
            )

            // Opciones de duración
            Text("Duración", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = duracionLarga && !indefinido,
                    onClick = {
                        duracionLarga = true
                        indefinido = false
                    }
                )
                Text("LONG")
                Spacer(Modifier.width(16.dp))
                RadioButton(
                    selected = !duracionLarga && !indefinido,
                    onClick = {
                        duracionLarga = false
                        indefinido = false
                    }
                )
                Text("SHORT")
                Spacer(Modifier.width(16.dp))
                RadioButton(
                    selected = indefinido,
                    onClick = {
                        indefinido = true
                        duracionLarga = false
                    }
                )
                Text("INDEFINIDO")
            }

            // Botón para mostrar Snackbar
            Button(
                onClick = {
                    scope.launch {
                        val duration = if (indefinido) {
                            SnackbarDuration.Indefinite
                        } else if (duracionLarga) {
                            SnackbarDuration.Long
                        } else {
                            SnackbarDuration.Short
                        }

                        snackbarHostState.showSnackbar(
                            message = texto.text,
                            duration = duration,
                            actionLabel = "OK"
                        )
                        snackbarCount++
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("MOSTRAR SNACKBAR")
            }

            // Contador
            Text("# Snackbars mostrados: $snackbarCount")
        }
    }
}

/*
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AccountSelectionScreen()
                }
            }
        }
    }
}

data class Account(
    val name: String,
    val imageRes: Int
)

@Composable
fun AccountSelectionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    accounts: List<Account>,
    onAccountSelected: (String) -> Unit
) {
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Seleccionar cuenta", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            },
            text = {
                Column {
                    accounts.forEach { account ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Toast.makeText(context, "Seleccionado: ${account.name}", Toast.LENGTH_SHORT).show()
                                    onAccountSelected(account.name)
                                    onDismiss()
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = account.imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(account.name, fontSize = 16.sp)
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Toast.makeText(context, "Añadir cuenta", Toast.LENGTH_SHORT).show()
                                onDismiss()
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Añadir cuenta",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Añadir cuenta", fontSize = 16.sp)
                    }
                }
            },
            confirmButton = {}
        )
    }
}

@Composable
fun AccountSelectionScreen() {
    var showDialog by remember { mutableStateOf(false) }
    val accounts = listOf(
        Account("drjlaw@outlook.com", R.drawable.ic_launcher_foreground),
        Account("sopwith@sbcglobal.net", R.drawable.ic_launcher_foreground),
        Account("rmcfarla@att.net", R.drawable.ic_launcher_foreground)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Seleccionar cuenta")
        }

        AccountSelectionDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            accounts = accounts,
            onAccountSelected = { selected ->
                println("Cuenta seleccionada: $selected")
            }
        )
    }
}
/*
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    DialogExampleScreen()
                }
            }
        }
    }
}
@Composable
fun SimpleAlertDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Título del diálogo")
            },
            text = {
                Text("Este es el contenido del diálogo.")
            },
            confirmButton = {
                Button(onClick = {

                    Toast.makeText(context, "Has pulsado Aceptar", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    Toast.makeText(context, "Has pulsado Cancelar", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun DialogExampleScreen() {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Mostrar diálogo")
        }

        SimpleAlertDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogExample() {
    MaterialTheme {
        DialogExampleScreen()
    }
}


 */