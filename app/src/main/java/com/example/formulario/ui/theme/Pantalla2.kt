package com.example.formulario.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.navigation.NavController

@Composable
fun Pantalla2(navController: NavController, mascotas: MutableList<List<String>>) {
    var showDialog by remember { mutableStateOf(false) }
    var mascotaToDelete by remember { mutableStateOf<List<String>?>(null) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var nombre by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var tamano by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFEFFAF1))
    ) {
        Text(
            "Mascotas Registradas",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF1E5631),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (mascotas.isEmpty()) {
            Text(
                "No hay mascotas registradas.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(mascotas) { index, mascota ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("🐾 Nombre: ${mascota[0]}", style = MaterialTheme.typography.titleMedium)
                            Text("🦴 Raza: ${mascota[1]}")
                            Text("📏 Tamaño: ${mascota[2]}")
                            Text("🎂 Edad: ${mascota[3]} años")

                            if (mascota[4].isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(mascota[4])
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        editingIndex = index
                                        nombre = mascota[0]
                                        raza = mascota[1]
                                        tamano = mascota[2]
                                        edad = mascota[3]
                                        fotoUrl = mascota[4]
                                    },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        mascotaToDelete = mascota
                                        showDialog = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEB5757)),
                                ) {
                                    Text("Eliminar", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (editingIndex != null) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Editar Mascota", style = MaterialTheme.typography.titleLarge)

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
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(fotoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Foto de la mascota",
                        modifier = Modifier.size(150.dp)
                    )
                }

                Button(
                    onClick = {

                        if (editingIndex != null) {
                            val updatedMascota = listOf(nombre, raza, tamano, edad, fotoUrl)
                            mascotas[editingIndex!!] = updatedMascota
                        }
                        editingIndex = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar cambios")
                }
            }
        }

        Button(
            onClick = { navController.navigate("Pantalla1") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E5631)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al formulario", color = Color.White)
        }
    }


    if (showDialog && mascotaToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación de eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta mascota?") },
            confirmButton = {
                TextButton(onClick = {
                    mascotaToDelete?.let { mascota ->
                        mascotas.remove(mascota)
                    }
                    showDialog = false
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}
