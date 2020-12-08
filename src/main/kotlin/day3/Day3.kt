package day3

import java.io.File

    fun trees(input: List<CharArray>, right: Int, down: Int) =
        input
            .filterIndexed { i, _ -> i % down == 0 }
            .filterIndexed { i, chars ->
                val idx = (i * right) % chars.size
                i != 0 && chars[idx] == '#'
            }
            .count()

    fun main() {
        val input = File("src/main/kotlin/day3/day3.input")
            .readLines()
            .map { it.toCharArray() }


        println("part1 " + trees(input, 3, 1))

        println(
            "part2 " + (
                    trees(input, 1, 1).toLong()
                            * trees(input, 3, 1)
                            * trees(input, 5, 1)
                            * trees(input, 7, 1)
                            * trees(input, 1, 2)
                    )
        )
    }
