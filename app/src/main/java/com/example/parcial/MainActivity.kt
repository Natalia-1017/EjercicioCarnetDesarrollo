package com.example.parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.parcial.screens.CarnetScreen
import com.example.parcial.screens.FormularioScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(navController = navController, startDestination = "formulario") {
                    composable("formulario") {
                        FormularioScreen(navController)
                    }
                    composable(
                        route = "carnet/{nombre}/{raza}/{tamano}/{imagenUrl}",
                        arguments = listOf(
                            navArgument("nombre") { type = NavType.StringType },
                            navArgument("raza") { type = NavType.StringType },
                            navArgument("tamano") { type = NavType.StringType },
                            navArgument("imagenUrl") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        CarnetScreen(
                            navController = navController,
                            nombre = backStackEntry.arguments?.getString("nombre") ?: "",
                            raza = backStackEntry.arguments?.getString("raza") ?: "",
                            tamano = backStackEntry.arguments?.getString("tamano") ?: "",
                            imagenUrl = backStackEntry.arguments?.getString("imagenUrl") ?: ""
                        )
                    }
                }
            }
        }
    }
}
