// AuthViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.unmsm.agrolink.data.DatabaseHelper

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    // Método para iniciar sesión
    fun login(email: String, contrasena: String): Int? {
        return dbHelper.verificarCredenciales(email, contrasena)
    }
}