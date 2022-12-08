private fun getElfCaloriesSortedDescending(input: List<String>): List<Int> {
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

private fun part1(input: List<String>): Int = getElfCaloriesSortedDescending(input).max()
private fun part2(input: List<String>): Int = getElfCaloriesSortedDescending(input).take(3).sum()

fun main() {
    val input = loadFile("./src/main/kotlin/Day01.txt")
    println(part1(input))
    println(part2(input))
}