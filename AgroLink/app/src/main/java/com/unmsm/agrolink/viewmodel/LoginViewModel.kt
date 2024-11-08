// LoginViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.unmsm.agrolink.data.DatabaseHelper

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    fun login(email: String, password: String): Int? {
        return dbHelper.verificarCredenciales(email, password)
    }
}