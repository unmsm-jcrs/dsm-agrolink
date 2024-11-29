package com.unmsm.agrolink.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unmsm.agrolink.R

@Composable
fun ClimaScreen(fetchWeatherData: suspend () -> Map<String, Any>?) {
    var stage by remember { mutableStateOf("initial") }
    var temperature by remember { mutableStateOf("Cargando...") }
    var feelsLike by remember { mutableStateOf("Cargando...") }
    var humidity by remember { mutableStateOf("Cargando...") }
    var pressure by remember { mutableStateOf("Cargando...") }
    var windSpeed by remember { mutableStateOf("Cargando...") }
    var weatherCondition by remember { mutableStateOf("Cargando...") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        stage = "loading"
        val data = fetchWeatherData()
        if (data != null) {
            stage = "loaded"
            temperature = "${data["temp"]} °C"
            feelsLike = "${data["feels_like"]} °C"
            humidity = "${data["humidity"]} %"
            pressure = "${data["pressure"]} hPa"
            windSpeed = "${data["wind_speed"]} m/s"
            weatherCondition = data["description"] as String
        } else {
            stage = "error"
            errorMessage = "No se pudo cargar el clima. Verifica tu conexión o clave API."
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título "Clima Actual" fuera del cuadro azul
            Text(
                text = "Clima Actual",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF1565C0),
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.weather_icon),
                contentDescription = "Ícono del clima",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 24.dp),
                tint = Color.Unspecified
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF80D6FF),
                                Color(0xFF42A5F5)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                when (stage) {
                    "initial" -> Text("¡Mira el clima!", color = MaterialTheme.colorScheme.onPrimary)
                    "loading" -> CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    "loaded" -> Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Detalles dentro del cuadro azul
                        Text("Temperatura: $temperature", color = Color.White)
                        Text("Sensación térmica: $feelsLike", color = Color.White)
                        Text("Humedad: $humidity", color = Color.White)
                        Text("Presión: $pressure", color = Color.White)
                        Text("Velocidad del viento: $windSpeed", color = Color.White)
                        Text("Condición: $weatherCondition", color = Color.White)
                    }
                    "error" -> Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
