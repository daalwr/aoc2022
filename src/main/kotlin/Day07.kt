import kotlin.collections.ArrayDeque

sealed interface FileSystemItem {
    val name: String
}

data class Directory(override val name: String, val contents: MutableList<FileSystemItem> = mutableListOf()) :
    FileSystemItem

data class File(override val name: String, val size: Long) : FileSystemItem

private fun calculateSize(directory: Directory): Long =
    directory.contents.sumOf {
        when (it) {
            is File -> it.size
            is Directory -> calculateSize(it)
        }
    }

fun createDirectoryTree(input: List<String>): Directory {
    val rootDirectory = Directory("root", mutableListOf())
    val directoryStack = ArrayDeque<Directory>(listOf(rootDirectory))

    for (currentLine in input) {
        if (currentLine == "$ cd /" || currentLine.startsWith("$ ls")) {
            // Ignore
        } else if (currentLine == "$ cd ..") {
            directoryStack.removeLast()
        } else if (currentLine.startsWith("$ cd ")) {
            val dirName = currentLine.drop(5)
            val newDirectory =
                directoryStack
                    .last()
                    .contents
                    .find { it.name == dirName && it is Directory } as Directory
            directoryStack.add(newDirectory)
        } else if (currentLine.startsWith("dir ")) {
            val dirName = currentLine.drop(4)
            directoryStack.last().contents.add(Directory(dirName))
        } else {
            // Must be a file
            val fileStrings = currentLine.split(" ")
            val file = File(fileStrings[1], fileStrings[0].toLong())
            directoryStack.last().contents.add(file)
        }
    }

    return rootDirectory
}

fun day07a(input: List<String>): Long {
    val rootDirectory = createDirectoryTree(input)
    var result = 0L
    val queue = ArrayDeque<Directory>()
    queue.add(rootDirectory)

    while (queue.isNotEmpty()) {
        val dir = queue.removeFirst()
        val size = calculateSize(dir)

        if (size <= 100000L) {
            result += size
        }

        for (d in dir.contents) {
            if (d is Directory) {
                queue.add(d)
            }
        }
    }

    return result
}

fun day07b(input: List<String>): Long {
    val rootDirectory = createDirectoryTree(input)
    val diskSpace = 70000000L
    val requiredFreeSpace = 30000000L
    val usedSpace = calculateSize(rootDirectory)
    val spaceToDelete = usedSpace - diskSpace + requiredFreeSpace

    val queue = ArrayDeque<Directory>()
    queue.add(rootDirectory)

    var smallestEligibleDirSize = Long.MAX_VALUE

    while (queue.isNotEmpty()) {
        val dir = queue.removeFirst()
        val size = calculateSize(dir)

        if (size in spaceToDelete until smallestEligibleDirSize) {
            smallestEligibleDirSize = size
        }

        for (d in dir.contents) {
            if (d is Directory) {
                queue.add(d)
            }
        }
    }

    return smallestEligibleDirSize
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day07.txt")
    println(day07a(input))
    println(day07b(input))
}