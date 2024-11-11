// Actividad.kt

package com.unmsm.agrolink.models

data class Actividad(
    val idActividad: Int,
    val idCultivo: Int,
    val tipoActividad: String,
    val fecha: String,
    val nota: String?,
    val cantidad: Double? = null
)