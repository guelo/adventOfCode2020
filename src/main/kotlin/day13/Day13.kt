package day13

import java.io.File

fun main() {
	val (earliestS, schedule) = File("src/main/kotlin/day13/day13.input")
		.readLines()

	val earliest = earliestS.toInt()

	part1(earliest, schedule)

	val buses = schedule.split(",")

	val table = mutableMapOf<Int, Int>()

	for (i in buses.indices) {
		if (buses[i] == "x") continue
		val bid = buses[i].toInt()
		table[bid] = (bid - i) % bid
	}

	val mods = table.keys.sortedDescending()

	var jump = mods[0]
	var time: Long = table[jump]!!.toLong()
	for (next in mods.drop(1)) {

		while (time % next != table[next]!!.toLong()) {
			time += jump;
		}

		jump *= next;
	}

	println( "Part 2: $time")

}

private fun part1(earliest: Int, schedule: String) {
	val buses = schedule.split(",").filterNot { it == "x" }.map { it.toInt() }

	println(buses)
	var time = earliest + 1
	while (true) {
		buses.filter { time % it == 0 }
			.forEach {
				println("${(time - earliest) * it} ")
				return
			}
		time++
	}
}