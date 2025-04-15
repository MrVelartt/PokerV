package com.velarttdesign.pokerv.models

/**
 * GameManager es el encargado de iniciar y manejar el flujo principal del juego.
 * Genera el mazo, baraja y reparte 5 cartas a cada jugador (dos jugadores y la viuda).
 */
class GameManager {

    val players: MutableList<Player> = mutableListOf()
    private var deck = Deck()

    // Control de turnos
    private var currentPlayerIndex = 0

    // Referencia directa a la viuda
    val viuda: Player
        get() = players.last()

    // Jugador actual (excluyendo la viuda)
    val currentPlayer: Player
        get() = players[currentPlayerIndex]

    init {
        // Crea dos jugadores y una viuda
        players.add(Player(name = "Jugador 1"))
        players.add(Player(name = "Jugador 2"))
        players.add(Player(name = "Viuda"))
    }

    /**
     * Baraja el mazo y reparte 5 cartas a cada jugador.
     * Si no hay suficientes cartas, reinicia el mazo.
     */
    fun repartirCartas() {
        if (deckSize() < players.size * 5) {
            reiniciarJuego()
        } else {
            deck.shuffle()
        }

        players.forEach { player ->
            player.hand = deck.draw(5).toMutableList()
        }

        // Reinicia al primer jugador en cada nueva mano
        currentPlayerIndex = 0
    }

    /**
     * Avanza al siguiente jugador (excepto la viuda).
     */
    fun avanzarTurno() {
        // Avanza solo entre los jugadores, no incluye la viuda
        currentPlayerIndex = (currentPlayerIndex + 1) % (players.size - 1)
    }

    /**
     * Reinicia el juego: crea un nuevo mazo y limpia las manos.
     */
    fun reiniciarJuego() {
        deck = Deck()
        players.forEach { it.hand.clear() }
        currentPlayerIndex = 0
    }

    /**
     * Retorna un resumen del estado del juego.
     */
    fun estadoDelJuego(): String {
        return players.joinToString("\n") { player ->
            "${player.name}: ${player.hand.joinToString(", ") { "${it.value}${it.suit}" }}"
        }
    }

    /**
     * Devuelve el tamaño actual del mazo.
     */
    fun deckSize(): Int {
        return deck.remainingCards()
    }

    /**
     * Compara dos manos de jugador y devuelve:
     * > 0 si j1 gana, < 0 si j2 gana, 0 si empate
     */
    fun compararManos(j1: Player, j2: Player): Int {
        val j1Score = j1.hand.sumOf { getValorCarta(it.value) }
        val j2Score = j2.hand.sumOf { getValorCarta(it.value) }
        return j1Score.compareTo(j2Score)
    }

    private fun getValorCarta(value: String): Int {
        return when (value) {
            "A" -> 14
            "K" -> 13
            "Q" -> 12
            "J" -> 11
            else -> value.toIntOrNull() ?: 0
        }
    }

    /**
     * Devuelve true si es el turno del jugador pasado por parámetro.
     */
    fun esTurnoDe(jugador: Player): Boolean {
        return jugador == currentPlayer
    }

    /**
     * Intercambia las cartas entre el jugador actual y la viuda.
     */
    fun intercambiarConViuda() {
        if (currentPlayer == viuda) return  // La viuda no intercambia consigo misma
        val manoTemp = currentPlayer.hand.toList()
        currentPlayer.hand = viuda.hand.toMutableList()
        viuda.hand = manoTemp.toMutableList()
    }
}
