
fun <T> T.print() = this.also { println(it) }

fun String.splitNewLines() = split("\n")

fun String.filterDigits(): String = filter { it.isDigit() }

fun Char.isSymbol(): Boolean = "[/!@#$%&*()_+=|<>?{}\\[\\]~-]".toRegex().matches(this.toString())

fun String.takeWhile(index: Int, next: Int.() -> Int, predicate: (Char) -> Boolean, output: String = ""): String =
    getOrNull(index)?.let { character ->
        if (predicate(character)) {
            takeWhile(index.next(), next, predicate, output + character)
        } else output
    } ?: output

fun String.findWholeValue(index: Int, predicate: (Char) -> Boolean): Int =
    (takeWhile(index, Int::dec, predicate).reversed() + takeWhile(index, Int::inc, predicate).drop(1)).toInt()
