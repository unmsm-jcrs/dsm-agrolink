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
import com.unmsm.agrolink.factory.CultivoViewModelFactory

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
    var showDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de confirmación
    var cultivoToDelete by remember { mutableStateOf<Cultivo?>(null) } // Estado para el cultivo a eliminar

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
                            cultivoToDelete = cultivo // Guardamos el cultivo a eliminar
                            showDialog = true // Mostramos el diálogo de confirmación
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

    // Diálogo de confirmación de eliminación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "¿Deseas eliminar el cultivo?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        cultivoToDelete?.let {
                            cultivoViewModel.eliminarCultivo(it.idCultivo, idUsuario)
                        }
                        showDialog = false // Cerrar el diálogo
                        isDeleteMode = false // Salir del modo de eliminación
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false } // Cerrar el diálogo sin hacer nada
                ) {
                    Text("No")
                }
            }
        )
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
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Columna que contiene el nombre del cultivo, cantidad y fecha de siembra
            Column(
                modifier = Modifier
                    .weight(1f) // Asegura que la columna ocupe el espacio disponible
                    .fillMaxHeight()
            ) {
                Text(
                    text = cultivo.tipoCultivo,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isDeleteMode) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary
                )
                Text(
                    text = "${cultivo.cantidad} Hectáreas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDeleteMode) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary
                )
            }

            // Coloca la fecha de siembra a la derecha y alineada abajo
            Text(
                text = cultivo.fechaSiembra,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDeleteMode) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.align(Alignment.Bottom) // Alinea verticalmente
            )

            // Si está en modo de eliminación, muestra el icono de eliminación
            if (isDeleteMode) {
                Spacer(modifier = Modifier.width(16.dp)) // Esto asegura un espacio entre la fecha y el icono
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.CenterEnd // Alinea el icono de eliminación al final
                ) {
                    IconButton(
                        onClick = { onDelete() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar Cultivo",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}



