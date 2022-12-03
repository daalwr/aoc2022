private fun getPairs(input: List<String>) = input.map { x ->
    val row = x.split(" ")
    Pair(row[0], row[1])
}

fun day02a(input: List<String>): Int {
    val pairs = getPairs(input)

    return pairs.sumOf { pair ->

        val play = when (pair.second) {
            "X" -> 1
            "Y" -> 2
            else -> 3 // "Z"
        }

        val score = when (pair) {
            Pair("A", "X") -> 3
            Pair("A", "Y") -> 6
            Pair("A", "Z") -> 0
            Pair("B", "X") -> 0
            Pair("B", "Y") -> 3
            Pair("B", "Z") -> 6
            Pair("C", "X") -> 6
            Pair("C", "Y") -> 0
            else -> 3 // Pair("C", "Z")
        }

        play + score
    }

}

fun day02b(input: List<String>): Int {
    val pairs = getPairs(input)

    return pairs.sumOf { pair ->

        val score = when (pair.second) {
            "X" -> 0
            "Y" -> 3
            else -> 6 // "Z"
        }

        val play = when (pair) {
            Pair("A", "X") -> 3
            Pair("A", "Y") -> 1
            Pair("A", "Z") -> 2
            Pair("B", "X") -> 1
            Pair("B", "Y") -> 2
            Pair("B", "Z") -> 3
            Pair("C", "X") -> 2
            Pair("C", "Y") -> 3
            else -> 1 // Pair("C", "Z")
        }

        play + score
    }

}

fun main() {
    val input = loadFile("./src/main/kotlin/Day02.txt")
    println(day02a(input))
    println(day02b(input))
}