package com.example.formulario.ui.theme

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formulario.ui.screen.Pantalla1
import com.example.formulario.ui.screen.Pantalla2

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // Lista compartida de mascotas guardadas
    val mascotas = remember { mutableStateListOf<List<String>>() }

    NavHost(navController = navController, startDestination = "Pantalla1") {
        composable("Pantalla1") {
            Pantalla1(navController, mascotas)
        }
        composable("Pantalla2") {
            Pantalla2(navController, mascotas)
        }
    }
}
