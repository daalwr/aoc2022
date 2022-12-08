private fun getGrid(input: List<String>) =
    input.map { it.toCharArray().map { char -> char.digitToInt() } }

private fun part1(input: List<String>): Int {

    val treesVisible = mutableSetOf<Pair<Int, Int>>()
    val grid = getGrid(input)

    val rowCheckRanges = listOf(grid.indices, grid.indices.reversed())
    val colCheckRanges = listOf(grid[0].indices, grid[0].indices.reversed())

    grid.indices.forEach { row ->
        colCheckRanges.forEach { columnRange ->
            var highestTreeSeen = -1
            columnRange.forEach { column ->
                if (grid[row][column] > highestTreeSeen) {
                    treesVisible.add(Pair(column, row))
                    highestTreeSeen = grid[row][column]
                }
            }
        }
    }

    grid.first().indices.forEach { column ->
        rowCheckRanges.forEach { rowRange ->
            var highestTreeSeen = -1
            rowRange.forEach { row ->
                if (grid[row][column] > highestTreeSeen) {
                    treesVisible.add(Pair(column, row))
                    highestTreeSeen = grid[row][column]
                }
            }
        }
    }

    return treesVisible.size
}


private fun part2(input: List<String>): Int {
    val g = getGrid(input)

    return (1 until g.size - 1).flatMap { r ->
        (1 until g[1].size - 1).map { c ->
            listOf(
                (r - 1 downTo 0).map { g[it][c] },
                (r + 1 until g.size).map { g[it][c] },
                (c + 1 until g[1].size).map { g[r][it] },
                (c - 1 downTo 0).map { g[r][it] }
            ).map { ts ->
                val i = ts.indexOfFirst { it >= g[r][c] }
                if (i < 0) {
                    ts.size
                } else {
                    i + 1
                }
            }.fold(1) { x, y -> x * y }
        }
    }.max()
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day08.txt")
    println(part1(input))
    println(part2(input))
}