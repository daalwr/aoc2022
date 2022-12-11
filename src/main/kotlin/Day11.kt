enum class Operation {
    Add, Mult
}

data class Monkey(
    val items: MutableList<Long>,
    val operation: Operation,
    val operationAmount: Long?,
    val divTest: Int,
    val ifTrue: Int,
    val ifFalse: Int,
    var inspections: Long = 0L
)

private fun getMonkeys(input: List<String>) = input.windowed(7, 7, true).map { line ->
    Monkey(
        line[1].drop("  Starting items: ".length).split(", ").map { it.toLong() }.toMutableList(),
        if (line[2].contains("*")) Operation.Mult else Operation.Add,
        line[2].split(" ").last().toLongOrNull(),
        line[3].split(" ").last().toInt(),
        line[4].split(" ").last().toInt(),
        line[5].split(" ").last().toInt(),
    )
}

private fun part1(input: List<String>): Long {
    val monkeys = getMonkeys(input)

    repeat(20) {
        monkeys.forEach { monkey ->
            monkey.items.toList().forEach { item ->
                val newItemWorry = if (monkey.operation == Operation.Add) {
                    item + (monkey.operationAmount ?: item)
                } else {
                    item * (monkey.operationAmount ?: item)
                } / 3

                if (newItemWorry % monkey.divTest == 0L) {
                    monkeys[monkey.ifTrue].items.add(newItemWorry)
                } else {
                    monkeys[monkey.ifFalse].items.add(newItemWorry)
                }
                monkey.inspections++
            }
            monkey.items.clear()
        }
    }

    val sorted = monkeys.map { it.inspections }.sortedDescending()
    return sorted[0] * sorted[1]
}

private fun part2(input: List<String>): Long {
    val monkeys = getMonkeys(input)
    val commonMultiple = monkeys.map { it.divTest }.fold(1L) { x, y -> x * y }

    repeat(10000) {
        monkeys.forEach { monkey ->
            monkey.items.toList().forEach { item ->
                val newItemWorry = if (monkey.operation == Operation.Add) {
                    item + (monkey.operationAmount ?: item)
                } else {
                    item * (monkey.operationAmount ?: item)
                } % commonMultiple

                if (newItemWorry % (monkey.divTest.toLong()) == 0L) {
                    monkeys[monkey.ifTrue].items.add(newItemWorry)
                } else {
                    monkeys[monkey.ifFalse].items.add(newItemWorry)
                }
                monkey.inspections++
            }
            monkey.items.clear()
        }
    }

    val sorted = monkeys.map { it.inspections }.sortedDescending()
    return sorted[0] * sorted[1]
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day11.txt")
    println(part1(input))
    println(part2(input))
}