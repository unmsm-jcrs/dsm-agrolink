// Cultivo.kt

package com.unmsm.agrolink.models

data class Cultivo(
    val idCultivo: Int,
    val idUsuario: Int,
    val tipoCultivo: String,
    val cantidad: Double,
    val fechaSiembra: String,
    val estado: String
)