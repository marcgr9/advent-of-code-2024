import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.map {
            it.toCharArray()
        }
        var i = input.indexOfFirst { "[\\^<v>]".toRegex().containsMatchIn(it) }
        var j = input[i].indexOfFirst { it != '#' && it != '.' }
        val positions = mutableSetOf<String>()
        var steps = 0

        return try {
            while (steps < 10000000) {
                steps++
                positions.add("$i-$j")
                when (matrix[i][j]) {
                    '^' -> {
                        if (i - 1 >= 0 && matrix[i-1][j] == '#') {
                            matrix[i][j] = '>'
                            continue
                        } else {
                            matrix[i][j] = '.'
                            matrix[i - 1][j] = '^'
                            i--
                        }
                    }
                    '>' -> {
                        if (j + 1 < matrix.first().size && matrix[i][j+1] == '#') {
                            matrix[i][j] = 'v'
                            continue
                        } else {
                            matrix[i][j] = '.'
                            matrix[i][j+1] = '>'
                            j++
                        }
                    }
                    'v' -> {
                        if (i + 1 < matrix.size && matrix[i+1][j] == '#') {
                            matrix[i][j] = '<'
                            continue
                        } else {
                            matrix[i][j] = '.'
                            matrix[i + 1][j] = 'v'
                            i++
                        }
                    }
                    '<' -> {
                        if (j - 1 >= 0 && matrix[i][j-1] == '#') {
                            matrix[i][j] = '^'
                            continue
                        } else {
                            matrix[i][j] = '.'
                            matrix[i][j - 1] = '<'
                            j--
                        }
                    }
                    else -> throw IllegalStateException(matrix[i][j].toString())
                }
            }

            // kotlin compiler isn't that smart after all :-)
            // it should have known that i won't ever reach here and a return isn't needed
            -1
        } catch (e: IndexOutOfBoundsException) {
            positions.size
        }
    }

    fun part2(input: List<String>): Int = runBlocking {
        var total = 0
        val soldierI = input.indexOfFirst { "[\\^<v>]".toRegex().containsMatchIn(it) }
        val soldierJ = input[soldierI].indexOfFirst { it != '#' && it != '.' }

        input.indices.map { i ->
            input.first().indices.map { j ->
                async(Dispatchers.Default) {
                    if (i == soldierI && j == soldierJ) return@async
                    if (input[i][j] == '#') return@async
                    val newMap = buildList {
                        addAll(input.map { it })
                    }.map {
                        it.toCharArray()
                    }
                    newMap[i][j] = '#'
                    if (part1(newMap.map { it.joinToString("") }) == -1) {
                        total++
                    }
                }
            }
        }.flatten().awaitAll()
        return@runBlocking total
    }


    val testInput = readInput("Day06_test")
    part1(testInput).let {
        check(it == 41) { it }
    }
    val testInput2 = readInput("Day06_test")
    part2(testInput2).let {
        check(it == 6) { it }
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
