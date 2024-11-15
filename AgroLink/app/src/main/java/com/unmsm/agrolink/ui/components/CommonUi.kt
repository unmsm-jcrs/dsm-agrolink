package com.unmsm.agrolink.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Función para el botón con tipo de colores dinámicos

enum class ButtonSize {
    Large, Medium
}
@Composable
fun CustomButton(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
    type: Int,
    size: ButtonSize
) {
    val buttonColors = when (type) {
        1 -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        2 -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        3 -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
        else -> ButtonDefaults.buttonColors(
            containerColor = Color.Gray,  // Por defecto en caso de un valor incorrecto
            contentColor = Color.White
        )
    }
    val buttonModifier = when (size) {
        ButtonSize.Large -> modifier.then(Modifier.padding(16.dp)) // Tamaño grande con padding adicional
        ButtonSize.Medium -> modifier.then(Modifier.padding(8.dp)) // Tamaño medio con padding reducido
    }
    val textStyle = when (size) {
        ButtonSize.Large -> MaterialTheme.typography.displayLarge
        ButtonSize.Medium -> MaterialTheme.typography.bodyMedium
    }
    Button(
        onClick = onClick,
        modifier = buttonModifier,
        colors = buttonColors
    ) {
        Text(
            text = buttonText,
            style = textStyle
        )
    }
}



