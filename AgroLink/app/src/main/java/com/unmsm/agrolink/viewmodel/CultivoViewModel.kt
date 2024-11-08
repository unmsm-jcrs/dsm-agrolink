package com.unmsm.agrolink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unmsm.agrolink.data.Cultivo
import com.unmsm.agrolink.data.DatabaseHelper

class CultivoViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DatabaseHelper(application)
    private val cultivosLiveData = MutableLiveData<List<Cultivo>>()

    init {
        cultivosLiveData.value = dbHelper.getCultivos()
    }

    fun getCultivos(): LiveData<List<Cultivo>> {
        return cultivosLiveData
    }

    fun agregarCultivo(userId: Int, tipo: String, cantidad: Int, fecha: String) {
        dbHelper.insertCultivo(userId, tipo, cantidad, fecha)
        cultivosLiveData.value = dbHelper.getCultivos()
    }
}
