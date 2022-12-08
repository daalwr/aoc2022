private fun part1(input: List<String>): Int {

    val treesVisible = mutableSetOf<Pair<Int, Int>>()
    val grid = input.map { it.toCharArray().map { char -> char.digitToInt() } }

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
    val grid = input.map { it.toCharArray().map { char -> char.digitToInt() } }

    var maxScore = 0
    for (gridRow in 1 until grid.size - 1) {
        for (gridCol in 1 until grid[1].size - 1) {

            var viewingDistanceUp = -1
            for (row in gridRow - 1 downTo 0) {
                if (grid[row][gridCol] >= grid[gridRow][gridCol]) {
                    viewingDistanceUp = gridRow - row
                    break
                }
            }
            if (viewingDistanceUp < 0) {
                viewingDistanceUp = gridRow
            }

            var viewingDistanceRight = -1
            for (col in gridCol + 1 until grid[1].size) {
                if (grid[gridRow][col] >= grid[gridRow][gridCol]) {
                    viewingDistanceRight = col - gridCol
                    break
                }
            }
            if (viewingDistanceRight < 0) {
                viewingDistanceRight = grid[gridRow].size - gridCol - 1
            }

            var viewingDistanceDown = -1
            for (row in gridRow + 1 until grid.size) {
                if (grid[row][gridCol] >= grid[gridRow][gridCol]) {
                    viewingDistanceDown = row - gridRow
                    break
                }
                if (viewingDistanceDown < 0) {
                    viewingDistanceDown = grid.size - gridRow - 1
                }
            }

            var viewingDistanceLeft = -1
            for (col in gridCol - 1 downTo 0) {
                if (grid[gridRow][col] >= grid[gridRow][gridCol]) {
                    viewingDistanceLeft = gridCol - col
                    break
                }
                if (viewingDistanceLeft < 0) {
                    viewingDistanceLeft = grid[0].size - gridCol - 1
                }
            }

            val result = viewingDistanceUp * viewingDistanceDown * viewingDistanceRight * viewingDistanceLeft
            if (maxScore < result) {
                maxScore = result
            }
        }
    }

    return maxScore

}

fun main() {
    val input = loadFile("./src/main/kotlin/Day08.txt")
    println(part1(input))
    println(part2(input))
}