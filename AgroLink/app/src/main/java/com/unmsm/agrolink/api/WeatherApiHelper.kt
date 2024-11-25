package com.unmsm.agrolink.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

object WeatherApiHelper {
    private const val API_KEY = "4864f216a1aabb560ae6fcfc469e31d0"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"

    suspend fun fetchWeatherData(city: String = "Lima"): Map<String, Any>? {
        return withContext(Dispatchers.IO) {
            try {
                val url = "$BASE_URL?q=$city&units=metric&appid=$API_KEY"
                val response = URL(url).readText()

                // Procesar respuesta JSON
                val jsonObject = JSONObject(response)

                // Validar código de respuesta
                if (jsonObject.has("cod") && jsonObject.getInt("cod") != 200) {
                    println("Error en la API: ${jsonObject.getString("message")}")
                    return@withContext null
                }

                // Extraer datos del clima
                val main = jsonObject.getJSONObject("main")
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0)

                mapOf(
                    "temp" to main.getDouble("temp"),
                    "humidity" to main.getInt("humidity"),
                    "description" to weather.getString("description")
                )
            } catch (e: Exception) {
                println("Excepción en la solicitud de API: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
}
