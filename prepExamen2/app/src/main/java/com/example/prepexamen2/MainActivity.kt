package com.example.prepexamen2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * MainActivity es la actividad principal de la aplicación.
 * Es el punto de entrada que se lanza al iniciar la app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge permite que la app se dibuje a pantalla completa, ocupando el espacio de las barras de sistema.
        enableEdgeToEdge()
        // setContent define la interfaz de usuario de la actividad usando Jetpack Compose.
        setContent {
            // Llama al Composable principal que gestiona la navegación.
            Navegar()
        }
    }
}

/**
 * Este Composable se encarga de configurar el sistema de navegación de la aplicación.
 * Utiliza un NavHost para definir las diferentes pantallas (destinos) y cómo se puede navegar entre ellas.
 */
@Preview // La anotación @Preview permite ver este Composable en la vista de diseño de Android Studio.
@Composable
fun Navegar() {
    // Crea y recuerda un NavController. Este objeto es responsable de gestionar la pila de navegación.
    val navController = rememberNavController()
    // NavHost es el contenedor que mostrará el destino actual según la ruta.
    NavHost(navController = navController, startDestination = "main") {
        // Define la pantalla principal con la ruta "main".
        composable("main") { MainPrincipal(navController) }
        // Define la pantalla de configuración con la ruta "configuracion".
        composable("configuracion") { ConfigurarPedido(navController) }
    }
}

/**
 * Composable para la pantalla principal de la aplicación.
 * Muestra el nombre de la tienda y un botón para ir a la pantalla de configuración.
 * @param navController Se pasa el controlador de navegación para poder cambiar de pantalla.
 */
@Composable
fun MainPrincipal(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.Home, contentDescription = "Home", tint = Color.Blue)
            Text("NovaTech Shop", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            // Al hacer clic, navega a la pantalla "configuracion".
            onClick = { navController.navigate("configuracion") },
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Configurar Pedido")
        }
    }
}

/**
 * Composable para la pantalla de configuración del pedido.
 * Contiene la lógica para modificar y mostrar las opciones del pedido.
 * @param navController Se pasa para poder volver a la pantalla anterior.
 */
@Composable
fun ConfigurarPedido(navController: NavController) {
    // --- VARIABLES DE ESTADO ---
    // Estas variables 'remember' guardan el estado de la configuración del pedido.
    // Si su valor cambia, la interfaz se recompone (se redibuja) automáticamente para reflejar el cambio.

    // `alias`: Almacena el alias para las reseñas. Se usa en el diálogo `DialogoAlias` y se muestra en el resumen.
    var alias by remember { mutableStateOf("Ninguno") }
    // `metodoPago`: Guarda el método de pago seleccionado. Se usa en `DialogoMetodoPago` y se muestra en el resumen.
    var metodoPago by remember { mutableStateOf("No seleccionado") }
    // `avisos`: Es una lista que contiene los tipos de avisos seleccionados (Email, SMS, etc.). Se usa en `DialogoAvisos` y en el resumen.
    var avisos by remember { mutableStateOf<List<String>>(emptyList()) }
    // `cambiosConfirmados`: Un booleano que indica si el usuario ha confirmado los cambios. Se usa en el `DialogoConfirmacionSimple` de confirmar y en el resumen.
    var cambiosConfirmados by remember { mutableStateOf(false) }

    // --- VARIABLES DE ESTADO PARA DIÁLOGOS ---
    // Estas variables booleanas controlan si los diálogos (ventanas emergentes) se muestran o no.
    // Se activan (true) al pulsar los botones correspondientes.

    var mostrarDialogoEliminar by remember { mutableStateOf(false) } // Usada para el diálogo de confirmación de vaciar carrito.
    var mostrarDialogoPago by remember { mutableStateOf(false) } // Usada para mostrar el diálogo de selección de pago.
    var mostrarDialogoAvisos by remember { mutableStateOf(false) } // Usada para el diálogo de selección de avisos.
    var mostrarDialogoAlias by remember { mutableStateOf(false) } // Usada para el diálogo que permite introducir un alias.
    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) } // Usada para el diálogo de confirmación final.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- BARRA SUPERIOR MEJORADA ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Botón para volver a la pantalla anterior.
            TextButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                Text("Volver", modifier = Modifier.padding(start = 4.dp))
            }
            // Título de la pantalla centrado.
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Icono de Pedido")
                Text("Configurar pedido", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTONES DE ACCIONES EN CUADRÍCULA ---
        val colorBotonMorado = Color(0xFF7E57C2)
        val formaBoton = RoundedCornerShape(16.dp)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp) // Espacio vertical entre las filas de botones
        ) {
            // --- Fila 1 de botones ---
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonPersonalizado(
                    modifier = Modifier.weight(1f),
                    texto = "Eliminar del carrito",
                    colorFondo = Color.Red,
                    onClick = { mostrarDialogoEliminar = true } // Al hacer clic, muestra el diálogo de eliminar.
                )
                Button(
                    onClick = { mostrarDialogoPago = true }, // Muestra el diálogo de pago.
                    modifier = Modifier.weight(1f),
                    shape = formaBoton,
                    colors = ButtonDefaults.buttonColors(containerColor = colorBotonMorado)
                ) {
                    Text("Método de pago")
                }
            }
            // --- Fila 2 de botones ---
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { mostrarDialogoAvisos = true }, // Muestra el diálogo de avisos.
                    modifier = Modifier.weight(1f),
                    shape = formaBoton,
                    colors = ButtonDefaults.buttonColors(containerColor = colorBotonMorado)
                ) {
                    Text("Avisos de envío")
                }
                Button(
                    onClick = { mostrarDialogoAlias = true }, // Muestra el diálogo de alias.
                    modifier = Modifier.weight(1f),
                    shape = formaBoton,
                    colors = ButtonDefaults.buttonColors(containerColor = colorBotonMorado)
                ) {
                    Text("Alias reseñas")
                }
            }
            // --- Fila 3: Botón de confirmar ---
            Button(
                onClick = { mostrarDialogoConfirmacion = true }, // Muestra el diálogo de confirmación.
                modifier = Modifier.fillMaxWidth(0.7f),
                shape = formaBoton,
                colors = ButtonDefaults.buttonColors(containerColor = colorBotonMorado)
            ) {
                Text("Confirmar")
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja el resumen hacia abajo.

        // --- ZONA DE RESUMEN DEL PEDIDO ---
        // Muestra los valores actuales de las variables de estado.
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Resumen del pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Alias: $alias")
            Text("Método de pago: $metodoPago")
            Text("Avisos: ${if (avisos.isEmpty()) "Ninguno" else avisos.joinToString(", ")}")
            Text("Cambios confirmados: ${if (cambiosConfirmados) "Sí" else "No"}")
        }
    }

    // --- LÓGICA DE LOS DIÁLOGOS ---
    // Estos bloques `if` dibujan los diálogos solo si su variable de estado correspondiente es `true`.

    if (mostrarDialogoEliminar) {
        DialogoConfirmacionSimple(
            titulo = "Eliminar carrito",
            texto = "¿Estás seguro de que quieres vaciar el carrito?",
            onDismiss = { mostrarDialogoEliminar = false }, // Cierra el diálogo al cancelar.
            onConfirm = {
                // Al confirmar, resetea todos los estados a sus valores iniciales.
                alias = "Ninguno"
                metodoPago = "No seleccionado"
                avisos = emptyList()
                cambiosConfirmados = false
                mostrarDialogoEliminar = false // Cierra el diálogo.
            }
        )
    }

    if (mostrarDialogoPago) {
        DialogoMetodoPago(
            onDismiss = { mostrarDialogoPago = false }, // Cierra el diálogo.
            onConfirm = { nuevoMetodo ->
                metodoPago = nuevoMetodo // Actualiza el estado con el nuevo método de pago.
                mostrarDialogoPago = false // Cierra el diálogo.
            }
        )
    }

    if (mostrarDialogoAvisos) {
        DialogoAvisos(
            avisosActuales = avisos,
            onDismiss = { mostrarDialogoAvisos = false },
            onConfirm = { nuevosAvisos ->
                avisos = nuevosAvisos // Actualiza el estado con los nuevos avisos.
                mostrarDialogoAvisos = false
            }
        )
    }

    if (mostrarDialogoAlias) {
        DialogoAlias(
            aliasActual = alias,
            onDismiss = { mostrarDialogoAlias = false },
            onConfirm = { nuevoAlias ->
                alias = nuevoAlias // Actualiza el estado con el nuevo alias.
                mostrarDialogoAlias = false
            }
        )
    }

    if (mostrarDialogoConfirmacion) {
        DialogoConfirmacionSimple(
            titulo = "Confirmar cambios",
            texto = "¿Deseas confirmar los cambios?",
            onDismiss = { mostrarDialogoConfirmacion = false },
            onConfirm = {
                cambiosConfirmados = true // Actualiza el estado de confirmación.
                mostrarDialogoConfirmacion = false
            }
        )
    }
}

/**
 * Un botón personalizado creado a partir de un Box para tener más control sobre su apariencia.
 * @param modifier Modificador para personalizar el layout.
 * @param texto El texto que se mostrará en el botón.
 * @param colorFondo El color de fondo del botón.
 * @param onClick La acción a ejecutar cuando se hace clic en el botón.
 */
@Composable
fun BotonPersonalizado(modifier: Modifier = Modifier, texto: String, colorFondo: Color, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorFondo)
            .clickable(onClick = onClick) // Hace que el Box sea clickeable.
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(texto, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

/**
 * Un diálogo de alerta reutilizable para confirmaciones simples (Sí/No).
 * @param titulo El título del diálogo.
 * @param texto El mensaje principal del diálogo.
 * @param onDismiss Función que se llama cuando se descarta el diálogo (ej. clic fuera o botón cancelar).
 * @param onConfirm Función que se llama cuando se pulsa el botón de confirmar.
 */
@Composable
fun DialogoConfirmacionSimple(titulo: String, texto: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(titulo) },
        text = { Text(texto) },
        confirmButton = { TextButton(onClick = onConfirm) { Text("Confirmar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

/**
 * Diálogo para seleccionar un método de pago de una lista de opciones.
 * Usa RadioButtons para asegurar que solo se pueda seleccionar una opción.
 * @param onDismiss Se llama para cerrar el diálogo.
 * @param onConfirm Devuelve la opción seleccionada.
 */
@Composable
fun DialogoMetodoPago(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    val opciones = listOf("Visa", "Mastercard", "PayPal")
    // Variable de estado local para guardar la selección DENTRO del diálogo.
    var seleccion by remember { mutableStateOf(opciones[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Elige un método de pago") },
        text = {
            Column {
                opciones.forEach { opcion ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(selected = (opcion == seleccion), onClick = { seleccion = opcion })
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = (opcion == seleccion), onClick = null) // El clic se maneja en la Row.
                        Text(text = opcion, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(seleccion) }) { Text("Aceptar") } }
    )
}

/**
 * Diálogo para seleccionar múltiples avisos (notificaciones).
 * Usa Checkboxes para permitir selecciones múltiples.
 * @param avisosActuales (Parámetro de ENTRADA) La lista de avisos que ya están seleccionados. Se usa para inicializar el diálogo.
 * @param onDismiss (Parámetro de ACCIÓN DE SALIDA) Función que se ejecuta cuando el usuario quiere cerrar el diálogo sin guardar.
 * @param onConfirm (Parámetro de ACCIÓN DE SALIDA CON DATOS) Función a la que se le pasa la nueva lista de selecciones cuando el usuario confirma.
 */
@Composable
fun DialogoAvisos(avisosActuales: List<String>, onDismiss: () -> Unit, onConfirm: (List<String>) -> Unit) {
    // Define la lista de todas las opciones de avisos disponibles.
    val opciones = listOf("Email", "SMS", "Push")

    // Variable de estado INTERNA del diálogo. Guarda las selecciones del usuario MIENTRAS el diálogo está abierto.
    // `remember` hace que el estado persista entre redibujados (recompositions).
    // Se inicializa con los `avisosActuales` convertidos a un Set para un manejo más eficiente.
    val selecciones = remember { mutableStateOf(avisosActuales.toSet()) }

    // El Composable que construye la ventana de diálogo.
    AlertDialog(
        // Cuando el usuario pide cerrar el diálogo (ej. pulsando fuera), se ejecuta la función `onDismiss` que pasamos.
        onDismissRequest = onDismiss,
        title = { Text("Selecciona los avisos") },
        text = {
            // El contenido principal del diálogo será una columna de opciones.
            Column {
                // Bucle `forEach`: por cada String en `opciones`, crea una fila (Row) con un Checkbox y un Texto.
                opciones.forEach { opcion ->
                    // Fila que contiene un Checkbox y un Texto.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            // Hace que toda la fila sea "clickeable", mejorando la experiencia de usuario.
                            .clickable {
                                // Lógica para añadir/quitar la opción del conjunto de selecciones.
                                val actuales = selecciones.value.toMutableSet() // 1. Crea una copia mutable del estado actual.
                                if (actuales.contains(opcion)) {
                                    actuales.remove(opcion) // 2a. Si ya estaba, la quita.
                                } else {
                                    actuales.add(opcion) // 2b. Si no estaba, la añade.
                                }
                                selecciones.value = actuales // 3. Actualiza el estado, provocando que la UI se redibuje.
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // El Checkbox es puramente visual.
                        Checkbox(
                            // Su estado (marcado/desmarcado) depende de si la `opcion` está en el conjunto `selecciones`.
                            checked = selecciones.value.contains(opcion),
                            // La lógica de cambio ya la maneja el `clickable` de la Row, por eso aquí es `null`.
                            onCheckedChange = null
                        )
                        // Texto que acompaña al Checkbox.
                        Text(opcion, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        },
        // Define el botón de confirmación del diálogo.
        confirmButton = {
            TextButton(
                // Al hacer click en "Aceptar"...
                onClick = {
                    // ...se llama a la función `onConfirm` que recibimos como parámetro...
                    // ...y se le pasa el estado actual de `selecciones`, convertido de nuevo a una lista.
                    onConfirm(selecciones.value.toList())
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}

/**
 * Diálogo para que el usuario introduzca un texto para el alias.
 * @param aliasActual El valor actual del alias para mostrarlo en el campo de texto.
 * @param onDismiss Se llama para cerrar el diálogo.
 * @param onConfirm Devuelve el nuevo alias introducido por el usuario.
 */
@Composable
fun DialogoAlias(aliasActual: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    // Estado local para guardar lo que el usuario escribe en el campo de texto.
    var texto by remember { mutableStateOf(aliasActual) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Introduce tu alias") },
        text = {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Alias") },
                singleLine = true
            )
        },
        confirmButton = { TextButton(onClick = { onConfirm(texto) }) { Text("Guardar") } }
    )
}
