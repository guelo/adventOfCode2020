package day14

import java.io.File

fun main() {
	var input = File("src/main/kotlin/day14/day14.input")
		.readLines()
	;{
		val mem = mutableMapOf<Long, Long>()
		var (maskAnd, maskOr) = listOf(0L, 0L)
		for(s in input) {
			if (s.take(7) == "mask = ") {
				val mask = s.drop(7)
				maskAnd = mask.replace("X", "1").toLong(2)
				maskOr = mask.replace("X", "0").toLong(2)
			}
			if (s.take(4) == "mem[") {
				var (loc, value) = "\\d+".toRegex().findAll(s).map { it.value.toLong() }.toList()
				mem[loc] = (value and maskAnd) or maskOr
			}
		}
		println(mem.values.sum())
	}()

	;{
		val mem = mutableMapOf<Long, Long>()
		var (maskOr) = listOf(0L, 0L)
		var mask = ""
		for(s in input) {
			if (s.take(7) == "mask = ") {
				mask = s.drop(7)
				maskOr = mask.replace("X", "0").toLong(2)
			}
			if (s.take(4) == "mem[") {
				var (loc, value) = "\\d+".toRegex().findAll(s).map { it.value.toLong() }.toList()
				val floatingBits = mask.mapIndexed { index, c -> index to c }.filter { it.second == 'X' }
				loc = loc or maskOr
				for(i in (0 until (1 shl floatingBits.size))) {
					val bits = i.toString(2).padStart(floatingBits.size, '0')
					val locArray = loc.toString(2).padStart(36, '0').toCharArray()
					bits.forEachIndexed { index, c -> locArray[floatingBits[index].first] = bits[index] }
					val address = locArray.joinToString("").toLong(2)
					mem[address] = value
				}
			}
		}
		println(mem.values.sum())
	}()
}
