//MisCultivosActivity.kt

package com.unmsm.agrolink.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.R
import com.unmsm.agrolink.viewmodel.CultivoViewModel

@Composable
fun MisCultivosActivity(
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val cultivos by cultivoViewModel.cultivos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AgroLink") },
                backgroundColor = Color(0xFF00695C),
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navegar a AgregarCultivoActivity */ },
                containerColor = Color(0xFF80CBC4)
            ) {
                Text("+", color = Color.White)
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
                CultivoItem(cultivo)
            }
        }
    }
}

@Composable
fun CultivoItem(cultivo: Cultivo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Navegar a DetalleCultivoActivity con cultivo.id */ },
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
                painter = painterResource(id = R.drawable.sample_crop_image),
                contentDescription = "Imagen del cultivo",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = cultivo.tipo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "Plantado el ${cultivo.fechaSiembra}", color = Color.White)
            }
        }
    }
}
