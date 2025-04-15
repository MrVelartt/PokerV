package com.velarttdesign.pokerv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.velarttdesign.pokerv.R
import com.velarttdesign.pokerv.ui.navigation.Routes

@Composable
fun LobbyScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.lobby_background), // Debes agregar la imagen en res/drawable
            contentDescription = "Fondo Lobby",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(0.4f)) // Empuja el título hacia abajo
            Text(
                text = "Poker V",
                fontSize = 60.sp,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(140.dp))

            // Botón principal para ir al juego
            Button(
                onClick = { navController.navigate(Routes.GAME) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("JUGAR", fontSize = 16.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(114.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    iconRes = R.drawable.ic_profile,
                    label = "Perfil",
                    onClick = { navController.navigate(Routes.PROFILE) }
                )
                IconButton(
                    iconRes = R.drawable.ic_club,
                    label = "Clubes",
                    onClick = { navController.navigate(Routes.CLUB) }
                )
                IconButton(
                    iconRes = R.drawable.ic_settings,
                    label = "Opciones",
                    onClick = { navController.navigate(Routes.SETTINGS) }
                )
            }
            Spacer(modifier = Modifier.height(58.dp)) // Este espacio los empuja más abajo

        }
    }
}

@Composable
fun IconButton(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(70.dp)
        )
        Text(text = label, fontSize = 14.sp, color = Color.White)
    }
}
