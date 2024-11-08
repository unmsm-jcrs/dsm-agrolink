//Cultivo.kt

package com.unmsm.agrolink.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivos")
data class Cultivo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,  // Foreign key to User
    val tipo: String,
    val cantidad: Int,
    val fechaSiembra: String
)
