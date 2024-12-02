fun main() {
    fun part1(input: List<String>): Int = input.fold(initial = 0) { acc, it ->
        val nums = it.split(" ").map { it.toInt() }
        val isDecreasing = nums[1] < nums[0]
        val isSafe = (1 until nums.size).all {
            nums[it] - nums[it - 1] in listOf(1, 2, 3).map {
                if (isDecreasing) it * -1
                else it
            }
        }
        acc + if (isSafe) 1 else 0
    }

    fun part2(input: List<String>): Int = input.fold(initial = 0) { acc, it ->
        val nums = it.split(" ").map { it.toInt() }
        val isSafe = (-1 until (nums.size)).any { ind ->
            val newNums = nums.toMutableList().apply {
                if (ind >= 0) removeAt(ind)
            }

            val isDecreasing = newNums[1] < newNums[0]

            (1 until newNums.size).all {
                newNums[it] - newNums[it - 1] in listOf(1, 2, 3).map {
                    if (isDecreasing) it * -1
                    else it
                }
            }
        }

        acc + if (isSafe) 1 else 0
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
