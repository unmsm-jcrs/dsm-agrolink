// CultivoViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.data.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CultivoViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    private val _cultivos = MutableLiveData<List<Cultivo>>()
    val cultivos: LiveData<List<Cultivo>> get() = _cultivos

    // Método para cargar cultivos de un usuario específico
    fun loadCultivos(idUsuario: Int) {
        _cultivos.value = dbHelper.getCultivosPorUsuario(idUsuario)
    }

    // Método para agregar cultivo y recargar la lista
    fun agregarCultivo(
        idUsuario: Int,
        tipoCultivo: String,
        cantidad: Double,
        fechaSiembra: String,
        estado: Int = 8, // Por defecto, "en proceso"
        visibilidad: Int = 1, // Por defecto, "visible"
        fechaCosechado: String = "Pendiente" // Por defecto, algún valor representativo
    ) {
        dbHelper.insertCultivo(
            idUsuario = idUsuario,
            tipoCultivo = tipoCultivo,
            cantidad = cantidad,
            fechaSiembra = fechaSiembra,
            estado = estado,
            visibilidad = visibilidad,
            fechaCosechado = fechaCosechado
        )
        loadCultivos(idUsuario) // Recargar cultivos después de agregar uno nuevo
    }


    // Método para eliminar un cultivo por ID y recargar la lista
    fun eliminarCultivo(idCultivo: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    dbHelper.deleteCultivo(idCultivo) // Elimina de la base de datos
                }

                // Elimina el cultivo de la lista en memoria antes de recargar la lista
                _cultivos.value = _cultivos.value?.filter { it.idCultivo != idCultivo }

                // Opcional: Actualiza la lista desde la base de datos si es necesario
                loadCultivos(idUsuario)

            } catch (e: Exception) {
                e.printStackTrace() // Manejo de error en caso de excepción
            }
        }
    }


    fun obtenerCultivo(idCultivo: Int): LiveData<Cultivo?> {
        val cultivoLiveData = MutableLiveData<Cultivo?>()
        cultivoLiveData.value = dbHelper.getCultivoById(idCultivo)
        return cultivoLiveData
    }
}
