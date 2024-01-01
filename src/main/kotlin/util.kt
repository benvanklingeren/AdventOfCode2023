
fun <T> T.print() = this.also { println(it) }

fun String.splitNewLines() = split("\n")

fun String.filterDigits(): String = filter { it.isDigit() }