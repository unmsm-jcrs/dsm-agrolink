// LoginScreen.kt

package com.unmsm.agrolink.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unmsm.agrolink.viewmodel.AuthViewModel
import com.unmsm.agrolink.viewmodel.AuthViewModelFactory
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.unmsm.agrolink.R
import com.unmsm.agrolink.ui.components.ButtonSize
import com.unmsm.agrolink.ui.components.CustomButton

@Composable
fun LoginScreen(
    onLoginSuccess: (Int) -> Unit,
    onNavigateToRegister: () -> Unit,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }
    val imageHeight = 300.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_header_landscape1),
            contentDescription = "Imagen del login",
            modifier = Modifier
                .fillMaxWidth()  // Ocupa todo el ancho de la pantalla
                .height(imageHeight)  // Recorta la imagen según la altura que indiques
                .align(Alignment.TopCenter),  // Alineado al top
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .padding(top = imageHeight) // Añade padding superior para que la columna empiece debajo de la imagen
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Inicio de sesión",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = buildAnnotatedString {
                    append("Inicia sesión usando tu cuenta ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("AgroLink")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )


            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Default.Visibility // Icono de ojo abierto
                    } else {
                        Icons.Default.VisibilityOff // Icono de ojo cerrado
                    }

                    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(
                onClick = {
                    val userId = authViewModel.login(email, contrasena)
                    if (userId != null) {
                        onLoginSuccess(userId)
                    } else {
                        errorMessage = "Correo o contraseña incorrectos"
                    }
                },
                buttonText = "Iniciar sesión",
                modifier = Modifier.fillMaxWidth(),
                type = 2,
                size = ButtonSize.Large
            )

            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier
                    .clickable { onNavigateToRegister() }
                    .padding(0.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("¿No tienes una cuenta? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Regístrate")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            errorMessage?.let {
                Text(it,
                    color = MaterialTheme.colorScheme.onError,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

