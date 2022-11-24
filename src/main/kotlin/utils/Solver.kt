package utils

abstract class Solver(val day: Int) {
    fun solve() {
        val input = readInputs(day)

        println("Part 1: ${solvePart1(input)}")
        println("Part 2: ${solvePart2(input)}")
    }

    fun verifyAndSolve(testData: String, expectedOutcome: Int, part: String) {
        if (part == "#1") {
            val result = solvePart1(testData)

            if (result.toString() != expectedOutcome.toString()) {
                error("Part 1 should result in $expectedOutcome but was $result")
            }
        } else {
            val result = solvePart2(testData)

            if (result.toString() != expectedOutcome.toString()) {
                error("Part 2 should result in $expectedOutcome but was $result")
            }
        }

        solve()
    }

    abstract fun solvePart1(input: String): Any
    abstract fun solvePart2(input: String): Any
}