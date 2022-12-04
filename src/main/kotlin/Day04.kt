private fun getRangesFromInput(line: String): Pair<List<Int>, List<Int>> {
    val pair = line.split(",")
    val a = pair[0].split("-").map { it.toInt() }
    val b = pair[1].split("-").map { it.toInt() }
    return Pair(a, b)
}

fun day04a(input: List<String>): Int =
    input.count { line ->
        val (a, b) = getRangesFromInput(line)
        (a[0] <= b[0] && a[1] >= b[1]) || (b[0] <= a[0] && b[1] >= a[1])
    }

fun day04b(input: List<String>): Int =
    input.count { line ->
        val (a, b) = getRangesFromInput(line)
        (a[0] < b[1] && a[1] >= b[0]) || (a[1] > b[0] && a[0] <= b[1]) || (a[0] == a[1] && a == b)
    }

fun main() {
    val input = loadFile("./src/main/kotlin/Day04.txt")
    println(day04a(input))
    println(day04b(input))
}