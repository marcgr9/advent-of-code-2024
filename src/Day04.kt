fun main() {
    data class State(
        var total: Int,
        var current: String,
        var dir: Pair<Int, Int>?,
    )

    val directions = arrayOf(
        Pair(0, 1),
        Pair(1, 0),
        Pair(1, 1),
        Pair(0, -1),
        Pair(-1, 0),
        Pair(-1, -1),
        Pair(-1, 1),
        Pair(1, -1),
    )
    fun backtrack(input: List<String>, visited: Array<BooleanArray>, i: Int, j: Int, state: State) {
        if (i < 0 || j < 0 || i >= visited.size || j >= visited[0].size || visited[i][j]) return
        if (!"XMAS".startsWith(state.current + input[i][j])) return

        visited[i][j] = true
        state.current += input[i][j]

        if (state.current == "XMAS") {
            state.total++
        } else {
            if (state.current == "X") {
                directions.forEach {
                    state.dir = it
                    backtrack(input, visited, i + it.first, j + it.second, state)
                }
            } else {
                backtrack(input, visited, i + state.dir!!.first, j + state.dir!!.second, state)
            }
        }
        visited[i][j] = false
        state.current = state.current.dropLast(1)
    }

    fun part1(input: List<String>): Int {
        val state = State(0, "", null)
        val vis = Array(input.size) {
            BooleanArray(input[0].length) { false }
        }
        input.indices.forEach { i ->
            input[i].indices.forEach { j ->
                backtrack(
                    input,
                    vis,
                    i,
                    j,
                    state,
                )
            }
        }

        return state.total
    }

    val part2Regex = buildList {
        add(Pair("(?=(S.M))".toRegex(), "(S.M)".toRegex()))
        add(Pair("(?=(M.S))".toRegex(), "(M.S)".toRegex()))
        add(Pair("(?=(S.S))".toRegex(), "(M.M)".toRegex()))
        add(Pair("(?=(M.M))".toRegex(), "(S.S)".toRegex()))
    }
    fun part2(input: List<String>): Int = part2Regex.fold(initial = 0) { acc, rg ->
        input.mapIndexed { ind, it ->
            rg.first.findAll(it).fold(initial = 0) { acc, it ->
                val range = it.groups[1]!!.range
                try {
                    // if in the middle of the group, on next line, there's an "A"
                    if (input[ind + 1][range.elementAt(1)] != 'A') return@fold acc

                    // if the next next line has the matching regex in the matching range
                    val stringOnLastLine = buildString {
                        range.forEach {
                            append(input[ind + 2][it])
                        }
                    }
                    if (rg.second.matches(stringOnLastLine)) {
                        acc + 1
                    } else acc
                } catch (ex: Exception) {
                    // easier than to check for bounds
                    acc
                }
            }
        }.sum() + acc
    }


    val testInput = readInput("Day04_test")
    part1(testInput).let {
//        check(it == 18) { it }
    }
    val testInput2 = readInput("Day04_test")
    part2(testInput2).let {
        check(it == 9) { it }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
