package day_1

import filterDigits
import print
import splitNewLines

// take first and last digit to form a single two-digit number,
// what is the sum of all calibration values?
fun main() {
    // only actual digits
    input
        .splitNewLines()
        .sumOf { line ->
            line.filterDigits()
                .firstAndLast()
                .toInt()
        }
        .print()

    // including digits as text
    input
        .splitNewLines()
        .sumOf { line ->
            line.numbersAsDigits()
                .filterDigits()
                .firstAndLast()
                .toInt()
        }
        .print()
}

fun String.numbersAsDigits(index: Int = 0, indices: List<Pair<Int, String>> = emptyList()): String =
    findAnyOf(digitMap.keys, index, true)?.let { found ->
        numbersAsDigits(index + 1, indices + found)
    } ?: indices
        .distinct()
        .fold(this) { acc, (index, key) ->
            acc.take(index) + digitMap[key].toString() + acc.drop(index + 1)
        }

val digitMap: Map<String, Int> = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun String.firstAndLast(): String = "${first()}${last()}"