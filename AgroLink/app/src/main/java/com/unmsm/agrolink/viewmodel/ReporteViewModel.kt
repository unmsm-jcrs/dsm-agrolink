package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.unmsm.agrolink.data.DatabaseHelper

class ReporteViewModel(application: Application) : AndroidViewModel(application) {



    private val databaseHelper = DatabaseHelper(application)

    private val _totalCultivos = MutableLiveData<Int>()
    val totalCultivos: LiveData<Int> get() = _totalCultivos

    private val _totalCosechas = MutableLiveData<Int>()
    val totalCosechas: LiveData<Int> get() = _totalCosechas

    private val _totalDesechados = MutableLiveData<Int>()
    val totalDesechados: LiveData<Int> get() = _totalDesechados

    private val _totalActividadesPorTipo = MutableLiveData<List<Pair<String, Int>>>()
    val totalActividadesPorTipo: LiveData<List<Pair<String, Int>>> get() = _totalActividadesPorTipo

    fun fetchTotalCultivos(idUsuario: Int) {
        val total = databaseHelper.getCultivosPorUsuarioTotal(idUsuario)
        _totalCultivos.postValue(total.size)
    }

    fun fetchTotalCosechas(idUsuario: Int) {
        val cultivos = databaseHelper.getCultivosPorUsuarioTotal(idUsuario)
        val total = cultivos.filter { it.estado == 1 }  // estado == 1 -> "cosechado"
        _totalCosechas.postValue(total.size)
    }

    fun fetchTotalDesechados(idUsuario: Int) {
        val cultivos = databaseHelper.getCultivosPorUsuarioTotal(idUsuario)
        val total = cultivos.filter { it.estado == 2 }  // estado == 2 -> "desechado"
        _totalDesechados.postValue(total.size)
    }

    fun fetchTotalActividadesPorTipo(idUsuario: Int) {
        /*val actividades = databaseHelper.getActividadesPorUsuario(idUsuario)
        val actividadesPorTipo = actividades.groupBy { it.tipo } // Agrupa por tipo de actividad
            .map { Pair(it.key, it.value.size) } // Calcula la cantidad de cada tipo*/
        //Datos de prueba
        val actividadesPorTipo = listOf(
            Pair("Siembra", 5),
            Pair("Riego", 3),
            Pair("Cosecha", 7),
            Pair("Fertilización", 2),
            Pair("Control de plagas", 4)
        )


        _totalActividadesPorTipo.postValue(actividadesPorTipo)
    }

}