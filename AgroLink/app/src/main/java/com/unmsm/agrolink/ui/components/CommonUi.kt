package com.unmsm.agrolink.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
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
    size: ButtonSize,
    icon: ImageVector? = null
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
        4 -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer

        )
        else -> ButtonDefaults.buttonColors(
            containerColor = Color.Gray,  // Por defecto en caso de un valor incorrecto
            contentColor = Color.White
        )
    }
    val buttonModifier = when (size) {
        ButtonSize.Large -> modifier.then(Modifier.padding(16.dp)) // Tamaño grande con padding adicional
        ButtonSize.Medium -> modifier.then(Modifier.padding(2.dp)) // Tamaño medio con padding reducido
    }
    val textStyle = when (size) {
        ButtonSize.Large -> MaterialTheme.typography.displayLarge
        ButtonSize.Medium -> MaterialTheme.typography.displayMedium
    }
    Button(
        onClick = onClick,
        modifier = buttonModifier,
        colors = buttonColors
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
        }

        Text(
            text = buttonText,
            style = textStyle
        )
    }
}
