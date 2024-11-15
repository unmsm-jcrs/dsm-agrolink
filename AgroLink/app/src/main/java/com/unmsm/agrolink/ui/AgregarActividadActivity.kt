// AgregarActividadActivity.kt
package com.unmsm.agrolink.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.viewmodel.ActividadViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.ui.Alignment

@Composable
fun AgregarActividadActivity(
    cultivoId: Int,
    onNavigateBack: () -> Unit,
    actividadViewModel: ActividadViewModel = viewModel()
) {
    var tipoActividad by remember { mutableStateOf("Riego") } // Tipo de actividad predeterminado
    var fechaActividad by remember { mutableStateOf("") }

    // Date picker para la fecha de la actividad
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, day ->
            calendar.set(year, month, day)
            fechaActividad = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Selecciona el tipo de actividad",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    RadioButtonItem(text = "Riego", selectedOption = tipoActividad) { tipoActividad = it }
                    RadioButtonItem(text = "Fertilización", selectedOption = tipoActividad) { tipoActividad = it }
                    RadioButtonItem(text = "Control de Plagas", selectedOption = tipoActividad) { tipoActividad = it }
                    RadioButtonItem(text = "Control de Enfermedades", selectedOption = tipoActividad) { tipoActividad = it }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = fechaActividad,
                    onValueChange = {},
                    label = { Text("Fecha de la actividad") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            var notasActividad by remember { mutableStateOf("") }

            TextField(
                value = notasActividad,
                onValueChange = { notasActividad = it },
                label = { Text("Notas de la actividad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    onNavigateBack() // Regresa a la pantalla anterior
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Actividad")
            }
        }
    }
}

@Composable
fun RadioButtonItem(text: String, selectedOption: String, onSelect: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        RadioButton(
            selected = (text == selectedOption),
            onClick = { onSelect(text) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }

}
