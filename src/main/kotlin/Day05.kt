import java.util.*

/*
        [M]     [B]             [N]
[T]     [H]     [V] [Q]         [H]
[Q]     [N]     [H] [W] [T]     [Q]
[V]     [P] [F] [Q] [P] [C]     [R]
[C]     [D] [T] [N] [N] [L] [S] [J]
[D] [V] [W] [R] [M] [G] [R] [N] [D]
[S] [F] [Q] [Q] [F] [F] [F] [Z] [S]
[N] [M] [F] [D] [R] [C] [W] [T] [M]
 1   2   3   4   5   6   7   8   9
 */

fun getStacks(): List<Stack<Char>> {
    val stacks: List<String> =
        listOf("TQVCDSN", "FVM", "MHNPDWQF", "FTRQD", "BVHQNMFR", "QWPNGFC", "TCLRFW", "SNZT", "NHQRJDSM")

    return stacks.map { it ->
        val stack = Stack<Char>()
        val reversedInput = it.reversed()
        for (char in reversedInput) {
            stack.add(char)
        }
        stack
    }
}

fun getInstructions(input: List<String>) = input.map {
    val itemsToMove = it.drop(5).split(" ").first().toInt()
    val from = it.split(" to ").first().split(" ").last().toInt()
    val to = it.split(" to ").last().toInt()
    Triple(itemsToMove, from, to)
}

fun day05a(input: List<String>): String {
    val stacks = getStacks()
    getInstructions(input).forEach { instruction ->
        for (i in 0 until instruction.first) {
            val itemRemoved = stacks[instruction.second - 1].pop()!!
            stacks[instruction.third - 1].add(itemRemoved)
        }
    }
    return stacks.map { it.lastElement() }.joinToString("")
}

fun day05b(input: List<String>): String {
    val stacks = getStacks()
    getInstructions(input).forEach { instruction ->
        val itemsRemoved = mutableListOf<Char>()
        for (i in 0 until instruction.first) {
            itemsRemoved.add(stacks[instruction.second - 1].pop()!!)
        }
        for (itemToAdd in itemsRemoved.reversed()) {
            stacks[instruction.third - 1].add(itemToAdd)
        }
    }
    return stacks.map { it.lastElement() }.joinToString("")
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day05.txt")
    println(day05a(input))
    println(day05b(input))
}