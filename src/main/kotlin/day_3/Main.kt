fun main() {
    input.parse(Char::isSymbol).findAndSumParts(List<Int>::sum).print()

    input.parse('*'::equals).findAndSumParts {
        filterNot(0::equals).run {
            if (size == 2) reduce(Int::times) else 0
        }
    }.print()
}

// 0 = .
// null = part
// > 0 = value
fun String.parse(filter: Char.() -> Boolean): List<List<Int?>> = splitNewLines().map { line ->
    line.foldIndexed(emptyList()) { index, acc, char ->
        acc + if (char.filter()) null else if (char.isDigit()) {
            line.findWholeValue(index, Char::isDigit)
        } else 0
    }
}

fun List<List<Int?>>.findAndSumParts(processParts: List<Int>.() -> Int) =
    foldIndexed(0) { row, rowAcc, line ->
        rowAcc + line.foldIndexed(0) { column, acc, value ->
            acc + if (value == null) {
                getPartNumbers(row, column).distinct().processParts()
            } else 0
        }
    }

fun List<List<Int?>>.getPartNumbers(row: Int, column: Int): List<Int> = listOfNotNull(
    getOrNull(row - 1)?.getOrNull(column - 1),
    getOrNull(row - 1)?.getOrNull(column),
    getOrNull(row - 1)?.getOrNull(column + 1),

    getOrNull(row)?.getOrNull(column - 1),
    getOrNull(row)?.getOrNull(column),
    getOrNull(row)?.getOrNull(column + 1),

    getOrNull(row + 1)?.getOrNull(column - 1),
    getOrNull(row + 1)?.getOrNull(column),
    getOrNull(row + 1)?.getOrNull(column + 1)
)