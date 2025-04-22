package com.example.parcial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.net.URLDecoder

@Composable
fun CarnetScreen(
    navController: NavController,
    nombre: String,
    raza: String,
    tamano: String,
    imagenUrl: String
) {
    val decodedNombre = URLDecoder.decode(nombre, "UTF-8")
    val decodedRaza = URLDecoder.decode(raza, "UTF-8")
    val decodedTamano = URLDecoder.decode(tamano, "UTF-8")
    val decodedUrl = URLDecoder.decode(imagenUrl, "UTF-8")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4FC)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(500.dp), // aumentamos para que entre el botón
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Carnet de Mascota",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A73E8)
                )

                Image(
                    painter = rememberAsyncImagePainter(decodedUrl),
                    contentDescription = "Foto de la mascota",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Text("Nombre: $decodedNombre", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Text("Raza: $decodedRaza", fontSize = 16.sp)
                Text("Tamaño: $decodedTamano", fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver al formulario")
                }
            }
        }
    }
}
