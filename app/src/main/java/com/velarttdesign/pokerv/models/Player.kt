package com.velarttdesign.pokerv.models

data class Player(
    val name: String,
    var hand: MutableList<Card> = mutableListOf(),
    var score: Int = 0,
    var status: PlayerStatus = PlayerStatus.PENDING,
    var hasTurn: Boolean = false,
    // Flags de estado para el turno y cambios
    var hasPassed: Boolean = false,
    var simpleExchangeUsed: Boolean = false,
    var fullExchangeUsed: Boolean = false,
    var lastRound: Boolean = false,
    // Cartas seleccionadas (para cambio simple)
    var selectedIndices: MutableSet<Int> = mutableSetOf()
) {

    /**
     * Intercambia UNA o TODAS las cartas con la viuda.
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

    /**
     * Intercambio simple usando las cartas seleccionadas.
     */
    fun intercambiarSimple(widowCards: MutableList<Card>): List<Card> {
        require(selectedIndices.size == 1) { "Debe haber exactamente una carta seleccionada para el cambio simple." }
        return exchangeWithWidow(selectedIndices.toList(), widowCards)
    }

    /**
     * Intercambio completo (todas las cartas).
     */
    fun intercambiarTotal(widowCards: MutableList<Card>): List<Card> {
        return exchangeWithWidow(hand.indices.toList(), widowCards)
    }

    /**
     * Alterna la selección de una carta.
     */
    fun toggleCardSelection(index: Int) {
        if (selectedIndices.contains(index)) {
            selectedIndices.remove(index)
        } else {
            selectedIndices.add(index)
        }
    }

    /**
     * Resetea el estado del jugador para una nueva partida.
     */
    fun resetPlayer() {
        hand.clear()
        score = 0
        status = PlayerStatus.PENDING
        hasTurn = false
        hasPassed = false
        simpleExchangeUsed = false
        fullExchangeUsed = false
        lastRound = false
        selectedIndices.clear()
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
