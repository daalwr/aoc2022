import kotlin.math.abs

data class Inst(val dir: String, val amount: Int)
data class Pos(var x: Int, var y: Int)

private fun solve(input: List<String>, ropeLength: Int): Int {
    val instructions = input.map { line -> line.split(" ").let { Inst(it[0], it[1].toInt()) } }
    val knots = MutableList(ropeLength) { Pos(0, 0) }

    return instructions.flatMap { instruction ->
        (0 until instruction.amount).map {
            when (instruction.dir) {
                "U" -> knots[0].y++
                "R" -> knots[0].x++
                "L" -> knots[0].x--
                "D" -> knots[0].y--
            }

            (0 until ropeLength - 1).forEach { i ->
                if (maxOf(abs(knots[i].x - knots[i + 1].x), abs(knots[i].y - knots[i + 1].y)) > 1) {
                    if (knots[i].x == knots[i + 1].x) {
                        knots[i + 1].y += if (knots[i].y > knots[i + 1].y) 1 else -1
                    } else if (knots[i].y == knots[i + 1].y) {
                        knots[i + 1].x += if (knots[i].x > knots[i + 1].x) 1 else -1
                    } else {
                        for (dir in listOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))) {
                            if (maxOf(
                                    abs(knots[i].x - (knots[i + 1].x + dir.first)), abs(
                                        knots[i].y - (knots[i + 1].y + dir.second
                                                )
                                    )
                                ) == 1
                            ) {
                                knots[i + 1].x += dir.first
                                knots[i + 1].y += dir.second
                                break
                            }
                        }
                    }
                }
            }
            knots.last().let { Pair(it.x, it.y) }
        }
    }.toSet().size
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day09.txt")
    println(solve(input, 2))
    println(solve(input, 10))
}