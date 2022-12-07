package nl.mmeijboom.advent_of_code.puzzle.solver

interface PuzzleSolver {

    fun supports(day: Int): Boolean
    fun solve(input: List<String>)

}