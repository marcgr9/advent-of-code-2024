fun main() {
    val regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
    val regex2 = "(mul|do|don't)\\(((\\d{1,3}),(\\d{1,3}))?\\)".toRegex()

    fun part1(input: List<String>): Int = input.fold(initial = 0) { acc, it ->
        acc + regex.findAll(it).fold(initial = 0) { acc, it ->
            acc + it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
    }

    fun part2(input: List<String>): Int = input.fold(initial = Pair(0, true)) { acc, it ->
        regex2.findAll(it).fold(initial = acc) { acc, it ->
            val groups = it.groupValues
            when (groups[1]) {
                "do" -> acc.copy(second = true)
                "don't" -> acc.copy(second = false)
                "mul" -> {
                    if (!acc.second) acc
                    else {
                        acc.copy(
                            first = acc.first + it.groupValues[3].toInt() * it.groupValues[4].toInt()
                        )
                    }
                }
                else -> throw IllegalArgumentException("Illegal command ${groups[1]} in $groups")
            }
        }
    }.first

    val testInput = readInput("Day03_test")
    part1(testInput).let {
        check(it == 161) { it }
    }
    val testInput2 = readInput("Day03_test2")
    part2(testInput2).let {
        check(it == 48) { it }
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
