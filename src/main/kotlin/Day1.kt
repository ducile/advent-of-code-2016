import utils.Solver
import kotlin.math.absoluteValue

val Pair<Int, Int>.manhattanDistance: Int get() = first.absoluteValue + second.absoluteValue
val Direction.turnLeft: Direction
    get() = Direction.values().first { it -> it.ordinal == Math.floorMod(this.ordinal + 1, 4) }
val Direction.turnRight: Direction
    get() = Direction.values().first { it -> it.ordinal == Math.floorMod(this.ordinal - 1, 4) }

enum class Direction {
    NORTH, WEST, SOUTH, EAST
}

class Day1 : Solver(1) {
    data class State(val pos: Pair<Int, Int>, val dir: Direction) {
        val manhattanDistance get() = pos.manhattanDistance
        val x get() = pos.first
        val y get() = pos.second

        constructor(x: Int, y: Int, dir: Direction) : this(x to y, dir)

        fun copy(x: Int = pos.first, y: Int = pos.second, dir: Direction = this.dir) = copy(pos = x to y, dir = dir)
    }


    private val historicLocations = mutableSetOf<Pair<Int, Int>>()

    override fun solvePart1(input: String): Any {
        val steps = input.split(",").map { it.trim() }.map { parseStep(it) }

        return steps.fold(State(0, 0, Direction.NORTH)) { state, step ->
            walkTo(state, step)
        }.manhattanDistance
    }

    override fun solvePart2(input: String): Any = solutionPart2 ?: 0

    private var solutionPart2: Int? = null

    private fun walkTo(state: Day1.State, step: Pair<Char, Int>): Day1.State =
        when (if (step.first == 'L') state.dir.turnLeft else state.dir.turnRight) {
            Direction.NORTH -> moveNorth(state, step.second)
            Direction.EAST -> moveEast(state, step.second)
            Direction.SOUTH -> moveSouth(state, step.second)
            Direction.WEST -> moveWest(state, step.second)
        }

    private fun walkTo(pos: Pair<Int, Int>) {
        if (!historicLocations.add(pos)) {
            solutionPart2 = if (solutionPart2 == null) pos.manhattanDistance else solutionPart2
        }
    }

    private fun moveNorth(state: State, distance: Int): State {
        for (i in state.y until state.y + distance) {
            walkTo(state.x to i)
        }

        return state.copy(y = state.y + distance, dir = Direction.NORTH)
    }

    private fun moveEast(state: State, distance: Int): State {
        for (i in state.x until state.x + distance) {
            walkTo(i to state.y)
        }

        return state.copy(x = state.x + distance, dir = Direction.EAST)
    }

    private fun moveSouth(state: State, distance: Int): State {
        for (i in state.y downTo state.y - distance + 1) {
            walkTo(state.x to i)
        }

        return state.copy(y = state.y - distance, dir = Direction.SOUTH)
    }

    private fun moveWest(state: State, distance: Int): State {
        for (i in state.x downTo state.x - distance + 1) {
            walkTo(i to state.y)
        }

        return state.copy(x = state.x - distance, dir = Direction.WEST)
    }

    private fun parseStep(input: String): Pair<Char, Int> {
        return input.first() to input.drop(1).toInt()
    }
}

fun main() {
    Day1().solve()
}