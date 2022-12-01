import java.io.File

fun loadFile(input: String): List<String> {
    return File(input).readLines()
}