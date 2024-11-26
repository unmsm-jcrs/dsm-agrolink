package com.unmsm.agrolink.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClimaScreen(fetchWeatherData: suspend () -> Map<String, Any>?) {
    // Variables de estado
    var stage by remember { mutableStateOf("initial") }
    var temperature by remember { mutableStateOf("Cargando...") }
    var humidity by remember { mutableStateOf("Cargando...") }
    var weatherCondition by remember { mutableStateOf("Cargando...") }
    var errorMessage by remember { mutableStateOf("") }


    Scaffold{ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Llenar el ancho disponible
                .padding(20.dp),
        ) {
            Text(
                text = "Ver Clima",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))

            LaunchedEffect(Unit) {
                stage = "loading"
                val data = fetchWeatherData()
                if (data != null) {
                    stage = "loaded"
                    temperature = "${data["temp"]} °C"
                    humidity = "${data["humidity"]} %"
                    weatherCondition = data["description"] as String
                } else {
                    stage = "error"
                    errorMessage = "No se pudo cargar el clima. Verifica tu conexión o clave API."
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(16.dp)
            ) {
                when (stage) {
                    "initial" -> Text("¡Mira el clima!", style = MaterialTheme.typography.displayLarge)
                    "loading" -> CircularProgressIndicator()
                    "loaded" -> Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Clima Actual", style = MaterialTheme.typography.displayLarge)
                        Text( "$temperature", fontSize = 40.sp)
                        Text("Humedad: $humidity", fontSize = 20.sp)
                        Text("Condición: $weatherCondition", fontSize = 20.sp)
                    }
                    "error" -> Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
