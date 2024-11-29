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



    fun fetchTotalCultivos(idUsuario: Int) {
        val cultivos = databaseHelper.getCultivosPorUsuario(idUsuario)
        val total = cultivos.filter { it.estado == 8 }  // estado != 1 -> "sin cosecha"
        _totalCultivos.postValue(total.size)
    }

    fun fetchTotalCosechas(idUsuario: Int) {
        val cultivos = databaseHelper.getCultivosPorUsuario(idUsuario)
        val total = cultivos.filter { it.estado == 1 }  // estado == 1 -> "cosechado"
        _totalCosechas.postValue(total.size)
    }

    fun fetchTotalDesechados(idUsuario: Int) {
        val cultivos = databaseHelper.getCultivosPorUsuario(idUsuario)
        val total = cultivos.filter { it.estado == 2 }  // estado == 2 -> "desechado"
        _totalDesechados.postValue(total.size)
    }
}