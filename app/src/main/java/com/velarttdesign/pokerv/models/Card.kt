package com.velarttdesign.pokerv.models
import com.velarttdesign.pokerv.R

data class Card(
    val value: String,
    val suit: String
) {
    fun getDrawableResId(): Int {
        val key = "${value.uppercase()}${suit.uppercase()}" // ej: "10H"
        return CardResources.getImageRes(key) ?: R.drawable.card_back
    }

    override fun toString(): String = "$value$suit"
}
