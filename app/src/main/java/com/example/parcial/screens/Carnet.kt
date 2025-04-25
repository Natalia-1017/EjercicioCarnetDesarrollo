package com.example.parcial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.parcial.R
import java.net.URLDecoder
import androidx.compose.ui.geometry.Offset
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.*

@Composable
fun CarnetScreen(
    navController: NavController,
    nombre: String,
    raza: String,
    tamano: String,
    imagenUrl: String
) {
    fun decode(input: String): String = URLDecoder.decode(input, "UTF-8")

    val decodedNombre = decode(nombre)
    val decodedRaza = decode(raza)
    val decodedTamano = decode(tamano)
    val decodedUrl = decode(imagenUrl)

    var carnetList by rememberSaveable { mutableStateOf(mutableListOf<Carnet>()) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var currentCarnet by remember { mutableStateOf<Carnet?>(null) }

    fun addOrUpdateCarnet(carnet: Carnet) {
        if (carnetList.contains(carnet)) {
            // Actualiza el carnet existente
            val index = carnetList.indexOf(carnet)
            carnetList[index] = carnet
        } else {
            // Añadir el carnet a la lista
            carnetList.add(carnet)
        }
    }

    fun removeCarnet(carnet: Carnet) {
        carnetList.remove(carnet)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2F7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tarjeta
        Card(
            modifier = Modifier
                .width(280.dp)
                .height(460.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Box {
                // Fondo huellas
                Image(
                    painter = painterResource(id = R.drawable.huella),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.08f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Título
                    TitleText("IDENTIFICACIÓN", 20.sp)

                    // Imagen circular
                    Image(
                        painter = rememberAsyncImagePainter(decodedUrl),
                        contentDescription = "Foto de la mascota",
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                            .background(Color.White),
                        contentScale = ContentScale.Crop
                    )

                    // Info mascota
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TitleText(decodedNombre.uppercase(), 22.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoText("Raza: $decodedRaza")
                        InfoText("Tamaño: $decodedTamano")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón volver
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF1E88E5)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Volver",
                color = Color(0xFF1E88E5),
                fontWeight = FontWeight.Bold
            )
        }

        // Botón para editar o eliminar el carnet
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = {
                    isDialogOpen = true
                    currentCarnet = Carnet(decodedNombre, decodedRaza, decodedTamano, decodedUrl)
                }
            ) {
                Text("Editar / Añadir")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    currentCarnet?.let {
                        removeCarnet(it)
                    }
                }
            ) {
                Text("Eliminar")
            }
        }

        // Mostrar lista de carnets
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            carnetList.forEach { carnet ->
                Text("${carnet.nombre}, ${carnet.raza}, ${carnet.tamano}")
            }
        }
    }

    // Diálogo para editar o añadir un carnet
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            title = { Text("Editar / Añadir Carnet") },
            text = {
                Column {
                    OutlinedTextField(
                        value = currentCarnet?.nombre ?: "",
                        onValueChange = { currentCarnet = currentCarnet?.copy(nombre = it) },
                        label = { Text("Nombre") }
                    )
                    OutlinedTextField(
                        value = currentCarnet?.raza ?: "",
                        onValueChange = { currentCarnet = currentCarnet?.copy(raza = it) },
                        label = { Text("Raza") }
                    )
                    OutlinedTextField(
                        value = currentCarnet?.tamano ?: "",
                        onValueChange = { currentCarnet = currentCarnet?.copy(tamano = it) },
                        label = { Text("Tamaño") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        currentCarnet?.let {
                            addOrUpdateCarnet(it)
                            isDialogOpen = false
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isDialogOpen = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// Texto principal con sombra
@Composable
fun TitleText(text: String, fontSize: TextUnit) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        style = TextStyle(
            shadow = Shadow(
                color = Color(0x55000000),
                offset = Offset(2f, 2f),
                blurRadius = 4f
            )
        )
    )
}

// Texto secundario
@Composable
fun InfoText(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = Color.White,
        style = TextStyle(
            shadow = Shadow(
                color = Color(0x33000000),
                offset = Offset(1f, 1f),
                blurRadius = 2f
            )
        )
    )
}

// Datos del carnet
data class Carnet(
    val nombre: String,
    val raza: String,
    val tamano: String,
    val imagenUrl: String
)
