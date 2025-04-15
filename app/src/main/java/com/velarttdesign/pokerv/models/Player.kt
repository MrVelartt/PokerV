package com.velarttdesign.pokerv.models

data class Player(
    val name: String,
    var hand: MutableList<Card> = mutableListOf(),
    var score: Int = 0,
    var status: PlayerStatus = PlayerStatus.PENDING,
    var hasTurn: Boolean = false
) {

    /**
     * Intercambia UNA o TODAS las cartas con la viuda.
     * - Si [indexes] tiene tamaño 1, se cambia una carta.
     * - Si [indexes] tiene tamaño 5, se cambian todas.
     * - Cualquier otro número lanza excepción.
     */
    fun exchangeWithWidow(indexes: List<Int>, widowCards: MutableList<Card>): List<Card> {
        require(indexes.size == 1 || indexes.size == 5) {
            "Solo puedes cambiar UNA carta o TODAS las cartas."
        }
        require(widowCards.size >= indexes.size) {
            "La viuda no tiene suficientes cartas para este cambio."
        }

        val returnedToWidow = mutableListOf<Card>()

        indexes.forEachIndexed { i, index ->
            if (index in hand.indices) {
                returnedToWidow.add(hand[index])
                hand[index] = widowCards.removeAt(0)
            }
        }

        return returnedToWidow
    }

    fun resetPlayer() {
        hand.clear()
        score = 0
        status = PlayerStatus.PENDING
        hasTurn = false
    }

    fun handToString(): String {
        return hand.joinToString(" ") { "${it.value}${it.suit}" }
    }
}

enum class PlayerStatus {
    PENDING,
    WON,
    LOST,
    TIED
}
