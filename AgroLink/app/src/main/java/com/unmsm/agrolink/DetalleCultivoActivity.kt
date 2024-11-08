//DetalleCultivoActivity.kt

package com.unmsm.agrolink.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.R
import com.unmsm.agrolink.data.Cultivo
import com.unmsm.agrolink.viewmodel.CultivoViewModel

@Composable
fun DetalleCultivoActivity(
    cultivoId: Int,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModel.Factory)
) {
    val cultivo = cultivoViewModel.getCultivoById(cultivoId).collectAsState(initial = null).value

    cultivo?.let {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AgroLink") },
                    backgroundColor = Color(0xFF00695C),
                    contentColor = Color.White
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_crop_image),
                    contentDescription = "Imagen del cultivo",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.tipo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Plantado el ${it.fechaSiembra}")
                Text(text = "${it.cantidad} Hectáreas")
            }
        }
    }
}
