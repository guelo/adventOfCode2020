package day1

import java.io.File

fun main() {
    val file = File("src/main/kotlin/day1/day1.input")
    val input = HashSet(file.readLines().map { it.toInt() })

    fun addUpto(total: Int) = input.filter { input.contains(total - it) }

    fun threeAddUpTo(total: Int) = input.filter { addUpto(total - it).isNotEmpty() }

    // part 1
    println(addUpto(2020).let { it[0] * it[1] })
    // part2
    println(threeAddUpTo(2020).let { it[0] * it[1] * it[2] })

}