package utils

import java.io.File

fun readInputs(day: Int): String {
    return File("data/day${day}_input").readText()
}

fun readInputLines(input: String) : List<String> {
    return input.lines()
}