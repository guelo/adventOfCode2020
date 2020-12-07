package day1

import java.io.File

fun main() {

    val day1 = Day1(File("src/main/kotlin/day1/day1.input"))

    println(day1.addUpto(2020).also {
        println(it!!.first * it.second)
    })

    println(day1.threeAddUpTo(2020).also {
        println(it)
        println(it!!.first * it.second * it.third)
    })


}

class Day1(file: File) {
    private val input = HashSet(file.readLines().map { it.toInt() })

    fun threeAddUpTo(total: Int): Triple<Int, Int, Int>? {
        for (i in 0 until total / 2) {
            if (input.contains(i)) {
                println("input.contains($i)")
                val addupTo = addUpto(total - i)
                if (addupTo != null)
                    return Triple(i, addupTo.first, addupTo.second)
            }
        }
        return null
    }

    fun addUpto(total: Int): Pair<Int, Int>? {
        println("ttrying $total")
        for (i in 0 until total / 2) {
            if (input.contains(i) && input.contains(total - i))
                return Pair(i, total - i)
        }

        return null
    }

}
