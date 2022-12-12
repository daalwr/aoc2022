import java.util.PriorityQueue

data class GridData(val grid: List<List<Int>>, val startC: Int, val startR: Int, val endC: Int, val endR: Int)

private fun getGrid(input: List<String>): GridData {
    var startC = 0
    var startR = 0

    var endC = 0
    var endR = 0

    val grid = input.mapIndexed { r, row ->
        row.toCharArray().mapIndexed { c, char ->

            if (char == 'S') {
                startC = c
                startR = r
            }

            if (char == 'E') {
                endC = c
                endR = r
            }

            if (char in 'a'..'z') {
                char.code - 97
            } else if (char == 'S') {
                0
            } else {
                25
            }
        }
    }

    return GridData(grid, startC, startR, endC, endR)
}

private fun shortestPath(gridData: GridData): Int {
    val grid = gridData.grid

    data class NodeToVisit(val r: Int, val c: Int, val distSoFar: Int)

    val visitedNodes = mutableSetOf<Pair<Int, Int>>()
    val nodesToVisit = PriorityQueue<NodeToVisit> { o1, o2 -> o1.distSoFar - o2.distSoFar }
    nodesToVisit.add(NodeToVisit(gridData.startR, gridData.startC, 0))

    while (nodesToVisit.size > 0) {
        val currentNode = nodesToVisit.poll()!!
        val possibleDirections = listOf(
            Pair(currentNode.r + 1, currentNode.c),
            Pair(currentNode.r - 1, currentNode.c),
            Pair(currentNode.r, currentNode.c + 1),
            Pair(currentNode.r, currentNode.c - 1),
        )

        possibleDirections.forEach { dir ->
            val newR = dir.first
            val newC = dir.second
            if (newR >= 0 && newC >= 0 && newR < grid.size && newC < grid[0].size) {
                if (!visitedNodes.contains(Pair(newR, newC))) {
                    if (grid[newR][newC] <= grid[currentNode.r][currentNode.c] + 1) {
                        val existingEntry = nodesToVisit.find { it.c == newC && it.r == newR }
                        if (existingEntry != null) {
                            if (existingEntry.distSoFar > currentNode.distSoFar + 1) {
                                nodesToVisit.removeIf { it.c == newC && it.r == newR }
                                nodesToVisit.add(NodeToVisit(newR, newC, currentNode.distSoFar + 1))
                            }
                        } else {
                            nodesToVisit.add(NodeToVisit(newR, newC, currentNode.distSoFar + 1))
                        }
                    }
                }
            }
        }

        if (currentNode.c == gridData.endC && currentNode.r == gridData.endR) {
            return currentNode.distSoFar
        }

        visitedNodes.add(Pair(currentNode.r, currentNode.c))
    }

    return Integer.MAX_VALUE
}


private fun part1(input: List<String>): Int = shortestPath(getGrid(input))

private fun part2(input: List<String>): Int {
    var shortestPathFromAnyStartingPoint = Integer.MAX_VALUE
    val gridData = getGrid(input)

    gridData.grid.forEachIndexed { ri, row ->
        row.forEachIndexed { ci, height ->
            if (height == 0) {
                val result = shortestPath(GridData(gridData.grid, ci, ri, gridData.endC, gridData.endR))
                shortestPathFromAnyStartingPoint = minOf(result, shortestPathFromAnyStartingPoint)
            }
        }
    }

    return shortestPathFromAnyStartingPoint
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day12.txt")
    println(part1(input))
    println(part2(input))
}