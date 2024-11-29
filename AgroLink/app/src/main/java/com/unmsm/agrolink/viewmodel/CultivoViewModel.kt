// CultivoViewModel.kt

package com.unmsm.agrolink.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.data.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CultivoViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    private val _cultivos = MutableLiveData<List<Cultivo>>()
    val cultivos: LiveData<List<Cultivo>> get() = _cultivos

    // Método para cargar cultivos de un usuario específico
    fun loadCultivos(idUsuario: Int) {
        Log.d("CultivoViewModel", "Cargando cultivos para el usuario $idUsuario")
        _cultivos.value = dbHelper.getCultivosPorUsuario(idUsuario)
        Log.d("CultivoViewModel", "Cultivos cargados: ${_cultivos.value}")
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun cosecharCultivo(idCultivo: Int, idUsuario: Int) {
        Log.d("CultivoViewModel", "Cosechando cultivo con id: $idCultivo")
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val fechaCosechaActual = obtenerFechaActual() // Deberia poder reemplazarse por la fecha que se indique
                    Log.d("CultivoViewModel", "Fecha de cosecha: $fechaCosechaActual")
                    dbHelper.actualizarCultivo( // Falta completar la funcion actualizarCultivo en Create_DB
                        idCultivo = idCultivo,
                        nuevoEstado = 1, // Estado 1: "Cosechado"
                        nuevaVisibilidad = 0, // Ya no se ve
                        nuevaFechaCosechado = fechaCosechaActual
                    )
                    Log.d("CultivoViewModel", "Cultivo cosechado correctamente.")
                }
                // Actualiza la lista de cultivos
                _cultivos.value = _cultivos.value?.filter { it.idCultivo != idCultivo }
                loadCultivos(idUsuario)
            } catch (e: Exception) {
                Log.e("CultivoViewModel", "Error al cosechar el cultivo: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun desecharCultivo(idCultivo: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val fechaCosechaActual = obtenerFechaActual() // Deberia poder reemplazarse por la fecha que se indique
                    dbHelper.actualizarCultivo( // Falta completar la funcion actualizarCultivo en Create_DB
                        idCultivo = idCultivo,
                        nuevoEstado = 2, // Estado 2: "Malogrado"
                        nuevaVisibilidad = 0, // Ya no se ve
                        nuevaFechaCosechado = fechaCosechaActual
                    )
                }
                // Actualiza la lista de cultivos
                _cultivos.value = _cultivos.value?.filter { it.idCultivo != idCultivo }
                loadCultivos(idUsuario)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerFechaActual(): String {
        val fechaActual = LocalDate.now()
        val formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return fechaActual.format(formatoFecha)
    }
}
