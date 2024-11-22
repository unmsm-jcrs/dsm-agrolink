// ActividadViewModelFactory.kt

package com.unmsm.agrolink.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unmsm.agrolink.viewmodel.ActividadViewModel

class ActividadViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActividadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActividadViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}