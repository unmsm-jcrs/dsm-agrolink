//AgregarCultivoActivity.kt
package com.unmsm.agrolink.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModel

@Composable
fun AgregarCultivoActivity(
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModel.Factory)
) {
    var tipoCultivo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var fechaSiembra by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar cultivo") },
                backgroundColor = Color(0xFF00695C),
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = tipoCultivo,
                onValueChange = { tipoCultivo = it },
                label = { Text("Tipo de cultivo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad (Hectáreas)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = fechaSiembra,
                onValueChange = { fechaSiembra = it },
                label = { Text("Fecha de siembra") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { /* Cancelar */ }, colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) {
                    Text("Cancelar")
                }
                Button(onClick = {
                    cultivoViewModel.agregarCultivo(tipoCultivo, cantidad.toIntOrNull() ?: 0, fechaSiembra)
                }) {
                    Text("Guardar")
                }
            }
        }
    }
}
