fun main() {
    fun List<String>.combinationsOfSize(n: Int): List<List<String>> {
        if (n == 0) return listOf(emptyList())

        val result = mutableListOf<List<String>>()
        for (element in this) {
            val smallerCombinations = this.combinationsOfSize(n - 1)
            for (combo in smallerCombinations) {
                result.add(listOf(element) + combo)
            }
        }
        return result
    }

    fun part1(input: List<String>) = input.fold(0L) { acc, it ->
        val total = it.split(":").first().toLong()
        val nums = it.split(":").last().trimStart().split(" ").map { it.toLong() }

        val isOk = listOf("+", "*").combinationsOfSize(nums.size - 1).any { operations ->
            val sum = (1 until nums.size).fold(nums.first()) { acc, it ->
                when (operations[it - 1]) {
                    "+" -> acc + nums[it]
                    "*" -> acc * nums[it]
                    else -> throw IllegalArgumentException()
                }
            }

            sum == total
        }

        acc + if (isOk) total else 0
    }

    fun part2(input: List<String>) = input.fold(0L) { acc, it ->
        val total = it.split(":").first().toLong()
        val nums = it.split(":").last().trimStart().split(" ").map { it.toLong() }

        val isOk =  listOf("+", "*", "|").combinationsOfSize(nums.size - 1).any { operations ->
            val sum = (1 until nums.size).fold(nums.first()) { acc, it ->
                when (operations[it - 1]) {
                    "+" -> acc + nums[it]
                    "*" -> acc * nums[it]
                    "|" -> "$acc${nums[it]}".toLong()
                    else -> throw IllegalArgumentException()
                }
            }

            sum == total
        }

        acc + if (isOk) total else 0
    }

    val testInput = readInput("Day07_test")
    part1(testInput).let {
        check(it == 3749L) { it }
    }
    val testInput2 = readInput("Day07_test")
    part2(testInput2).let {
        check(it == 11387L) { it }
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
