// Cultivo.kt

package com.unmsm.agrolink.models

data class Cultivo(
    val idCultivo: Int,
    val idUsuario: Int,
    val tipoCultivo: String,
    val cantidad: Double,
    val fechaSiembra: String,
    val visibilidad: Int, // Nuevo: visibilidad (0 = oculto, 1 = visible)
    val estado: Int, // Estado del cultivo
    val fechaCosechado: String // Nuevo: fecha de cosecha
)
