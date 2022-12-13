import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

private fun inOrder(left: JsonElement, right: JsonElement): Boolean? {

    if (left is JsonPrimitive && right is JsonArray) {
        return inOrder(JsonArray(listOf(left)), right)
    }

    if (left is JsonArray && right is JsonPrimitive) {
        return inOrder(left, JsonArray(listOf(right)))
    }

    if (left is JsonArray && right is JsonArray) {
        for (i in 0 until minOf(left.size, right.size)) {
            val l = left[i]
            val r = right[i]
            if (l is JsonPrimitive && r is JsonPrimitive) {
                if (l.int < r.int) {
                    return true
                } else if (l.int > r.int) {
                    return false
                }
            } else {
                val inOrd = inOrder(l, r)
                if (inOrd != null) {
                    return inOrd
                }
            }
        }

        if (left.size < right.size) {
            return true
        } else if (left.size > right.size) {
            return false
        }

        return null
    }

    throw Error("Unexpected")
}

class JsonComparator : Comparator<JsonElement> {
    override fun compare(o1: JsonElement, o2: JsonElement): Int =
        if (inOrder(o1, o2) == true) {
            -1
        } else {
            1
        }
}

private fun part1(input: List<String>): Int =
    input
        .asSequence()
        .windowed(3, 3, true)
        .map {
            Pair(
                Json.decodeFromString<JsonElement>(it[0]),
                Json.decodeFromString<JsonElement>(it[1])
            )
        }
        .mapIndexed { i, p -> Pair(i, inOrder(p.first, p.second)) }
        .filter { it.second == true }
        .map { it.first + 1 }
        .sum()

private fun part2(input: List<String>): Int {
    val strings = input.filter { it.isNotEmpty() } + "[[2]]" + "[[6]]"

    val sortedStringList = strings
        .map { Json.decodeFromString<JsonElement>(it) }
        .sortedWith(JsonComparator())
        .map { it.toString() }

    return (sortedStringList.indexOf("[[2]]") + 1) * (sortedStringList.indexOf("[[6]]") + 1)
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day13.txt")
    println(part1(input))
    println(part2(input))
}