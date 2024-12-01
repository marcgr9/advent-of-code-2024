import kotlin.math.abs

fun main() {
    fun extractLists(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.fold(initial = Pair(mutableListOf<Int>(), mutableListOf<Int>())) { acc, it ->
            it.split("   ").let {
                acc.first.add(it[0].toInt())
                acc.second.add(it[1].toInt())
            }
            acc
        }
    }

    fun part1(input: List<String>): Int {
        val (left, right) = extractLists(input)

        return left.sorted().zip(right.sorted()).fold(initial = 0) { acc, it ->
            acc + abs(it.second - it.first)
        }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = extractLists(input)

        val frequencies = Array(100_000) { 0 }
        right.forEach {
            frequencies[it]++
        }

        return left.fold(initial = 0) { acc, it -> acc + frequencies[it] * it }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
