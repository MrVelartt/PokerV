package com.velarttdesign.pokerv.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.velarttdesign.pokerv.models.Card as PokerCard
import com.velarttdesign.pokerv.R

/**
 * CartaView muestra una carta de poker y soporta:
 * - Modo boca abajo (faceDown): muestra el dorso de la carta.
 * - Estado seleccionado: resalta con borde.
 * - Callback onClick cuando es clickable.
 */
@Composable
fun CartaView(
    carta: PokerCard,
    faceDown: Boolean = false,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(width = 60.dp, height = 90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) BorderStroke(2.dp, Color.Yellow) else null,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            val imageRes = if (faceDown) {
                // Imagen de dorso de carta (asegúrate de tener este recurso en drawables)
                R.drawable.card_back
            } else {
                carta.getDrawableResId()
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = if (faceDown) "Carta boca abajo" else carta.toString()
            )
        }
    }
}
