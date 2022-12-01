fun getElfCaloriesSortedDescending(input: List<String>): List<Int> {
    var currentElf = 0
    val allElves = mutableListOf<Int>()

    for (line in input) {
        if (line.isEmpty()) {
            allElves.add(currentElf)
            currentElf = 0
        } else {
            currentElf += line.toInt()
        }
    }

    return allElves.sortedDescending()
}

fun day01a(input: List<String>): Int = getElfCaloriesSortedDescending(input).max()
fun day01b(input: List<String>): Int = getElfCaloriesSortedDescending(input).take(3).sum()

fun main() {
    val input = loadFile("./src/main/kotlin/Day01.txt")
    println(day01a(input))
    println(day01b(input))
}