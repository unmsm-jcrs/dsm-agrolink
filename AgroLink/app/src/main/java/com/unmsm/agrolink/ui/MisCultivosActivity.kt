// MisCultivosActivity.kt

package com.unmsm.agrolink.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.unmsm.agrolink.R
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModelFactory

@Composable
fun MisCultivosActivity(
    idUsuario: Int,
    onNavigateToAgregarCultivo: () -> Unit,
    onNavigateToDetalleCultivo: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    val cultivos by cultivoViewModel.cultivos.observeAsState(emptyList())
    var isDeleteMode by remember { mutableStateOf(false) } // Estado para el modo de eliminación

    LaunchedEffect(idUsuario) {
        cultivoViewModel.loadCultivos(idUsuario)
    }

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            Text(
                text = "Mis cultivos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onNavigateToAgregarCultivo,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF80CBC4))
                ) {
                    Text("+ Agregar cultivo")
                }

                Button(
                    onClick = { isDeleteMode = !isDeleteMode },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2))
                ) {
                    Text("Eliminar cultivo")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(cultivos) { cultivo ->
                    CultivoItem(
                        cultivo = cultivo,
                        isDeleteMode = isDeleteMode,
                        onDelete = {
                            cultivoViewModel.eliminarCultivo(cultivo.idCultivo, idUsuario)
                            isDeleteMode = false // Desactivar el modo de eliminación después de eliminar
                        },
                        onClick = {
                            if (!isDeleteMode) {
                                onNavigateToDetalleCultivo(cultivo.idCultivo)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CultivoItem(
    cultivo: Cultivo,
    isDeleteMode: Boolean,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (isDeleteMode) {
                    onDelete()
                } else {
                    onClick()
                }
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            if (isDeleteMode) Color(0xFFFFCDD2) else MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_my_crops),
                contentDescription = "Imagen del cultivo",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f) // Hace que la columna ocupe el espacio disponible
            ) {
                Text(
                    text = cultivo.tipoCultivo,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiary
                )
                Text(
                    text = "${cultivo.cantidad} Hectáreas", // Muestra el número de hectáreas
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
            Text(
                text = cultivo.fechaSiembra, // Muestra la fecha de siembra a la derecha
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.align(Alignment.CenterVertically) // Centra verticalmente el texto
            )
        }
    }
}

