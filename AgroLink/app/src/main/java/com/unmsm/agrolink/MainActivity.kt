package com.unmsm.agrolink

import ClimaScreen
import CosechaScreen
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.unmsm.agrolink.factory.CultivoViewModelFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import com.unmsm.agrolink.api.WeatherApiHelper
import com.unmsm.agrolink.factory.ReporteViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgroLinkApp(
    navController: NavHostController = rememberNavController()
) {
    // Variable para manejar la sesión activa del usuario
    var userId by remember { mutableStateOf<Int?>(null) }

    if (userId == null) {
        // Pantallas de autenticación
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { id ->
                        userId = id // Establece el ID del usuario al iniciar sesión
                    },
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = { navController.navigate("login") },
                    onNavigateToLogin = { navController.navigate("login") }
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
                startDestination = "home", // Pantalla inicial para usuarios autenticados
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(
                        onNavigateToMisCultivos = { navController.navigate("misCultivos") }
                    )
                }
                composable("misCultivos") {
                    MisCultivosActivity(
                        idUsuario = userId!!,
                        onNavigateToAgregarCultivo = { navController.navigate("agregarCultivo") },
                        onNavigateToDetalleCultivo = { cultivoId ->
                            navController.navigate("detalleCultivo/$cultivoId")
                        }
                    )
                }
                composable("logout") {
                    LogoutScreen(
                        onLogout = {
                            restartApp(navController) // Reinicia la aplicación
                        },
                        onReporte = {
                            navController.navigate("reporte")
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
                composable("cosecha") {
                    CosechaScreen(
                        idUsuario = userId!!,
                        cultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application))
                    )
                }
                composable("reporte") {
                    ReporteScreen(
                        idUsuario = userId!!,
                        reporteViewModel = viewModel(factory = ReporteViewModelFactory(LocalContext.current.applicationContext as Application))
                    )
                }
                composable("clima") {
                    ClimaScreen(fetchWeatherData = { WeatherApiHelper.fetchWeatherData() })
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToMisCultivos: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Bienvenido a Home", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onNavigateToMisCultivos) {
                Text(text = "Ir a Mis Cultivos")
            }
        }
    }
}


@Composable
fun LogoutScreen(
    onLogout: () -> Unit,
    onReporte: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "¿Estás seguro de que quieres salir?", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onLogout) {
                Text(text = "Salir")
            }
            Button(onClick = onReporte) {
                Text(text = "Reporte")
            }
        }
    }
}


// Función para reiniciar la aplicación
fun restartApp(navController: NavHostController) {
    val context = navController.context
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
}
