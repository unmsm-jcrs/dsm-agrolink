// MisCultivosActivity.kt

package com.unmsm.agrolink.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.unmsm.agrolink.R
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.ui.components.ButtonSize
import com.unmsm.agrolink.ui.components.CustomButton
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

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Mis cultivos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                CustomButton(
                    onClick = { onNavigateToAgregarCultivo() },
                    buttonText = "Agregar cultivo",
                    modifier = Modifier.weight(1f),
                    type = 2,
                    size = ButtonSize.Medium,
                    icon = Icons.Default.Add
                )
                
                CustomButton(
                    onClick = { isDeleteMode = !isDeleteMode },
                    buttonText = "Eliminar cultivo",
                    modifier = Modifier.weight(1f),
                    type = 4,
                    size = ButtonSize.Medium,
                    icon = Icons.Default.Delete
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            LazyColumn(
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
            if (isDeleteMode) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.tertiary
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
                    color = if (isDeleteMode) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary
                )
                Text(
                    text = "${cultivo.cantidad} Hectáreas", // Muestra el número de hectáreas
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDeleteMode) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary
                )
            }
            Text(
                text = cultivo.fechaSiembra, // Muestra la fecha de siembra a la derecha
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDeleteMode) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.align(Alignment.Bottom) // Centra verticalmente el texto
            )
        }
    }
}

