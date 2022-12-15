import java.math.BigInteger
import kotlin.math.abs

data class BeaconData(val sensorX: Int, val sensorY: Int, val beaconX: Int, val beaconY: Int) {
    val distance = abs(beaconX - sensorX) + abs(beaconY - sensorY)
}

private fun getBeaconData(input: List<String>) = input.map { row ->
    BeaconData(
        row.split("x=")[1].split(",")[0].toInt(),
        row.split("y=")[1].split(":")[0].toInt(),
        row.split("x=")[2].split(",")[0].toInt(),
        row.split("y=")[2].split(":")[0].toInt(),
    )
}

private fun isCellNotCoveredByBeacon(
    beaconData: List<BeaconData>,
    col: Int,
    row: Int
) = !(beaconData.indices.any { i ->
    val sensor = beaconData[i]
    abs(col - sensor.sensorX) + abs(row - sensor.sensorY) <= sensor.distance
} && !beaconData.any { it.beaconX == col && it.beaconY == row || it.sensorX == col && it.sensorY == row })


private fun part1(input: List<String>, r: Int): Int {
    val beaconData = getBeaconData(input)

    val minBeaconX = beaconData.map { it.beaconX }.min()
    val maxBeaconX = beaconData.map { it.beaconX }.max()

    val maxSensorToBeaconDistance = beaconData.map { it.distance }.max()

    return (minBeaconX - maxSensorToBeaconDistance..maxBeaconX + maxSensorToBeaconDistance).map { c ->
        !isCellNotCoveredByBeacon(beaconData, c, r)
    }.count { it }
}

private fun part2(input: List<String>, maxSize: Int): BigInteger {
    val beaconData = getBeaconData(input)

    for (s in beaconData.indices) {
        val sX = beaconData[s].sensorX
        val sY = beaconData[s].sensorY
        val dist = beaconData[s].distance + 1

        for (dR in -dist..dist) {
            val row = sY + dR
            if (row in 0..maxSize) {
                val remaining = dist - abs(dR)
                val cols = listOf(sX + remaining, sX - remaining).filter { it in 0..maxSize }
                for (col in cols) {
                    if (isCellNotCoveredByBeacon(beaconData, col, row)) {
                        return col.toBigInteger() * (4000000).toBigInteger() + row.toBigInteger()
                    }
                }
            }
        }
    }

    throw Exception("Not found")
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day15.txt")
    println(part1(input, 2000000))
    println(part2(input, 4000000))
}

