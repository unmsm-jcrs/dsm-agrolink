// MainActivity.kt

package com.unmsm.agrolink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.unmsm.agrolink.ui.*
import com.unmsm.agrolink.ui.theme.AgroLinkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgroLinkApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgroLinkApp() {
    AgroLinkTheme {
        val navController = rememberNavController()
        var userId by remember { mutableStateOf<Int?>(null) }

        if (userId == null) {
            // Pantallas de autenticación
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = { id ->
                            userId = id // Establece userId al iniciar sesión
                        },
                        onNavigateToRegister = { navController.navigate("register") }
                    )
                }
                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = { navController.popBackStack() }
                    )
                }
            }
        } else {
            // Contenido principal de la aplicación cuando el usuario está autenticado
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("AgroLink") },
                        actions = {
                            IconButton(onClick = {
                                // Cerrar sesión y volver a la pantalla de inicio de sesión
                                userId = null
                                navController.navigate("login") {
                                    popUpTo("misCultivos") { inclusive = true }
                                }
                            }) {
                                Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "misCultivos",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("misCultivos") {
                        MisCultivosActivity(
                            idUsuario = userId!!, // Pasa el userId como idUsuario
                            onNavigateToAgregarCultivo = { navController.navigate("agregarCultivo") },
                            onNavigateToDetalleCultivo = { cultivoId ->
                                navController.navigate("detalleCultivo/$cultivoId")
                            }
                        )
                    }
                    composable("agregarCultivo") {
                        AgregarCultivoActivity(
                            userId = userId!!,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable(
                        route = "detalleCultivo/{cultivoId}",
                        arguments = listOf(navArgument("cultivoId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val cultivoId = backStackEntry.arguments?.getInt("cultivoId") ?: 0
                        DetalleCultivoActivity(
                            userId = userId!!,
                            cultivoId = cultivoId,
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToAgregarActividad = {
                                navController.navigate("agregarActividad/$cultivoId")
                            }
                        )
                    }
                    // Agrega esta sección para navegar a AgregarActividadActivity
                    composable(
                        route = "agregarActividad/{cultivoId}",
                        arguments = listOf(navArgument("cultivoId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val cultivoId = backStackEntry.arguments?.getInt("cultivoId") ?: 0
                        AgregarActividadActivity(
                            cultivoId = cultivoId,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}