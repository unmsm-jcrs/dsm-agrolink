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

                val jsonObject = JSONObject(response)
                if (jsonObject.has("cod") && jsonObject.getInt("cod") != 200) {
                    println("Error en la API: ${jsonObject.getString("message")}")
                    return@withContext null
                }

                val main = jsonObject.getJSONObject("main")
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
                val wind = jsonObject.getJSONObject("wind")

                mapOf(
                    "temp" to main.getDouble("temp"),
                    "feels_like" to main.getDouble("feels_like"),
                    "humidity" to main.getInt("humidity"),
                    "pressure" to main.getInt("pressure"),
                    "description" to weather.getString("description"),
                    "wind_speed" to wind.getDouble("speed")
                )
            } catch (e: Exception) {
                println("Excepción en la solicitud de API: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
}
