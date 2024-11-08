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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCultivosActivity(
    idUsuario: Int, // Parámetro que identifica al usuario
    onNavigateToAgregarCultivo: () -> Unit,
    onNavigateToDetalleCultivo: (Int) -> Unit,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application)),
    modifier: Modifier = Modifier
) {
    val cultivos by cultivoViewModel.cultivos.observeAsState(emptyList())

    // Cargar cultivos para el usuario específico cuando se muestra la pantalla
    LaunchedEffect(idUsuario) {
        cultivoViewModel.loadCultivos(idUsuario)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AgroLink") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00695C),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAgregarCultivo,
                containerColor = Color(0xFF80CBC4)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        },
        modifier = modifier
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(cultivos) { cultivo ->
                CultivoItem(cultivo, onClick = { onNavigateToDetalleCultivo(cultivo.idCultivo) })
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFF78909C))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza con la imagen de cultivo que tengas
                contentDescription = "Imagen del cultivo",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = cultivo.tipoCultivo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "Plantado el ${cultivo.fechaSiembra}", color = Color.White)
            }
        }
    }
}