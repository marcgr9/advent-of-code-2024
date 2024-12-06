val goodPagePredicate: (page: List<Int>, rules: Map<Int, List<Int>>) -> Boolean = { page, rules ->
    page.all { num ->
        page.takeWhile { it != num }.all {
            rules[num] == null || rules[num]?.contains(it) == false
        }
    }
}

fun main() {
    fun parseInput(input: List<String>): Pair<List<List<Int>>, Map<Int, List<Int>>> {
        val rules = input.filter { it.contains("|") }.map {
            it.split("|").let { arrayOf(it[0].toInt(), it[1].toInt()) }
        }
        val pages = input.filter { it.contains(",") }.map {
            it.split(",").map { it.toInt() }
        }

        val rulesMap = mutableMapOf<Int, List<Int>>()
        rules.forEach {
            if (!rulesMap.containsKey(it[0])) {
                rulesMap[it[0]] = listOf()
            }
            rulesMap[it[0]] = rulesMap[it[0]]!! + it[1]
        }

        return Pair(pages, rulesMap)
    }

    fun part1(input: List<String>): Int {
        val (pages, rulesMap) = parseInput(input)

        return pages
            .filter {
                goodPagePredicate(it, rulesMap)
            }
            .sumOf {
                it[it.size / 2]
            }
    }

    fun part2(input: List<String>): Int {
        val (pages, rulesMap) = parseInput(input)

        return pages
            .filterNot {
                goodPagePredicate(it, rulesMap)
            }
            .sumOf {
                it.sortedWith { o1, o2 ->
                    if (rulesMap[o1] == null || rulesMap[o1]?.contains(o2) == false) 1
                    else -1
                }.elementAt(it.size / 2)
            }
    }


    val testInput = readInput("Day05_test")
    part1(testInput).let {
        check(it == 143) { it }
    }
    val testInput2 = readInput("Day05_test")
    part2(testInput2).let {
        check(it == 123) { it }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
