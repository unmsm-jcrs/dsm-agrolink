package com.unmsm.agrolink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unmsm.agrolink.ui.MisCultivosActivity
import com.unmsm.agrolink.ui.theme.AgroLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgroLinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MisCultivosActivity(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisCultivosPreview() {
    AgroLinkTheme(darkTheme = false) {
        MisCultivosActivity()
    }
}
