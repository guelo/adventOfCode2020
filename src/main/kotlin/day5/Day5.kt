package day5

import java.io.File

	fun main() {
		val file = File("src/main/kotlin/day5/day5.input")

		val seats = file.readLines()
			.map { seat(it) }

		println("Part 1: " + seats.maxOf { it.seatId })


		val arr = IntArray(127 * 8) { it }
		seats.forEach { arr[it.seatId] = 0 }

		println(
			"Part 2:" +
					arr.filterIndexed { index, i ->
						index != 0
								&& index != arr.size - 1
								&& i != 0
								&& arr[index - 1] == 0
								&& arr[index + 1] == 0
					}[0]
		)
	}

	data class RowCol(val row: Int, val col: Int) {
		val seatId = row * 8 + col
	}

	fun seat(string: String): RowCol {
		var rowRange = 0..127
		var colRange = 0..7
		string.forEach {
			when (it) {
				'F' -> rowRange = rowRange.half(true)
				'B' -> rowRange = rowRange.half(false)
				'L' -> colRange = colRange.half(true)
				'R' -> colRange = colRange.half(false)
			}
		}
		return RowCol(rowRange.first, colRange.first)
	}

	fun IntRange.half(lower: Boolean): IntRange {
		val halfPoint = (last - first) / 2 + first
		return if (lower) first..halfPoint
		else halfPoint + 1..last
	}