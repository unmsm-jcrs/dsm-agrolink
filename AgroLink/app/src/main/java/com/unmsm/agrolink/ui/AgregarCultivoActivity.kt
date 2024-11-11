// AgregarCultivoActivity.kt

package com.unmsm.agrolink.ui

import android.app.Application
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModelFactory
import com.unmsm.agrolink.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarCultivoActivity(
    userId: Int, // Agrega el parámetro userId
    onNavigateBack: () -> Unit,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    var tipoCultivo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var fechaSiembra by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            fechaSiembra = formatter.format(selectedCalendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold() {
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Agregar cultivo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
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

            // Campo de fecha con ícono de calendario
            OutlinedTextField(
                value = fechaSiembra,
                onValueChange = {},
                label = { Text("Fecha de siembra") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Seleccionar fecha",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { datePickerDialog.show() }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Cancelar")
                }
                Button(onClick = {
                    cultivoViewModel.agregarCultivo(
                        idUsuario = userId, // Usa el userId aquí
                        tipoCultivo = tipoCultivo,
                        cantidad = cantidad.toDoubleOrNull() ?: 0.0,
                        fechaSiembra = fechaSiembra
                    )
                    showConfirmation = true
                }) {
                    Text("Guardar")
                }
            }
        }
    }

    // Mensaje de confirmación
    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            title = { Text("Cultivo agregado") },
            text = { Text("El nuevo cultivo se agregó exitosamente a la lista.") },
            confirmButton = {
                Button(onClick = {
                    showConfirmation = false
                    onNavigateBack() // Vuelve a la pantalla anterior
                }) {
                    Text("Confirmar")
                }
            }
        )
    }
}