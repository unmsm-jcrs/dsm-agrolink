package com.unmsm.agrolink.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.unmsm.agrolink.R
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModelFactory

class AgregarCultivoActivity : AppCompatActivity() {

    private val cultivoViewModel: CultivoViewModel by viewModels {
        CultivoViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_cultivo)

        val tipoCultivoEditText = findViewById<EditText>(R.id.editTextTipoCultivo)
        val cantidadEditText = findViewById<EditText>(R.id.editTextCantidad)
        val fechaSiembraEditText = findViewById<EditText>(R.id.editTextFechaSiembra)
        val guardarButton = findViewById<Button>(R.id.buttonGuardar)
        val cancelarButton = findViewById<Button>(R.id.buttonCancelar)

        guardarButton.setOnClickListener {
            val tipoCultivo = tipoCultivoEditText.text.toString()
            val cantidad = cantidadEditText.text.toString().toIntOrNull()
            val fechaSiembra = fechaSiembraEditText.text.toString()

            if (tipoCultivo.isNotEmpty() && cantidad != null && fechaSiembra.isNotEmpty()) {
                cultivoViewModel.agregarCultivo(1, tipoCultivo, cantidad, fechaSiembra) // `1` es el `userId` para pruebas
                mostrarMensajeConfirmacion()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Por favor, completa todos los campos")
                    .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        cancelarButton.setOnClickListener {
            finish()
        }
    }

    private fun mostrarMensajeConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Se agregó a tus Cultivos")
            .setMessage("El nuevo cultivo se agregó exitosamente a tu lista de cultivos.")
            .setPositiveButton("Confirmar") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }
}
