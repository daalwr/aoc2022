import kotlin.math.abs

data class Instruction(val dir: String, val amount: Int)

data class Position(var x: Int, var y: Int)

private fun distance(headX: Int, headY: Int, tailX: Int, tailY: Int): Int =
    maxOf(abs(headX - tailX), abs(headY - tailY))

private fun getInstructions(input: List<String>) = input.map {
    val pair = it.split(" ")
    Instruction(pair[0], pair[1].toInt())
}

private fun part1(input: List<String>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(0, 0))

    val instructions = getInstructions(input)

    var headX = 0
    var headY = 0

    var tailX = 0
    var tailY = 0

    instructions.forEach { instruction ->
        for (step in 0 until instruction.amount) {
            when (instruction.dir) {
                "U" -> headY++
                "R" -> headX++
                "L" -> headX--
                "D" -> headY--
            }

            if (distance(headX, headY, tailX, tailY) > 1) {
                if (headX == tailX) {
                    if (headY > tailY) {
                        tailY++
                    } else {
                        tailY--
                    }
                } else if (headY == tailY) {
                    if (headX > tailX) {
                        tailX++
                    } else {
                        tailX--
                    }
                } else {
                    if (distance(headX, headY, tailX + 1, tailY + 1) == 1) {
                        tailX++
                        tailY++
                    } else if (distance(headX, headY, tailX - 1, tailY + 1) == 1) {
                        tailX--
                        tailY++
                    } else if (distance(headX, headY, tailX - 1, tailY - 1) == 1) {
                        tailX--
                        tailY--
                    } else if (distance(headX, headY, tailX + 1, tailY - 1) == 1) {
                        tailX++
                        tailY--
                    }
                }
            }
            visited.add(Pair(tailX, tailY))
        }
    }

    return visited.size
}


private fun part2(input: List<String>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(0, 0))

    val instructions = getInstructions(input)

    val ropePositions = (0 until 10).map { Position(0, 0) }.toMutableList()

    instructions.forEach { instruction ->
        for (step in 0 until instruction.amount) {
            when (instruction.dir) {
                "U" -> ropePositions[0].y++
                "R" -> ropePositions[0].x++
                "L" -> ropePositions[0].x--
                "D" -> ropePositions[0].y--
            }

            for (i in 0 until 9) {

                val head = ropePositions[i]
                val tail = ropePositions[i + 1]

                if (distance(head.x, head.y, tail.x, tail.y) > 1) {
                    if (head.x == tail.x) {
                        if (head.y > tail.y) {
                            tail.y++
                        } else {
                            tail.y--
                        }
                    } else if (head.y == tail.y) {
                        if (head.x > tail.x) {
                            tail.x++
                        } else {
                            tail.x--
                        }
                    } else {
                        if (distance(head.x, head.y, tail.x + 1, tail.y + 1) == 1) {
                            tail.x++
                            tail.y++
                        } else if (distance(head.x, head.y, tail.x - 1, tail.y + 1) == 1) {
                            tail.x--
                            tail.y++
                        } else if (distance(head.x, head.y, tail.x - 1, tail.y - 1) == 1) {
                            tail.x--
                            tail.y--
                        } else if (distance(head.x, head.y, tail.x + 1, tail.y - 1) == 1) {
                            tail.x++
                            tail.y--
                        }
                    }
                }
            }
            visited.add(Pair(ropePositions.last().x, ropePositions.last().y))
        }
    }

    return visited.size
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day09.txt")
    println(part1(input))
    println(part2(input))
}