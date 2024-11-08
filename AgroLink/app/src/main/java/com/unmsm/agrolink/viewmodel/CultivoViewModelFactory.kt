// CultivoViewModelFactory.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CultivoViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CultivoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CultivoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}