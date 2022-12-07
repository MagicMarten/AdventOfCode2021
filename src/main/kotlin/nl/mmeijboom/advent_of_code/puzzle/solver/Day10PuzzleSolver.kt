package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.LinkedList

@Component
class Day7PuzzleSolver : PuzzleSolver {

    companion object : Log()

    override fun supports(day: Int): Boolean {
        return day == 10
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY TEN")
        solvePartOne(input)
        solvePartTwo(input)
    }

    val openingCharacters: List<Char> = listOf('(', '[', '{', '<')
    val closingCharacters: List<Char> = listOf(')', ']', '}', '>')

    val closingBrackets: Map<Char, Char> = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    val scores: Map<Char, Int> = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    private fun solvePartOne(input: List<String>) {
        var result = 0

        for(row in input) {
            result += findFirstErrorInLine(row)
        }

        log.info("PART ONE RESULT: $result")
    }

    private fun findFirstErrorInLine(input: String): Int {
        val expectedQueue: LinkedList<Char> = LinkedList()
        for(c in input) {
            if(openingCharacters.contains(c)) {
                expectedQueue.add(c)
            } else if(closingCharacters.contains(c)) {
                val expected = closingBrackets[expectedQueue.removeLast()]!!
                if(expected != c) {
                    return scores[c]!!
                }
            }
        }

        return 0
    }

    private fun lineIsCorrupt(input: String): Boolean {
        val expectedQueue: LinkedList<Char> = LinkedList()
        for(c in input) {
            if(openingCharacters.contains(c)) {
                expectedQueue.add(c)
            } else if(closingCharacters.contains(c)) {
                val expected = closingBrackets[expectedQueue.removeLast()]!!
                if(expected != c) {
                    return true
                }
            }
        }

        return false
    }

    private fun solvePartTwo(input: List<String>) {
        val incompleteLines = input.filter { !lineIsCorrupt(it) }.toList()
        val scores = ArrayList<Int>()

        for(row in incompleteLines) {
            val closingBrackets = getRequiredClosingBrackets(row)
            scores.add(calculateScore(closingBrackets))
        }

        log.info("PART TWO: ${scores.sortedBy { it }[scores.size / 2]}")
    }

    val closingScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    private fun calculateScore(input: List<Char>): Int {
        var score = 0

        for(c in input) {
            score *= 5
            score += closingScores[c]!!
        }

        return score
    }

    private fun getRequiredClosingBrackets(input: String): List<Char> {
        val seenItems = ArrayList<Char>()
        for(c in input) {
            if(openingCharacters.contains(c)) {
                seenItems.add(c)
            } else if(closingCharacters.contains(c)) {
                val expected = closingBrackets[seenItems.removeLast()]
                if(expected != c){
                    throw RuntimeException("WOT")
                }
            }
        }

        println("--------")
        println(input)
        println(seenItems)

        return seenItems.map { closingBrackets[it]!! }.toList()
    }
}