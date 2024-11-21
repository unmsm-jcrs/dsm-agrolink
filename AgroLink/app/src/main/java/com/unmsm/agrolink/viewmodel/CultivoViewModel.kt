// CultivoViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.unmsm.agrolink.models.Cultivo
import android.database.sqlite.SQLiteDatabase
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
                    eliminarCultivoDeBaseDatos(idCultivo) // Llama al método interno para eliminar en la base de datos
                }
                // Recargar cultivos después de eliminar para asegurar la actualización de la interfaz
                loadCultivos(idUsuario)
            } catch (e: Exception) {
                e.printStackTrace() // Manejo de error en caso de excepción
            }
        }
    }

    // Método interno para eliminar el cultivo de la base de datos
    private fun eliminarCultivoDeBaseDatos(idCultivo: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.delete("Cultivo", "idCultivo = ?", arrayOf(idCultivo.toString()))
        db.close()
    }

    fun getCultivoById(idCultivo: Int): LiveData<Cultivo?> {
        val cultivoLiveData = MutableLiveData<Cultivo?>()
        cultivoLiveData.value = dbHelper.getCultivoById(idCultivo)
        return cultivoLiveData
    }
}
