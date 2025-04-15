package com.velarttdesign.pokerv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.velarttdesign.pokerv.R
import com.velarttdesign.pokerv.ui.navigation.Routes

@Composable
fun ProfileScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.profile_background),
            contentDescription = "Fondo de Perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Botón superior para volver
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { navController.navigate(Routes.LOBBY) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E3A59)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Lobby", color = Color.White, style = MaterialTheme.typography.labelMedium)
            }
        }

        // Contenido central
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfil de Usuario",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1F1F).copy(alpha = 0.8f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("👤 Nombre: Velartt", color = Color.White)
                    Text("🏅 Nivel: Avanzado", color = Color.White)
                    Text("💰 Fichas: 5000", color = Color.White)
                }
            }
        }
    }
}
