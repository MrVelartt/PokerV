package com.velarttdesign.pokerv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.velarttdesign.pokerv.models.GameManager
import com.velarttdesign.pokerv.models.Player
import com.velarttdesign.pokerv.ui.components.CartaView
import com.velarttdesign.pokerv.ui.navigation.Routes

@Composable
fun GameScreen(navController: NavController) {
    val gameManager = remember { GameManager() }
    var resultado by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        gameManager.resetGame()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.game),
            contentDescription = "Mesa de Poker",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallButton(text = "Lobby") { navController.navigate(Routes.LOBBY) }
            SmallButton(text = "Perfil") { navController.navigate(Routes.PROFILE) }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "La Viuda",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Turno de: ${gameManager.currentPlayer.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Yellow,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Mostrar mano del jugador actual
            JugadorManoView(
                jugador = gameManager.currentPlayer,
                isCurrent = true,
                gameManager = gameManager
            )

            // Mostrar la Viuda (boca abajo si no ha sido abierta)
            JugadorManoView(
                jugador = gameManager.viuda,
                isViuda = true,
                widowOpen = gameManager.widowOpen,
                gameManager = gameManager
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { gameManager.abrirViuda() },
                    enabled = !gameManager.widowOpen && !gameManager.currentPlayer.hasPassed,
                ) {
                    Text("Abrir Viuda")
                }

                Button(
                    onClick = { gameManager.intercambiarSimple() },
                    enabled = gameManager.widowOpen && !gameManager.currentPlayer.simpleExchangeUsed,
                ) {
                    Text("Cambio Simple")
                }

                Button(
                    onClick = { gameManager.intercambiarTotal() },
                    enabled = gameManager.widowOpen && !gameManager.currentPlayer.fullExchangeUsed,
                ) {
                    Text("Cambio Total")
                }
            }

            Button(
                onClick = { gameManager.marcarUltimaMano() },
                enabled = gameManager.widowOpen && !gameManager.currentPlayer.lastRound,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Última Mano")
            }

            Button(
                onClick = {
                    gameManager.pasarTurno()
                    if (gameManager.isEndOfGame()) {
                        val comp = gameManager.compararManos(
                            gameManager.players[0], gameManager.players[1]
                        )
                        resultado = when {
                            comp > 0 -> "${gameManager.players[0].name} gana!"
                            comp < 0 -> "${gameManager.players[1].name} gana!"
                            else -> "Empate!"
                        }
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Siguiente Turno")
            }

            resultado?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
@Composable
fun JugadorManoView(
    jugador: Player,
    isCurrent: Boolean = false,
    isViuda: Boolean = false,
    widowOpen: Boolean = false,
    gameManager: GameManager
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = jugador.name,
            style = MaterialTheme.typography.headlineSmall,
            color = if (isViuda) Color.Gray else Color.White
        )

        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(jugador.hand.indices.toList()) { index ->
                val card = jugador.hand[index]
                val isSelected = jugador.selectedIndices.contains(index)
                val showFace = !isViuda || widowOpen

                CartaView(
                    carta = card,
                    faceDown = !showFace, // <-- pasa la propiedad acá
                    isSelected = isSelected,
                    onClick = {
                        if (isCurrent || (isViuda && widowOpen)) {
                            gameManager.toggleCardSelection(jugador, index)
                        }
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}


@Composable
fun SmallButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp, 32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
    ) {
        Text(text, color = Color.White, style = MaterialTheme.typography.bodySmall)
    }
}
