package com.example.parcial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
