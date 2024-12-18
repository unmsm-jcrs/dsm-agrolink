package com.unmsm.agrolink.ui

import android.app.Application
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.factory.ReporteViewModelFactory
import com.unmsm.agrolink.viewmodel.ReporteViewModel

@Composable
fun ReporteScreen(
    idUsuario: Int,
    reporteViewModel: ReporteViewModel = viewModel(factory = ReporteViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    reporteViewModel.fetchTotalActividadesPorTipo(idUsuario)
    reporteViewModel.fetchTotalCultivos(idUsuario)
    reporteViewModel.fetchTotalCosechas(idUsuario)
    reporteViewModel.fetchTotalDesechados(idUsuario)

    // Observa el LiveData con observeAsState, con un valor inicial predeterminado (0)
    val totalCultivos by reporteViewModel.totalCultivos.observeAsState(initial = 0)
    val totalCosechas by reporteViewModel.totalCosechas.observeAsState(initial = 0)
    val totalDesechados by reporteViewModel.totalDesechados.observeAsState(initial = 0)
    val totalActividadesPorTipo by reporteViewModel.totalActividadesPorTipo.observeAsState(initial = emptyList())

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Reporte",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Total Cultivos: ${totalCultivos}")
            Text(text = "Total Cosechas: ${totalCosechas}")
            Text(text = "Total Desechados: ${totalDesechados}")

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Total Actividades")
            if (totalActividadesPorTipo.isNotEmpty()) {
                // Llama a tu Composable de gráfico personalizado
                PieChart(actividades = totalActividadesPorTipo)
            } else {
                Text("Cargando datos...")
            }

            Text(text = "En proceso de elaboracion...")
        }
    }
}

@Composable
fun PieChart(actividades: List<Pair<String, Int>>) {
    val total = actividades.sumOf { it.second }
    var startAngle = 0f
    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Cyan) // Colores para las 5 actividades

    Canvas(modifier = Modifier.size(200.dp)) {
        actividades.forEachIndexed { index, actividad ->
            val sweepAngle = 360 * (actividad.second / total.toFloat())
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
        }
    }
}