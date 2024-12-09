import java.lang.reflect.Array.set

fun main() {
    fun part1(input: List<String>): Long = input.first().let { it ->
        val extended = it.split("").filterNot { it.isBlank() }.map { it.toInt() }.mapIndexed { index, i ->
            buildList {
                repeat(i) {
                    add(
                        if (index % 2 == 0) (index/2).toChar()
                        else null
                    )
                }
            }
        }.flatten()

        val numberOfNums = extended.filterNot { it == null }.size

        var builtString = extended
        while (builtString.takeWhile { it != null }.size != numberOfNums) {
            val lastNumIndex = builtString.indexOfLast { it != null }
            val firstDotIndex = builtString.indexOfFirst { it == null }
            builtString = builtString.toMutableList().apply {
                set(firstDotIndex, builtString[lastNumIndex])
                set(lastNumIndex, null)
            }
        }

        builtString.foldIndexed(0L) { ind, acc, char ->
            if (char == null) acc
            else acc + (ind * (char.code)).toLong()
        }
    }

    fun part2(input: List<String>): Long = input.first().let { it ->
        var lastNum: Char = '±'
        val extended = it.split("").filterNot { it.isBlank() }.map { it.toInt() }.mapIndexed { index, i ->
            lastNum = (index / 2).toChar()
            buildList {
                repeat(i) {
                    add(
                        if (index % 2 == 0) (index/2).toChar()
                        else null
                    )
                }
            }
        }.flatten()

        var builtString = extended
        while (lastNum.code > 0) {
            val lastNumIndex = builtString.indexOfLast { it == lastNum }
            val firstIndexOfLastNum = builtString
                .subList(0, lastNumIndex + 1)
                .withIndex()
                .toList()
                .takeLastWhile { it.value == lastNum }
                .firstOrNull()
                ?.index

            firstIndexOfLastNum?.let { firstIndexOfSeq ->
                val size = lastNumIndex - firstIndexOfSeq + 1
                builtString.forEachIndexed { ind, it ->
                    if (ind >= firstIndexOfSeq) return@let
                    if (it == null) {
                        if (builtString.subList(ind, builtString.size).takeWhile { it == null }.size >= size) {
                            (0 until size).forEach {
                                builtString = builtString.toMutableList().apply {
                                    this[ind + it] = lastNum
                                    this[firstIndexOfSeq + it] = null
                                }
                            }

                            return@let
                        }
                    }
                }
            }
            lastNum = (lastNum.code - 1).toChar()
        }


        builtString.foldIndexed(0L) { ind, acc, char ->
            if (char == null) acc
            else acc + (ind * (char.code)).toLong()
        }
    }

    val testInput = readInput("Day09_test")
    part1(testInput).let {
        check(it == 1928L) { it }
    }
    val testInput2 = readInput("Day09_test")
    part2(testInput2).let {
        check(it == 2858L) { it }
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
