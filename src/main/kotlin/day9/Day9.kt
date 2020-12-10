package day9

import java.io.File


fun main() {
	val nums = File("src/main/kotlin/day9/day9.input")
		.readLines().map { it.toLong() }


	val invalid = part1(nums)
	println(invalid)

	println(part2(invalid, nums))
}

fun part2(target: Long, nums: List<Long>): Long {
	var lo = 0
	var hi = 1
	var sum = nums[lo] + nums[hi]

	fun increaseHi() {
		hi++
		sum += nums[hi]
	}

	while (hi < nums.size) {
		when {
			sum < target -> increaseHi()
			sum > target -> {
				sum -= nums[lo]
				lo++
				if (lo==hi) increaseHi()
			}
			else -> {
				val range = lo..hi
				val min = nums.slice(range).minOrNull()!!
				val max = nums.slice(range).maxOrNull()!!
				return  min + max
			}
		}
	}

	TODO() // shouldn't happen
}

fun part1(nums: List<Long>): Long {
	(25 until nums.size).forEach { idx ->
		val prev25 = nums.slice(idx - 25..idx - 1)
		if (prev25.addUpto(nums[idx]).isEmpty()) {
			return nums[idx]
		}
	}

	TODO()

}

fun List<Long>.addUpto(total: Long) = filter { contains(total - it) }
