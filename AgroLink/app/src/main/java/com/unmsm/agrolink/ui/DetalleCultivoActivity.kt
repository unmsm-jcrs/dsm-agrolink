// DetalleCultivoActivity.kt

package com.unmsm.agrolink.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import com.unmsm.agrolink.models.Actividad
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.viewmodel.CultivoViewModelFactory
import com.unmsm.agrolink.viewmodel.ActividadViewModel
import com.unmsm.agrolink.viewmodel.ActividadViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCultivoActivity(
    userId: Int,
    cultivoId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToAgregarActividad: (Int) -> Unit,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application)),
    actividadViewModel: ActividadViewModel = viewModel(factory = ActividadViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    val cultivoLiveData = cultivoViewModel.getCultivoById(cultivoId)
    val cultivo by cultivoLiveData.observeAsState()

    // Cargar actividades solo si el cultivo existe
    LaunchedEffect(cultivoId) {
        if (cultivo != null) {
            actividadViewModel.loadActividades(cultivoId)
        }
    }

    val actividades by actividadViewModel.actividades.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AgroLink", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00695C),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if (cultivo != null) { // Solo muestra el botón si el cultivo existe
                FloatingActionButton(onClick = { onNavigateToAgregarActividad(cultivoId) }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar actividad")
                }
            }
        }
    ) { padding ->
        cultivo?.let { cultivoDetails ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Mi cultivo", style = MaterialTheme.typography.titleLarge, color = Color(0xFF00695C))
                Spacer(modifier = Modifier.height(8.dp))
                CultivoCard(cultivoDetails)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Actividad", style = MaterialTheme.typography.titleLarge, color = Color(0xFF00695C))
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(actividades) { actividad ->
                        ActivityItem(actividad)
                    }
                }
            }
        } ?: run {
            // En caso de que el cultivo sea nulo, mostramos un mensaje de error
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No se encontró el cultivo. Regresa e intenta nuevamente.", color = Color.Red)
                Button(onClick = onNavigateBack) {
                    Text("Regresar")
                }
            }
        }
    }
}

@Composable
fun CultivoCard(cultivo: Cultivo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF78909C))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Imagen del cultivo",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = cultivo.tipoCultivo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "${cultivo.cantidad} Hectáreas", color = Color.White)
                Text(text = cultivo.fechaSiembra, color = Color.White)
            }
        }
    }
}

@Composable
fun ActivityItem(actividad: Actividad) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = actividad.tipoActividad,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = actividad.tipoActividad, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = actividad.fecha, color = Color.Gray)
            }
        }
    }
}