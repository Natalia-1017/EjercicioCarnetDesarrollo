package com.example.parcial.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parcial.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FormularioScreen(navController: NavController) {
    val context = LocalContext.current

    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var raza by remember { mutableStateOf(TextFieldValue("")) }
    var tamano by remember { mutableStateOf(TextFieldValue("")) }
    var edad by remember { mutableStateOf(TextFieldValue("")) }
    var imagenUrl by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2F7)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_mascota),
                        contentDescription = "Logo Mascota",
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Registro de Mascota",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = raza,
                    onValueChange = { raza = it },
                    label = { Text("Raza") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tamano,
                    onValueChange = { tamano = it },
                    label = { Text("Tamaño") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("URL de imagen") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (nombre.text.isNotBlank() && raza.text.isNotBlank() && imagenUrl.text.isNotBlank()) {
                            val encodedNombre = URLEncoder.encode(nombre.text, StandardCharsets.UTF_8.toString())
                            val encodedRaza = URLEncoder.encode(raza.text, StandardCharsets.UTF_8.toString())
                            val encodedTamano = URLEncoder.encode(tamano.text, StandardCharsets.UTF_8.toString())
                            val encodedUrl = URLEncoder.encode(imagenUrl.text, StandardCharsets.UTF_8.toString())

                            navController.navigate("carnet/$encodedNombre/$encodedRaza/$encodedTamano/$encodedUrl")
                        } else {
                            Toast.makeText(context, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Text("Registrar Mascota", color = Color.White)
                }
            }
        }
    }
}
