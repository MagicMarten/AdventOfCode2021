package nl.mmeijboom.advent_of_code.puzzle

import lombok.extern.slf4j.Slf4j
import nl.mmeijboom.advent_of_code.puzzle.input.InputService
import nl.mmeijboom.advent_of_code.puzzle.solver.PuzzleSolver
import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.Instant

@Slf4j
@Component
class PuzzleManager(
        private val inputService: InputService,
        private val solvers: List<PuzzleSolver>
) : CommandLineRunner {

    private val startDate: Instant = Instant.parse("2022-12-01T00:00:00.00Z")

    companion object : Log() {}

    override fun run(vararg args: String?) {
        val day = if (args.isNotEmpty()) {
            selectDayFromInput(args[0]!!)
        } else {
            selectCurrentDay()
        }

        log.info("Running for day #$day")

        val input = inputService.retrievePuzzleInput(day)

        val solver = getSolverForDay(day)

        solver.solve(input)
    }

    fun getSolverForDay(day: Int): PuzzleSolver {
        for (solver in solvers) {
            if(solver.supports(day)) {
                return solver
            }
        }

        throw NotImplementedError("No solver implemented for selected day")
    }

    private fun selectDayFromInput(inputDay: String): Int {
        return Integer.parseInt(inputDay)
    }

    fun selectCurrentDay(): Int {
        val now: Instant = Instant.now()
        return now.compareTo(startDate)
    }
}