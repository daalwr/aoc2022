private fun getItemScore(item: Char): Int =
    if (item in 'a'..'z') {
        item.code - 96
    } else {
        item.code - 65 + 27
    }

fun day03a(input: List<String>): Int =
    input.sumOf { line ->
        val first = line.subSequence(0, line.length / 2)
        val second = line.subSequence(line.length / 2, line.length)
        val item = first.first { second.contains(it) }
        getItemScore(item)
    }


fun day03b(input: List<String>): Int =
    (0 until input.size / 3).sumOf { i ->
        val one = input[i * 3]
        val two = input[i * 3 + 1]
        val three = input[i * 3 + 2]
        val item = one.first { two.contains(it) && three.contains(it) }
        getItemScore(item)
    }

fun main() {
    val input = loadFile("./src/main/kotlin/Day03.txt")
    println(day03a(input))
    println(day03b(input))
}