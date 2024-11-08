package com.unmsm.agrolink.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.unmsm.agrolink.R
import com.unmsm.agrolink.data.Cultivo
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModelFactory
import kotlinx.android.synthetic.main.activity_detalle_cultivo.*

class DetalleCultivoActivity : AppCompatActivity() {

    private lateinit var cultivoViewModel: CultivoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cultivo)

        // Inicializar el ViewModel
        cultivoViewModel = ViewModelProvider(this, CultivoViewModelFactory(application)).get(CultivoViewModel::class.java)

        // Recibir los datos del cultivo a través del Intent
        val cultivoId = intent.getIntExtra("cultivoId", -1)
        if (cultivoId == -1) {
            Toast.makeText(this, "Cultivo no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cargar los datos del cultivo
        cultivoViewModel.getCultivoById(cultivoId).observe(this) { cultivo ->
            if (cultivo != null) {
                mostrarDatosCultivo(cultivo)
            } else {
                Toast.makeText(this, "Error al cargar los datos del cultivo", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Configurar botón para agregar actividad
        buttonAgregarActividad.setOnClickListener {
            // Aquí puedes añadir la lógica para agregar una actividad
            Toast.makeText(this, "Funcionalidad para agregar actividad no implementada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDatosCultivo(cultivo: Cultivo) {
        textViewTipoCultivo.text = cultivo.tipo
        textViewFechaSiembra.text = "Plantado el ${cultivo.fechaSiembra}"
        textViewCantidad.text = "${cultivo.cantidad} Hectáreas"
    }
}
