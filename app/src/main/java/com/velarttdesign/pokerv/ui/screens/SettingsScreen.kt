package com.velarttdesign.pokerv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.velarttdesign.pokerv.R
import com.velarttdesign.pokerv.ui.navigation.Routes

@Composable
fun SettingsScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo visual coherente
        Image(
            painter = painterResource(id = R.drawable.settings_background), // Pon tu fondo aquí
            contentDescription = "Fondo Ajustes",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Configuraciones",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Opciones de configuración básicas (puedes personalizar luego)
            Text("Sonido: Activado")
            Text("Notificaciones: Activadas")
            Text("Idioma: Español")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Routes.LOBBY) },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Volver al Lobby")
            }
        }

        // Botón pequeño arriba
        Button(
            onClick = { navController.navigate(Routes.LOBBY) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("Lobby", style = MaterialTheme.typography.bodySmall)
        }
    }
}
