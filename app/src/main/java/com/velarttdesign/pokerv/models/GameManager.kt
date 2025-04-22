package com.velarttdesign.pokerv.models

/**
 * GameManager es el encargado de iniciar y manejar el flujo principal del juego.
 * Genera el mazo, baraja y reparte 5 cartas a cada jugador (dos jugadores y la viuda).
 */
class GameManager {

    // Lista de jugadores: Jugador 1, Jugador 2 y la Viuda
    val players: MutableList<Player> = mutableListOf()

    // Mazo de cartas
    private var deck = Deck()

    // Estado de la viuda (destapada o no)
    var widowOpen: Boolean = false
        private set

    // Índice del jugador actual (solo entre los jugadores, excluye la viuda)
    private var currentPlayerIndex = 0

    // Referencia directa a la Viuda
    val viuda: Player
        get() = players.last()

    // Jugador actual (excluyendo la viuda)
    val currentPlayer: Player
        get() = players[currentPlayerIndex]

    init {
        // Inicializa dos jugadores y una viuda
        players.add(Player(name = "Jugador 1"))
        players.add(Player(name = "Jugador 2"))
        players.add(Player(name = "Viuda"))
    }

    fun abrirViuda() {
        widowOpen = true
    }

    fun intercambiarSimple() {
        currentPlayer.intercambiarSimple(viuda.hand)
    }

    fun intercambiarTotal() {
        currentPlayer.intercambiarTotal(viuda.hand)
        widowOpen = true
    }

    fun marcarUltimaMano() {
        currentPlayer.lastRound = true
    }

    fun pasarTurno() {
        pasar()
    }

    fun isEndOfGame(): Boolean {
        return players.all { it.hasPassed }
    }

    fun toggleCardSelection(player: Player, index: Int) {
        if (player.selectedIndices.contains(index)) {
            player.selectedIndices.remove(index)
        } else {
            player.selectedIndices.add(index)
        }
    }

    /**
     * Inicia una nueva partida: reinicia mazo, limpia manos y flags, repone 5 cartas a cada jugador.
     */
    fun resetGame() {
        // Mazo completo de 52 cartas y barajar
        deck = Deck()
        deck.shuffle()

        // Reset de estado de viuda y jugadores
        widowOpen = false
        players.forEach { it.resetPlayer() }

        // Reparto inicial de 5 cartas a cada jugador y viuda (15 cartas)
        players.forEach { player ->
            player.hand = deck.draw(5).toMutableList()
        }

        // Primer jugador arranca con el turno
        currentPlayerIndex = 0
        players[currentPlayerIndex].hasTurn = true
    }

    /**
     * Pasa el turno del jugador actual (sin cambio de cartas).
     */
    fun pasar() {
        currentPlayer.hasPassed = true
        avanzarTurno()
    }

    /**
     * Cambio simple de UNA carta: sólo si la viuda está destapada.
     * @param playerCardIndex índice de la carta en mano del jugador
     * @param widowCardIndex índice de la carta en la viuda
     */
    fun simpleExchange(playerCardIndex: Int, widowCardIndex: Int) {
        require(widowOpen) { "La Viuda debe estar destapada para cambio simple." }
        require(!currentPlayer.simpleExchangeUsed) { "Ya usaste el cambio simple." }

        val returned = currentPlayer.exchangeWithWidow(
            listOf(playerCardIndex), viuda.hand
        )
        // Retorna a viuda la carta indicada (mantiene siempre 5 cartas en viuda)
        viuda.hand.addAll(returned)

        currentPlayer.simpleExchangeUsed = true
        avanzarTurno()
    }

    /**
     * Cambio completo de TODAS las cartas: destapa la viuda si aún estaba cerrada.
     */
    fun fullExchange() {
        require(!currentPlayer.fullExchangeUsed) { "Ya usaste el cambio completo." }

        // Intercambia todas las cartas de mano y de viuda
        val returned = currentPlayer.exchangeWithWidow(
            currentPlayer.hand.indices.toList(), viuda.hand
        )
        viuda.hand.addAll(returned)

        currentPlayer.fullExchangeUsed = true
        widowOpen = true
        avanzarTurno()
    }

    /**
     * Marca la "Última mano" para el jugador actual: ya no podrá hacer más cambios.
     */
    fun indicateLastRound() {
        require(!currentPlayer.lastRound) { "Ya marcaste Última mano." }
        currentPlayer.lastRound = true
        avanzarTurno()
    }

    /**
     * Avanza al siguiente jugador que no haya indicado "Última mano".
     */
    fun avanzarTurno() {
        // Quitar flag de turno del actual
        players[currentPlayerIndex].hasTurn = false

        // Buscar siguiente jugador disponible
        var nextIndex = (currentPlayerIndex + 1) % (players.size - 1)
        while (players[nextIndex].lastRound) {
            nextIndex = (nextIndex + 1) % (players.size - 1)
        }

        currentPlayerIndex = nextIndex
        players[currentPlayerIndex].hasTurn = true
    }

    /**
     * Comprueba si es el turno de un jugador dado.
     */
    fun esTurnoDe(jugador: Player): Boolean {
        return jugador.hasTurn
    }

    /**
     * Retorna el número de cartas restantes en el mazo.
     */
    fun deckSize(): Int {
        return deck.remainingCards()
    }

    /**
     * Para compatibilidad, también mantiene el método antiguo de repartir si ya se usa.
     */
    fun repartirCartas() {
        resetGame()
    }

    /**
     * Devuelve un resumen del estado del juego.
     */
    fun estadoDelJuego(): String {
        return players.joinToString("\n") { player ->
            "${player.name}: ${player.hand.joinToString(", ") { "${it.value}${it.suit}" }}"
        }
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
}
