// CultivoViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unmsm.agrolink.data.DatabaseHelper
import com.unmsm.agrolink.models.Cultivo

class CultivoViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DatabaseHelper(application)

    private val _cultivos = MutableLiveData<List<Cultivo>>()
    val cultivos: LiveData<List<Cultivo>> get() = _cultivos

    // Método para cargar cultivos de un usuario específico
    fun loadCultivos(idUsuario: Int) {
        _cultivos.value = dbHelper.getCultivosPorUsuario(idUsuario)
    }

    // Método para agregar cultivo y recargar la lista
    fun agregarCultivo(idUsuario: Int, tipoCultivo: String, cantidad: Double, fechaSiembra: String) {
        dbHelper.insertCultivo(idUsuario, tipoCultivo, cantidad, fechaSiembra)
        loadCultivos(idUsuario) // Recargar cultivos después de agregar uno nuevo
    }

    fun getCultivoById(idCultivo: Int): LiveData<Cultivo?> {
        val cultivoLiveData = MutableLiveData<Cultivo?>()
        cultivoLiveData.value = dbHelper.getCultivoById(idCultivo)
        return cultivoLiveData
    }
}