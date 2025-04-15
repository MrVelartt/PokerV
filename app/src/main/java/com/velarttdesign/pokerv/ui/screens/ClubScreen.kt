package com.velarttdesign.pokerv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.velarttdesign.pokerv.R
import com.velarttdesign.pokerv.ui.navigation.Routes

@Composable
fun ClubScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de imagen (puedes reemplazar con tu fondo de clubes si lo tienes)
        Image(
            painter = painterResource(id = R.drawable.club_background), // Asigna tu recurso aquí
            contentDescription = "Fondo Club",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clubes y Torneos",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Routes.LOBBY) },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Volver al Lobby")
            }
        }

        // Botón pequeño en la esquina
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
