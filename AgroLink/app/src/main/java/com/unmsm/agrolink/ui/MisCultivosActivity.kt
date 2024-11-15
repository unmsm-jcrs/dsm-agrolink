// MisCultivosActivity.kt

package com.unmsm.agrolink.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.unmsm.agrolink.R
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModelFactory

@Composable
fun MisCultivosActivity(
    idUsuario: Int, // Parámetro que identifica al usuario
    onNavigateToAgregarCultivo: () -> Unit,
    onNavigateToDetalleCultivo: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    val cultivos by cultivoViewModel.cultivos.observeAsState(emptyList())

    LaunchedEffect(idUsuario) {
        cultivoViewModel.loadCultivos(idUsuario)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAgregarCultivo,
                containerColor = Color(0xFF80CBC4)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        },
        modifier = modifier
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Padding general de la pantalla
        ) {
            // Título "Mis cultivos" (cerca de la parte superior)
            Text(
                text = "Mis cultivos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
            )

            // Listado de cultivos
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(cultivos) { cultivo ->
                    CultivoItem(cultivo, onClick = { onNavigateToDetalleCultivo(cultivo.idCultivo) })
                }
            }
        }
    }
}

@Composable
fun CultivoItem(cultivo: Cultivo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_my_crops), // Reemplaza con la imagen de cultivo que tengas
                contentDescription = "Imagen del cultivo",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = cultivo.tipoCultivo,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiary
                )
                Text(
                    text = "Plantado el ${cultivo.fechaSiembra}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}