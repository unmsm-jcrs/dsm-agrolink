package com.unmsm.agrolink.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.unmsm.agrolink.models.TipoActividad
import com.unmsm.agrolink.ui.components.ButtonSize
import com.unmsm.agrolink.ui.components.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarActividadActivity(
    cultivoId: Int,
    onNavigateBack: () -> Unit,
    actividadViewModel: ActividadViewModel = viewModel()
) {
    val tipoActividades = TipoActividad.values().toList()
    var tipoActividad by remember { mutableStateOf(tipoActividades.first().nombre) }
    var fechaActividad by remember { mutableStateOf("") }
    var notasActividad by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Selecciona el tipo de actividad",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier) {
                    tipoActividades.forEach { item ->
                        Row(
                            modifier = Modifier.selectable(
                                selected = tipoActividad == item.nombre,
                                onClick = { tipoActividad = item.nombre }
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = tipoActividad == item.nombre,
                                onClick = { tipoActividad = item.nombre }
                            )
                            Text(item.nombre)
                        }
                    }
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }

            OutlinedTextField(
                value = notasActividad,
                onValueChange = { notasActividad = it },
                label = { Text("Notas de la actividad") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                CustomButton(
                    onClick = { onNavigateBack() },
                    buttonText = "Cancelar",
                    modifier = Modifier,
                    type = 3,
                    size = ButtonSize.Large
                )
                CustomButton(
                    onClick = {
                        if (fechaActividad.isEmpty()) {
                            errorMessage = "Por favor, selecciona una fecha para la actividad."
                        } else if (notasActividad.isBlank()) {
                            errorMessage = "Por favor, ingresa una descripción o notas de la actividad."
                        } else {
                            actividadViewModel.agregarActividad(
                                idCultivo = cultivoId,
                                tipoActividad = tipoActividad,
                                fecha = fechaActividad,
                                notas = notasActividad
                            )
                            showConfirmation = true
                            errorMessage = ""
                        }
                    },
                    buttonText = "Guardar",
                    modifier = Modifier,
                    type = 1,
                    size = ButtonSize.Large
                )
            }
        }
    }

    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            title = { Text("Actividad agregada") },
            text = { Text("La actividad fue agregada exitosamente al cultivo.") },
            confirmButton = {
                Button(onClick = {
                    showConfirmation = false
                    onNavigateBack()
                }) {
                    Text("Confirmar")
                }
            }
        )
    }
}
