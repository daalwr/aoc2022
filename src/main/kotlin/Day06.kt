private fun getMarkerLocation(message: String, markerLength: Int): Int {
    for (i in 0 until message.length - markerLength - 1) {
        val substring = message.substring(i, i + markerLength)
        if (substring.toCharArray().toSet().size == markerLength) {
            return i + markerLength
        }
    }
    return -1
}

private fun part1(input: List<String>): Int = getMarkerLocation(input.first(), 4)
private fun part2(input: List<String>): Int = getMarkerLocation(input.first(), 14)

fun main() {
    val input = loadFile("./src/main/kotlin/Day06.txt")
    println(part1(input))
    println(part2(input))
}