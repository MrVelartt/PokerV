package com.velarttdesign.pokerv.models

import java.lang.IllegalArgumentException
import kotlin.math.min

enum class HandRank(val rankValue: Int) {
    ESCALERA_COLOR(8),
    POKER(7),
    FULL(6),
    COLOR(5),
    ESCALERA(4),
    TRIO(3),
    PAR(2),
    CARTA_ALTA(1)
}

data class HandEvaluation(
    val handRank: HandRank,
    val mainValues: List<Int> // Valores clave para desempate, ordenados de mayor a menor
)

object HandEvaluator {

    fun evaluateHand(hand: List<Card>): HandEvaluation {
        // Convertir los valores de las cartas a enteros y ordenarlos (de menor a mayor)
        val numericValues = hand.map { cardValueToInt(it.value) }.sorted()
        val suits = hand.map { it.suit }

        // Determinar si todas las cartas comparten el mismo palo (Flush)
        val flush = suits.all { it == suits.first() }
        // Determinar si los valores son consecutivos (Straight). Consideramos el caso especial: A, 2, 3, 4, 5.
        val straight = isStraight(numericValues)
        // Conteo de frecuencia de cada valor
        val frequencies = numericValues.groupingBy { it }.eachCount()
        val counts = frequencies.values.sortedDescending()

        // Para desempate, ordenar primero por frecuencia y luego por valor (en forma descendente)
        val sortedByCount = frequencies.entries.sortedWith(
            compareByDescending<Map.Entry<Int, Int>> { it.value }
                .thenByDescending { it.key }
        ).map { it.key }

        return when {
            flush && straight -> HandEvaluation(HandRank.ESCALERA_COLOR, listOf(numericValues.last()))
            counts[0] == 4 -> HandEvaluation(HandRank.POKER, sortedByCount)
            counts[0] == 3 && frequencies.values.contains(2) -> HandEvaluation(HandRank.FULL, sortedByCount)
            flush -> HandEvaluation(HandRank.COLOR, numericValues.reversed())
            straight -> HandEvaluation(HandRank.ESCALERA, listOf(numericValues.last()))
            counts[0] == 3 -> HandEvaluation(HandRank.TRIO, sortedByCount)
            counts[0] == 2 -> HandEvaluation(HandRank.PAR, sortedByCount)
            else -> HandEvaluation(HandRank.CARTA_ALTA, numericValues.reversed())
        }
    }

    private fun isStraight(values: List<Int>): Boolean {
        // Caso especial: A, 2, 3, 4, 5 (A se representa como 14)
        if (values == listOf(2, 3, 4, 5, 14)) return true
        for (i in 0 until values.size - 1) {
            if (values[i+1] - values[i] != 1) return false
        }
        return true
    }

    private fun cardValueToInt(value: String): Int {
        return when (value) {
            "A" -> 14
            "K" -> 13
            "Q" -> 12
            "J" -> 11
            "10", "0" -> 10  // Dependiendo del nombre que uses para el 10
            "9" -> 9
            "8" -> 8
            "7" -> 7
            "6" -> 6
            "5" -> 5
            "4" -> 4
            "3" -> 3
            "2" -> 2
            else -> throw IllegalArgumentException("Valor desconocido: $value")
        }
    }

    // Compara dos manos (listas de 5 cartas); devuelve:
    // > 0 si hand1 es mejor,
    // 0 si empatan,
    // < 0 si hand2 es mejor.
    fun compareHands(hand1: List<Card>, hand2: List<Card>): Int {
        val eval1 = evaluateHand(hand1)
        val eval2 = evaluateHand(hand2)

        if (eval1.handRank.rankValue != eval2.handRank.rankValue) {
            return eval1.handRank.rankValue - eval2.handRank.rankValue
        }
        val size = min(eval1.mainValues.size, eval2.mainValues.size)
        for (i in 0 until size) {
            if (eval1.mainValues[i] != eval2.mainValues[i]) {
                return eval1.mainValues[i] - eval2.mainValues[i]
            }
        }
        return 0 // Empate
    }
}
