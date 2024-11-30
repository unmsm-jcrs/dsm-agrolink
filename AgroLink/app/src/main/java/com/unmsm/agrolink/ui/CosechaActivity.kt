import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unmsm.agrolink.R
import com.unmsm.agrolink.viewmodel.CultivoViewModel
import com.unmsm.agrolink.factory.CultivoViewModelFactory
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.ui.components.ButtonSize
import com.unmsm.agrolink.ui.components.CustomButton


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CosechaScreen(
    idUsuario: Int,
    cultivoViewModel: CultivoViewModel = viewModel(factory = CultivoViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    val cosechas by cultivoViewModel.cultivos.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(idUsuario) {
        cultivoViewModel.loadCultivos(idUsuario) // Cargar cultivos (y cosechas)
    }
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Mi Cosecha",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(cosechas) { cultivo ->
                    CultivoItem(
                        cultivo = cultivo,
                        onClick1 = {
                            cultivoViewModel.cosecharCultivo(cultivo.idCultivo, idUsuario)
                            dialogMessage = "Cosecha realizada correctamente"
                            showDialog = true
                                   },
                        onClick2 = {cultivoViewModel.desecharCultivo(cultivo.idCultivo, idUsuario)
                            dialogMessage = "Cultivo desechado correctamente"
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false } // Cerrar el diálogo
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}


@Composable
fun CultivoItem(
    cultivo: Cultivo,
    onClick1: () -> Unit,
    onClick2: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
             MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier
                .animateContentSize() // Animación suave al expandir/contraer
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_my_crops),
                    contentDescription = "Imagen del cultivo",
                    modifier = Modifier
                        .size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f) // Asegura que la columna ocupe el espacio disponible
                        .fillMaxHeight()
                ) {
                    Text(
                        text = cultivo.tipoCultivo,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Text(
                        text = "${cultivo.cantidad} Hectáreas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ){
                    IconButton(onClick = { expanded = !expanded }) { // Botón para expandir/contraer
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = "Expandir/Contraer",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text(
                        text = cultivo.fechaSiembra,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }

            }



            if (expanded) { // Mostrar botones si está expandido
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        onClick = onClick1,
                        buttonText = "Cosechar",
                        modifier = Modifier.weight(1f),
                        type = 5,
                        size = ButtonSize.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CustomButton(
                        onClick = onClick2,
                        buttonText = "Descartar",
                        modifier = Modifier.weight(1f),
                        type = 4,
                        size = ButtonSize.Medium
                    )
                }
            }
        }
    }
}