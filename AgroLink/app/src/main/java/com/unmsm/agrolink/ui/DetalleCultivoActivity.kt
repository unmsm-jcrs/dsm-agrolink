// DetalleCultivoActivity.kt

package com.unmsm.agrolink.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.unmsm.agrolink.ui.components.ButtonSize
import com.unmsm.agrolink.ui.components.CustomButton

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
                .padding(20.dp)
        ) {
            Text(
                text = "Mi Cultivo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomButton(
                    onClick = { onNavigateToAgregarActividad() },
                    buttonText = "Agregar actividad",
                    modifier = Modifier
                        .weight(1f),
                    type = 2,
                    size = ButtonSize.Medium,
                    icon = Icons.Default.Add
                )

                CustomButton(
                    onClick = { /* Acción de eliminar actividad */ },
                    buttonText = "Eliminar actividad",
                    modifier = Modifier
                        .weight(1f),
                    type = 4,
                    size = ButtonSize.Medium,
                    icon = Icons.Default.Delete
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Actividad",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))

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
