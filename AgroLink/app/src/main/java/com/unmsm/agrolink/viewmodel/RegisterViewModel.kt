// RegisterViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.unmsm.agrolink.data.DatabaseHelper
import android.util.Log

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    fun register(nombre: String, email: String, password: String): Boolean {
        Log.d("RegisterViewModel", "Intentando registrar usuario con email: $email")
        val result = dbHelper.registerUser(nombre, email, password)
        Log.d("RegisterViewModel", "Resultado del registro: $result")
        return result
    }
}