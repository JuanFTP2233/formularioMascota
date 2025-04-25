package com.example.formulario.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Pantalla1(navController: NavController, mascotas: MutableList<List<String>>) {
    var nombre by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var tamano by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }
    val context = LocalContext.current
    val mascotaIndex = navController.currentBackStackEntry?.arguments?.getString("mascotaIndex")?.toIntOrNull()


    if (mascotaIndex != null && mascotaIndex >= 0 && mascotaIndex < mascotas.size) {
        val mascota = mascotas[mascotaIndex]
        nombre = mascota[0]
        raza = mascota[1]
        tamano = mascota[2]
        edad = mascota[3]
        fotoUrl = mascota[4]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Formulario de Mascota", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = raza,
            onValueChange = { raza = it },
            label = { Text("Raza") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tamano,
            onValueChange = { tamano = it },
            label = { Text("Tamaño") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fotoUrl,
            onValueChange = { fotoUrl = it },
            label = { Text("URL de la Foto") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (fotoUrl.isNotBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(fotoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Foto de la mascota",
                modifier = Modifier.size(150.dp)
            )
        }

        Button(
            onClick = {
                if (mascotaIndex != null && mascotaIndex >= 0 && mascotaIndex < mascotas.size) {
                    mascotas[mascotaIndex] = listOf(nombre, raza, tamano, edad, fotoUrl)
                } else {
                    mascotas.add(listOf(nombre, raza, tamano, edad, fotoUrl))
                }
                navController.navigate("Pantalla2")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}
