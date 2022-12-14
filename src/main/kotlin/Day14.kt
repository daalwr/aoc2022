enum class Cell {
    Rock, Air, Sand
}

private fun cellToString(cell: Cell): String = when (cell) {
    Cell.Rock -> "#"
    Cell.Air -> "."
    Cell.Sand -> "o"
}

private fun getPaths(input: List<String>) = input
    .map { line ->
        line.split(" -> ")
            .map { s ->
                s.split(",")
                    .let { x -> Pair(x[0].toInt(), x[1].toInt()) }
            }
    }

private fun printPicture(grid: MutableList<MutableList<Cell>>) {
    val picture = grid.joinToString("\n") { row -> row.joinToString("") { cellToString(it) } }
    println(picture)
}

private fun drawPaths(
    paths: List<List<Pair<Int, Int>>>,
    grid: MutableList<MutableList<Cell>>
) {
    paths.forEach { path ->
        path.windowed(2).forEach { pathSegment ->
            val start = pathSegment[0]
            val finish = pathSegment[1]

            if (start.first == finish.first) {
                val x = start.first
                for (y in minOf(start.second, finish.second)..maxOf(start.second, finish.second)) {
                    grid[y][x] = Cell.Rock
                }

            } else {
                val y = start.second
                for (x in minOf(start.first, finish.first)..maxOf(start.first, finish.first)) {
                    grid[y][x] = Cell.Rock
                }
            }
        }
    }
}


private fun part1(input: List<String>): Int {
    val paths = getPaths(input)

    val minX = paths.minOf { row -> row.minOf { it.first } }

    val offsetPaths = paths.map { row -> row.map { c -> Pair(c.first - minX, c.second) } }

    val maxX = offsetPaths.maxOf { row -> row.maxOf { it.first } }
    val maxY = offsetPaths.maxOf { row -> row.maxOf { it.second } }

    val grid = MutableList(maxY + 1) { MutableList(maxX + 1) { Cell.Air } }

    drawPaths(offsetPaths, grid)

    var reachedEdge = false
    var count = 0

    while (!reachedEdge) {
        var prevX: Int? = null
        var prevY: Int? = null

        var x = 500 - minX
        var y = 0

        while (prevX != x || prevY != y) {
            prevX = x
            prevY = y

            if (y + 1 >= grid.size) {
                reachedEdge = true
            } else if (grid[y + 1][x] == Cell.Air) {
                y++
            } else if ((x - 1) < 0) {
                reachedEdge = true
            } else if (grid[y + 1][x - 1] == Cell.Air) {
                y++
                x--
            } else if ((x + 1) >= maxX) {
                reachedEdge = true
            } else if (grid[y + 1][x + 1] == Cell.Air) {
                y++
                x++
            }
        }

        if (y + 1 <= maxY) {
            grid[y][x] = Cell.Sand
            count++
        }
    }

    return count
}


private fun part2(input: List<String>): Int {
    val paths = getPaths(input)
    val maxY = paths.maxOf { row -> row.maxOf { it.second } } + 2
    val grid = MutableList(maxY) { MutableList(1000) { Cell.Air } }

    drawPaths(paths, grid)

    var count = 0
    var sandFlowing = true

    while (sandFlowing) {
        var prevX = 99999
        var prevY = 99999

        var x = 500
        var y = 0
        sandFlowing = false
        while (prevX != x || prevY != y) {
            prevX = x
            prevY = y

            if (y + 1 < maxY && grid[y + 1][x] == Cell.Air) {
                y++
                sandFlowing = true
            } else if (y + 1 < maxY && grid[y + 1][x - 1] == Cell.Air) {
                y++
                x--
                sandFlowing = true
            } else if (y + 1 < maxY && grid[y + 1][x + 1] == Cell.Air) {
                y++
                x++
                sandFlowing = true
            }
        }

        grid[y][x] = Cell.Sand
        count++

        if (y == 500 && x == 0) {
            sandFlowing = false
        }
    }

    return count
}


fun main() {
    val input = loadFile("./src/main/kotlin/Day14.txt")
    println(part1(input))
    println(part2(input))
}
