import kotlin.math.abs

fun main() {
    data class Antenna(
        val symbol: Char,
        val i: Int,
        val j: Int,
    )

    fun inBounds(i: Int, j: Int, n: Int, m: Int): Boolean {
        return i >= 0 && j >= 0 && n > i && m > j
    }

    fun part1(input: List<String>): Int {
        val antennas = input.mapIndexedNotNull { ind, it ->
            it.mapIndexedNotNull { j, c ->
                if (c != '.') {
                    Antenna(c, ind, j)
                } else null
            }.takeIf { it.isNotEmpty() }
        }.flatten().groupBy { it.symbol }

        val set = mutableSetOf<String>()
        val new = buildList {
            input.forEach {
                add(it.toCharArray())
            }
        }
        antennas.keys.forEach {
            val pairs = antennas[it]!!.flatMapIndexed { i, a ->
                antennas[it]!!.drop(i + 1).map { b -> a to b }
            }
            pairs.forEach {
                val indices = mutableListOf<Pair<Int, Int>>()
                val distI = abs(it.second.i - it.first.i)
                val distJ = abs(it.second.j - it.first.j)

                when {
                    it.first.i < it.second.i && it.first.j < it.second.j -> {
                        indices.add(Pair(
                            it.first.i - distI,
                            it.first.j - distJ,
                        ))
                        indices.add(Pair(
                            it.second.i + abs(it.second.i - it.first.i),
                            it.second.j + abs(it.second.j - it.first.j),
                        ))
                    }
                    it.first.i < it.second.i && it.first.j > it.second.j -> {
                        indices.add(Pair(
                            it.first.i - abs(it.second.i - it.first.i),
                            it.first.j + abs(it.second.j - it.first.j),
                        ))
                        indices.add(Pair(
                            it.second.i + abs(it.second.i - it.first.i),
                            it.second.j - abs(it.second.j - it.first.j),
                        ))
                    }
                    it.first.i > it.second.i && it.first.j > it.second.j -> {
                        indices.add(Pair(
                            it.first.i + abs(it.second.i - it.first.i),
                            it.first.j - abs(it.second.j - it.first.j),
                        ))
                        indices.add(Pair(
                            it.second.i - abs(it.second.i - it.first.i),
                            it.second.j + abs(it.second.j - it.first.j),
                        ))
                    }
                    it.first.i > it.second.i && it.first.j < it.second.j -> {
                        indices.add(Pair(
                            it.first.i + abs(it.second.i - it.first.i),
                            it.first.j + abs(it.second.j - it.first.j),
                        ))
                        indices.add(Pair(
                            it.second.i - abs(it.second.i - it.first.i),
                            it.second.j - abs(it.second.j - it.first.j),
                        ))
                    }
                }

                indices.forEach {
                    if (inBounds(it.first, it.second, input.size, input.first().length)) {
                        set.add("${it.first}-${it.second}")
                    }
                }
            }
        }
        return set.size
    }

    fun part2(input: List<String>): Int {
        val antennas = input.mapIndexedNotNull { ind, it ->
            it.mapIndexedNotNull { j, c ->
                if (c != '.') {
                    Antenna(c, ind, j)
                } else null
            }.takeIf { it.isNotEmpty() }
        }.flatten().groupBy { it.symbol }

        val set = mutableSetOf<String>()
        antennas.keys.forEach {
            val pairs = antennas[it]!!.flatMapIndexed { i, a ->
                antennas[it]!!.drop(i + 1).map { b -> a to b }
            }
            pairs.forEach {
                val indices = mutableListOf<Pair<Int, Int>>()

                when {
                    it.first.i < it.second.i && it.first.j < it.second.j -> {
                        repeat(input.size) { d ->
                            indices.add(Pair(
                                it.first.i - abs(it.second.i - it.first.i) * d,
                                it.first.j - abs(it.second.j - it.first.j) * d,
                            ))
                            indices.add(Pair(
                                it.second.i + abs(it.second.i - it.first.i) * d,
                                it.second.j + abs(it.second.j - it.first.j) * d,
                            ))
                        }
                    }
                    it.first.i < it.second.i && it.first.j > it.second.j -> {
                        repeat(input.size) { d ->
                            indices.add(Pair(
                                it.first.i - abs(it.second.i - it.first.i) * d,
                                it.first.j + abs(it.second.j - it.first.j) * d,
                            ))
                            indices.add(Pair(
                                it.second.i + abs(it.second.i - it.first.i) * d,
                                it.second.j - abs(it.second.j - it.first.j) * d,
                            ))
                        }

                    }
                    it.first.i > it.second.i && it.first.j > it.second.j -> {
                        repeat(input.size) { d ->
                            indices.add(Pair(
                                it.first.i + abs(it.second.i - it.first.i) * d,
                                it.first.j - abs(it.second.j - it.first.j) * d,
                            ))
                            indices.add(Pair(
                                it.second.i - abs(it.second.i - it.first.i) * d,
                                it.second.j + abs(it.second.j - it.first.j) * d,
                            ))
                        }

                    }
                    it.first.i > it.second.i && it.first.j < it.second.j -> {
                        repeat(input.size) { d ->
                            indices.add(Pair(
                                it.first.i + abs(it.second.i - it.first.i) * d,
                                it.first.j + abs(it.second.j - it.first.j) * d,
                            ))
                            indices.add(Pair(
                                it.second.i - abs(it.second.i - it.first.i) * d,
                                it.second.j - abs(it.second.j - it.first.j) * d,
                            ))
                        }

                    }
                }

                indices.forEach {
                    if (inBounds(it.first, it.second, input.size, input.first().length)) {
                        set.add("${it.first}-${it.second}")
                    }
                }
            }
        }
        return set.size
    }

    val testInput = readInput("Day08_test")
    part1(testInput).let {
        check(it == 14) { it }
    }
    val testInput2 = readInput("Day08_test2")
    part2(testInput2).let {
        check(it == 9) { it }
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
