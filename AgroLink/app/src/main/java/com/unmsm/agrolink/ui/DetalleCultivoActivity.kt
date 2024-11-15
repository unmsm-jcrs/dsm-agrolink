// DetalleCultivoActivity.kt

package com.unmsm.agrolink.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unmsm.agrolink.models.Actividad
import com.unmsm.agrolink.viewmodel.ActividadViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun DetalleCultivoActivity(
    cultivoId: Int,
    onNavigateToAgregarActividad: () -> Unit,
    actividadViewModel: ActividadViewModel = viewModel()
) {
    val actividades by actividadViewModel.actividades.observeAsState(emptyList())

    LaunchedEffect(cultivoId) {
        actividadViewModel.loadActividades(cultivoId)
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Mi Cultivo", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Botones de agregar y eliminar actividad (reducidos)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onNavigateToAgregarActividad,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF80CBC4)),
                    modifier = Modifier
                        .height(36.dp) // Altura del botón reducida
                        .widthIn(min = 120.dp) // Ancho mínimo ajustado
                ) {
                    Text("+ Agregar actividad", fontSize = 12.sp) // Tamaño de fuente reducido
                }

                Button(
                    onClick = { /* Acción de eliminar actividad */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
                    modifier = Modifier
                        .height(36.dp) // Altura del botón reducida
                        .widthIn(min = 120.dp) // Ancho mínimo ajustado
                ) {
                    Text("Eliminar actividad", fontSize = 12.sp) // Tamaño de fuente reducido
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Actividad", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(actividades) { actividad ->
                    ActivityItem(actividad)
                }
            }
        }
    }
}

@Composable
fun ActivityItem(actividad: Actividad) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = actividad.tipoActividad,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = actividad.fecha,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
