// MainActivity.kt

package com.unmsm.agrolink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.unmsm.agrolink.ui.*
import com.unmsm.agrolink.ui.theme.AgroLinkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.unmsm.agrolink.ui.components.BottonBar
import com.unmsm.agrolink.ui.components.TopBar
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AgroLinkTheme {
                AgroLinkApp()
            }
        }
    }
}

@Composable
fun AgroLinkApp(
    navController: NavHostController = rememberNavController()
) {
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
            topBar = { TopBar() },
            bottomBar = { BottonBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "misCultivos",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("misCultivos") {
                    MisCultivosActivity(
                        idUsuario = userId!!,
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
                        cultivoId = cultivoId,
                        onNavigateToAgregarActividad = {
                            navController.navigate("agregarActividad/$cultivoId")
                        }
                    )
                }
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
