package day14

import backtracking.BinaryCounter
import java.io.File

fun main() {
	val lines = File("src/main/kotlin/day14/day14.input")
		.readLines()

	val cmds: List<CMD> = parse(lines)
	println(part1(cmds))

	println(part2(cmds))
}

fun part2(cmds: List<CMD>): Long {
	var sum = 0L
	val addrs = mutableMapOf<Long, Long>()
	(cmds.indices).forEach { index ->
		val cmd = cmds[index]
		if (cmd is CMD.MEM) {
			val mask = findPrevMask(index, cmds)
			val base = cmd.address or mask.unSetMask
			val xs = mutableListOf<Int>()
			mask.mask.forEachIndexed { index, c -> if (c == 'X') xs.add(index) }
			enumerate(xs, base)
				.forEach { addrs[it] = cmd.value }


			//sum += enumarate.count { !addrs.contains(it) } * cmd.value
			//addrs.addAll(enumarate)
		}
	}
	return addrs.values.sum()

}

/*150167032312
1564436736336
3279040504984
3289441921203
3279040504984
*/
fun findPrevMask(index: Int, cmd: List<CMD>): CMD.MASK {
	for (i in index downTo 0) {
		if (cmd[i] is CMD.MASK) return cmd[i] as CMD.MASK
	}

	TODO()
}

fun twoToThe(b: Int): Long = (1..b.toLong()).reduce { acc, _ -> acc * 2 }

fun enumerate(xs: List<Int>, base: Long): Set<Long> {
	val set = mutableSetOf<Long>()
	val bits = BinaryCounter(xs.size).enumerate()
	bits.forEach {
		var num = base
		it.forEachIndexed { index, i ->
			if (i == 0) num = num.unsetBitAt(xs[index])
			else num = num.setBitAt(xs[index])
		}
		set.add(num)
	}
	return set
}

fun Long.setBitAt(n: Int) = this or (1L shl n)
fun Long.unsetBitAt(n: Int) = this and (1L shl n).inv()


sealed class CMD {
	data class MEM(val address: Long, val value: Long) : CMD()
	data class MASK(val mask: String) : CMD() {
		val chars = mask.toCharArray()
		private val setMask =
			java.lang.Long.parseLong(String(chars.map { if (it == 'X') '0' else it }.toCharArray()), 2)
		val unSetMask = java.lang.Long.parseLong(String(chars.map {
			when (it) {
				'X' -> '1'
				'0' -> '0'
				'1' -> '1'
				else -> TODO()
			}
		}.toCharArray()), 2)

		fun maskPart1(num: Long) = (num or setMask) and unSetMask

		val xCount = chars.count { it == 'X' }


	}


}

fun part1(cmd: List<CMD>): Long {
	val addressLocations = mutableMapOf<Long, Long>()
	var mask = CMD.MASK("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
	cmd.forEach {
		when (it) {
			is CMD.MEM -> {
				addressLocations[it.address] = mask.maskPart1(it.value)
			}
			is CMD.MASK -> mask = it
		}
	}

	return addressLocations.values.sum()

}

val MASK_REGEX = ".*? = (.*)".toRegex()
val MEM_REGEX = """mem\[(\d*)] = (\d*)""".toRegex()
fun parse(lines: List<String>): List<CMD> {
	return lines.map {
		when {
			it.startsWith("mem") -> {
				val (addr, value) = MEM_REGEX.find(it)!!.destructured
				CMD.MEM(addr.toLong(), value.toLong())
			}
			it.startsWith("mask =") -> {
				CMD.MASK(MASK_REGEX.find(it)!!.groups[1]!!.value)
			}
			else -> {
				TODO()
			}
		}
	}
}
