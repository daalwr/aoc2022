private fun part1(input: List<String>): Int {
    var cycle = 1
    var register = 1
    var result = 0

    val interestingCycleTimes = listOf(20, 60, 100, 140, 180, 220)
    fun checkCycle() {
        if (interestingCycleTimes.contains(cycle)) {
            result += register * cycle
        }
    }

    input.forEach {
        if (it == "noop") {
            checkCycle()
            cycle++
        } else {
            checkCycle()
            cycle++
            checkCycle()
            cycle++
            register += it.drop(5).toInt()
        }
    }

    return result
}

private fun part2(input: List<String>) {
    var cycle = 0
    var register = 1
    val pixels = MutableList(240) { false }

    fun checkPixel() {
        if ((cycle - 1..cycle + 1).map { it % 40 }.contains(register)) {
            pixels[cycle] = true
        }
    }

    input.forEach {
        if (it == "noop") {
            checkPixel()
            cycle++
        } else {
            checkPixel()
            cycle++
            checkPixel()
            cycle++
            register += it.drop(5).toInt()
        }
    }

    pixels.windowed(40, 40).forEach { window ->
        println(window.joinToString("") { if (it) "#" else "." })
    }
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day10.txt")
    println(part1(input))
    part2(input)
}