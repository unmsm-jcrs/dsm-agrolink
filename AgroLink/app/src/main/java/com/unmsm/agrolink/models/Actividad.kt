// Actividad.kt

package com.unmsm.agrolink.models

import androidx.annotation.DrawableRes
import com.unmsm.agrolink.R

data class Actividad(
    val idActividad: Int,
    val idCultivo: Int,
    val tipoActividad: String, // Usamos el enum aquí
    val fecha: String,
    val nota: String?,
    val cantidad: Double? = null
)

enum class TipoActividad(
    val nombre: String,
    @DrawableRes val imagenResId: Int // ID de recurso drawable para la imagen
) {
    RIEGO("Riego", R.drawable.icon_water_tap),
    FERTILIZACION("Fertilizacion", R.drawable.icon_fertilizer),
    PLAGAS("Control de Plagas", R.drawable.icon_pest_control),
    ENFERMEDADES("Control de Enfermedades", R.drawable.icon_pest_control);

    override fun toString(): String = nombre
}

