package com.unmsm.agrolink.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.unmsm.agrolink.R

val AcmeFont = FontFamily(
    Font(R.font.acme_regular)
)
val AkatabFont = FontFamily(
    Font(R.font.akatab_regular),
    Font(R.font.akatab_extrabold, FontWeight.Bold)
)
val AmaranthFont = FontFamily(
    Font(R.font.amaranth_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = AmaranthFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ), //Titulo general
    titleMedium = TextStyle(
        fontFamily = AmaranthFont,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ), //Subtitulos
    bodyLarge = TextStyle(
        fontFamily = AkatabFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ), //Subencabezados
    labelLarge = TextStyle(
        fontFamily = AkatabFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ), //Etiquetas de cultivos(el tipo)
    labelMedium = TextStyle(
        fontFamily = AkatabFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ), //Etiquetas de actividades
    bodyMedium = TextStyle(
        fontFamily = AkatabFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ), //Texto plano
    displayMedium = TextStyle(
        fontFamily = AcmeFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ), //Botones
    displayLarge = TextStyle(
        fontFamily = AcmeFont,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp
    ), //Botones Grandes
)