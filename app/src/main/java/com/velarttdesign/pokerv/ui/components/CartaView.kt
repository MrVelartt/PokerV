package com.velarttdesign.pokerv.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

@Composable
fun CartaView(
    carta: PokerCard,
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
            val imageResId = carta.getDrawableResId()
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = carta.toString()
            )
        }
    }
}
