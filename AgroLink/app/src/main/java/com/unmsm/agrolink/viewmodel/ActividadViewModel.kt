// ActividadViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unmsm.agrolink.data.DatabaseHelper
import com.unmsm.agrolink.models.Actividad

class ActividadViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DatabaseHelper(application)

    private val _actividades = MutableLiveData<List<Actividad>>()
    val actividades: LiveData<List<Actividad>> get() = _actividades

    // Carga las actividades para un cultivo específico
    fun loadActividades(idCultivo: Int) {
        _actividades.value = dbHelper.getActividadesPorCultivo(idCultivo)
    }


    // Agregar una nueva actividad a un cultivo
    fun agregarActividad(idCultivo: Int, tipoActividad: String, fecha: String, notas: String, cantidad: Double? = null) {
        dbHelper.insertActividad(idCultivo, tipoActividad, fecha, notas, cantidad)
        loadActividades(idCultivo)  // Recarga las actividades después de agregar una nueva
    }

    fun eliminarActividad(idActividad: Int, idCultivo: Int) {
        dbHelper.deleteActividad(idActividad)
        loadActividades(idCultivo)
    }
}