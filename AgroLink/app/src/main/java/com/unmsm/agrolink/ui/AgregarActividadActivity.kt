// AgregarActividadActivity.kt

package com.unmsm.agrolink.ui

import android.app.Application
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.viewmodel.ActividadViewModel
import com.unmsm.agrolink.viewmodel.ActividadViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarActividadActivity(
    cultivoId: Int,
    onNavigateBack: () -> Unit,
    actividadViewModel: ActividadViewModel = viewModel(factory = ActividadViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    var tipoActividad by remember { mutableStateOf("") }
    var fechaActividad by remember { mutableStateOf("") }
    var notasActividad by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar actividad") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00695C),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Tipo de actividad",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF00695C)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    RadioButtonItem("Riego", tipoActividad) { tipoActividad = "Riego" }
                    RadioButtonItem("Fertilización", tipoActividad) { tipoActividad = "Fertilización" }
                    RadioButtonItem("Control de Plagas", tipoActividad) { tipoActividad = "Control de Plagas" }
                    RadioButtonItem("Control de Enfermedades", tipoActividad) { tipoActividad = "Control de Enfermedades" }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = fechaActividad,
                    onValueChange = { },
                    label = { Text("Fecha de la actividad") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { showDatePicker = true }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = notasActividad,
                    onValueChange = { notasActividad = it },
                    label = { Text("Notas de la actividad") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Cancelar")
                }
                Button(
                    onClick = {
                        if (tipoActividad.isNotBlank() && fechaActividad.isNotBlank()) {
                            actividadViewModel.agregarActividad(
                                cultivoId = cultivoId,
                                tipoActividad = tipoActividad,
                                fecha = fechaActividad,
                                notas = notasActividad
                            )
                            showConfirmation = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
                ) {
                    Text("Guardar")
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                fechaActividad = formatter.format(calendar.time)
                showDatePicker = false
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )

        DisposableEffect(Unit) {
            datePickerDialog.show()
            onDispose { datePickerDialog.dismiss() }
        }
    }

    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            title = { Text("Actividad agregada") },
            text = { Text("La nueva actividad se agregó exitosamente al cultivo.") },
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

@Composable
fun RadioButtonItem(text: String, selectedOption: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        RadioButton(
            selected = text == selectedOption,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF00695C))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}