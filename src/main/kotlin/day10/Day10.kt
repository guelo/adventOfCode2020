package day10

import java.io.File

fun main() {
	val nums = File("src/main/kotlin/day10/day10.input")
		.readLines().map { it.toInt() }
	val sorted = nums.sorted()

	println(part1(sorted))
	timer.end("a")
	println(part2(sorted))
	//timer.end("a")

}

	fun part2(sorted: List<Int>): Long {
		timer.end("arr")
		val dp = LongArray(sorted.size + 2)
		val all = ArrayList<Int>(sorted.size + 2).apply {
			add(0)
			addAll(sorted)
			add(last() + 3)
		}
		timer.end("loop")

		for (i in all.indices) {
			when (i) {
				0, 1 -> dp[i]=1
				else -> {
					timer.end("add")
					val x = (if (all[i] - all[i - 1] < 4) dp[i - 1] else 0) +
							(if (all[i] - all[i - 2] < 4) dp[i - 2] else 0) +
							(if (i > 2 && all[i] - all[i - 3] < 4) dp[i - 3] else 0)
					timer.end("dp")
					dp[i]= x
					timer.end()
				}
			}
		}
		timer.end()

		return dp.last()
	}
val timer = Timer()
class Timer {
	var a = System.nanoTime()
	var name: String? = ""
	fun end(name: String? = null) {
		val b = System.nanoTime()

		if (name!=null) println("${name} ${b-a} ns ")
		a = System.nanoTime()
		this.name = name
	}
}

	fun part1(sorted: List<Int>): Int {

		val diffCounter = IntArray(4)

		fun diff(a: Int, b: Int) {
			diffCounter[b - a]++
		}

		diff(0, sorted[0])
		diff(0, 3)

		for (i in 1 until sorted.size) {
			diff(sorted[i - 1], sorted[i])
		}

		return diffCounter[1] * diffCounter[3]

	}