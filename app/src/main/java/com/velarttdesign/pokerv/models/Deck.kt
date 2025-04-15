package com.velarttdesign.pokerv.models

class Deck {

    private val values = listOf("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2")
    private val suits = listOf("C", "D", "H", "S")
    private val cards: MutableList<Card> = mutableListOf()

    init {
        for (value in values) {
            for (suit in suits) {
                cards.add(Card(value, suit))
            }
        }
        cards.shuffle()
    }

    fun shuffle() {
        cards.shuffle()
    }

    fun draw(numCards: Int): List<Card> {
        return deal(numCards)
    }

    fun deal(numCards: Int): List<Card> {
        val dealtCards = cards.take(numCards)
        cards.removeAll(dealtCards)
        return dealtCards
    }

    fun remainingCards(): Int {
        return cards.size
    }
}
