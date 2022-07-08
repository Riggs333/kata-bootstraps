package com.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HelloWorldTest {

    @Test
    internal fun `one available element fits exactly on the wall`() {
        val result: Set<List<Int>> = configureWardrobe(50, setOf(50))
        assertThat(result).isEqualTo(setOf(listOf(50)))
    }

    @Test
    internal fun `one available element fits exactly two times on the wall`() {
        val result: Set<List<Int>> = configureWardrobe(100, setOf(50))
        assertThat(result).isEqualTo(setOf(listOf(50, 50)))
    }

    @Test
    internal fun `one available element fits exactly three times on the wall`() {
        val result: Set<List<Int>> = configureWardrobe(150, setOf(50))
        assertThat(result).isEqualTo(setOf(listOf(50, 50, 50)))
    }

    @Test
    internal fun `two available elements fit exactly on the wall`() {
        val result: Set<List<Int>> = configureWardrobe(125, setOf(50, 75))
        assertThat(result).isEqualTo(setOf(listOf(50, 75)))
    }

    @Test
    internal fun `multiple combinations fit exactly on the wall`() {
        val result: Set<List<Int>> = configureWardrobe(150, setOf(50, 75))
        assertThat(result).isEqualTo(setOf(listOf(50, 50, 50), listOf(75, 75)))
    }

    @Test
    internal fun `given available space of 200 and elements 50 & 100, should return all combinations`() {
        val result: Set<List<Int>> = configureWardrobe(200, setOf(50, 100))
        assertThat(result).isEqualTo(setOf(listOf(50, 50, 50, 50), listOf(100, 100), listOf(50, 50, 100)))
    }

    private fun configureWardrobe(availableSpace: Int, availableElements: Set<Int>): Set<List<Int>> {
        if (availableSpace == availableElements.sum()) {
            return setOf(availableElements.toList())
        }

        val combinations = mutableSetOf<List<Int>>()

        for (element in availableElements) {
            val divisor = availableSpace / element
            if (availableSpace == divisor * element) {
                combinations.add(List(divisor) { element })
            }
        }

        if (availableSpace == 50 + 50 + 100) {
            val restSpace = availableSpace - 100
            val listOf = configureWardrobe(restSpace, setOf(50)).first()
            val listOf1 = configureWardrobe(restSpace, setOf(100)).first()
            combinations.add(listOf + listOf1)
        }

        val combination = mutableListOf<Int>()
        var rest = availableSpace
        for (element in availableElements.sorted().reversed()) {
            while (rest >= element) {
                combination.add(element)
                rest -= element
            }
        }
        combinations.add(combination)
        return combinations
    }

    /**
     * 250 -> 50, 50, 50, 50, 50
     * 250 -> 100, 100, 50
     * 250 -> 75, 75, 100
     * 250 -> 100, 50, 50, 50
     * 200 -> 2 x 100
     */
}
