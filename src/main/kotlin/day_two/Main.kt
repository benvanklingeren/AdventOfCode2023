package day_two

import filterDigits
import print
import splitNewLines
import kotlin.math.max

fun main() {
    input
        .parse()
        .filter { it.isPossible(12, 13, 14) }
        .sumOf { it.id }
        .print()

    input
        .parse()
        .sumOf { game ->
            game.sets
                .maxed()
                .values
                .reduce(Int::times)
        }
        .print()
}

fun Game.isPossible(red: Int, green: Int, blue: Int): Boolean = sets.maxed().all { (cube, amount) ->
    when (cube) {
        Cube.Red -> amount <= red
        Cube.Green -> amount <= green
        Cube.Blue -> amount <= blue
    }
}

fun List<Map<Cube, Int>>.maxed(maxRed: Int = 0, maxGreen: Int = 0, maxBlue: Int = 0): Map<Cube, Int> = if (isEmpty()) {
    Cube.entries.associate {
        when (it) {
            Cube.Red -> it to maxRed
            Cube.Green -> it to maxGreen
            Cube.Blue -> it to maxBlue
        }
    }
} else {
    drop(1).maxed(
        maxRed = max(first()[Cube.Red]?:0, maxRed),
        maxGreen = max(first()[Cube.Green]?:0, maxGreen),
        maxBlue = max(first()[Cube.Blue]?:0, maxBlue)
    )
}

fun String.parse(): List<Game> = splitNewLines()
    .map { line ->
        Game(
            id = line.drop(5)
                .substringBefore(':')
                .toInt(),
            sets = line.substringAfter(": ")
                .split(';')
                .map(String::toSet)
        )
    }

fun String.toSet(): Map<Cube, Int> = split(',').associate { cube ->
    Cube.map[cube.findAnyOf(Cube.map.keys)!!.second]!! to cube.filterDigits().toInt()
}

data class Game(
    val id: Int,
    val sets: List<Map<Cube, Int>>
)

enum class Cube {
    Red, Green, Blue;

    companion object {
        val map = entries.associate { cube ->
            when (cube) {
                Red -> "red"
                Green -> "green"
                Blue -> "blue"
            }.let { key ->
                key to cube
            }
        }
    }
}