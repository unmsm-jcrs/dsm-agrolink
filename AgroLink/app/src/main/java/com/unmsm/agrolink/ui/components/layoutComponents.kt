package com.unmsm.agrolink.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unmsm.agrolink.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Preview
@Composable
fun BottonBar(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination by navController.currentBackStackEntryAsState()

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.surfaceTint),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            IconButtonWithText(
                imageRes = R.drawable.icon_home,
                contentDescription = "Inicio",
                label = "Inicio",
                onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                background = currentDestination?.destination?.route == "home"
            )

            IconButtonWithText(
                imageRes = R.drawable.icon_my_crops,
                contentDescription = "Mis cultivos",
                label = "Cultivos",
                onClick = {
                    navController.navigate("misCultivos") {
                        popUpTo("misCultivos") { inclusive = true }
                    }
                },
                background = currentDestination?.destination?.route == "misCultivos"
            )

            IconButtonWithText(
                imageRes = R.drawable.icon_harvest,
                contentDescription = "Mi cosecha",
                label = "Cosecha",
                onClick = {
                    navController.navigate("cosecha") {
                        popUpTo("cosecha") { inclusive = true }
                    }
                },
                background = currentDestination?.destination?.route == "cosecha"
            )

            IconButtonWithText(
                imageRes = R.drawable.icon_sky,
                contentDescription = "Ver clima",
                label = "Clima",
                onClick = {
                    navController.navigate("clima") {
                        popUpTo("clima") { inclusive = true }
                    }
                },
                background = currentDestination?.destination?.route == "clima"
            )

            IconButtonWithText(
                imageRes = R.drawable.icon_user,
                contentDescription = "Usuario",
                label = "Salir",
                onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                background = currentDestination?.destination?.route == "login"
            )
        }
    }
}


@Composable
fun IconButtonWithText(
    imageRes: Int,
    contentDescription: String,
    label: String,
    onClick: () -> Unit,
    background: Boolean = false
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(60.dp) // Ancho personalizado
                .height(40.dp) // Mantiene la altura original
                .background(
                    color = if (background) MaterialTheme.colorScheme.outline else Color.Transparent,
                    shape = CircleShape
                ) // Fondo circular con color dinámico
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = imageRes),
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


