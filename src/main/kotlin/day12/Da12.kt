package day12

import java.io.File
import kotlin.math.abs

fun main() {
	val commands = File("src/main/kotlin/day12/day12.input")
		.readLines().map {
			Pair(it.take(1), it.drop(1).toInt())
		}

	println(part1(commands))

	println(part2(commands))
}

fun part2(commands: List<Pair<String, Int>>): Int {
	var wx = 10
	var wy = 1
	var x = 0
	var y = 0

	for ((command, units) in commands) {
		when (command) {
			"N" -> wy += units
			"S" -> wy -= units
			"E" -> wx += units
			"W" -> wx -= units
			"L" -> {
				repeat(units / 90) {
					wy *= -1
					val tmp = wx
					wx = wy
					wy = tmp
				}
			}
			"R" -> {
				repeat(units / 90) {
					wx *= -1
					val tmp = wx
					wx = wy
					wy = tmp
				}
			}
			"F" -> {
				x += wx * units
				y += wy * units
			}
			else -> TODO()
		}
	}

	return abs(x) + abs(y)


}

fun part1(commands: List<Pair<String, Int>>): Int {
	var x = 0
	var y = 0
	var dir = 0

	for ((command, units) in commands) {
		when (command) {
			"N" -> y += units
			"S" -> y -= units
			"E" -> x += units
			"W" -> x -= units
			"L" -> {
				dir += units
				if (dir >= 360) dir -= 360
			}
			"R" -> {
				dir -= units
				if (dir < 0) dir += 360
			}
			"F" -> when (dir) {
				0 -> x += units
				90 -> y += units
				180 -> x -= units
				270 -> y -= units
				else -> {
					println(dir)
					TODO()
				}
			}
			else -> TODO()
		}
	}

	return abs(x) + abs(y)
}