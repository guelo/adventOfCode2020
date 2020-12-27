package day16

import java.io.File

data class Rule(val name: String, val ranges: List<IntRange>) {
	fun valid(int: Int) = ranges.any { it.contains(int) }
}

fun main() {
	val sections = File("src/main/kotlin/day16/day16.input")
		.readText()
		.split("""\n\n""".toRegex(RegexOption.MULTILINE))

	val rules = sections[0].split("\\n".toRegex(RegexOption.MULTILINE))
		.map {
			val groups = """(.*): (\d+)-(\d+) or (\d+)-(\d+)"""
				.toRegex()
				.find(it)!!
				.groups
			val num11 = groups[2]!!.value.toInt()
			val num12 = groups[3]!!.value.toInt()
			val num21 = groups[4]!!.value.toInt()
			val num22 = groups[5]!!.value.toInt()
			Rule(groups[1]!!.value, listOf(num11..num12, num21..num22))
		}

	val yourTicket = sections[1]
		.split("\\R".toRegex())[1]
		.split(",")
		.map { it.toInt() }
	val nearbyTickets = sections[2]
		.split("\\R".toRegex())
		.drop(1)
		.map { it.split(",").map { it.toInt() } }

	val youv = isValid(rules, yourTicket)

	val inValidFields = nearbyTickets.flatMap { invalids(rules, it) }
		.sum()

	println(inValidFields)


	val valids = nearbyTickets.filter { isValid(rules, it) }
		.plus(listOf(yourTicket))

	val fields = Array<Rule?>(yourTicket.size) { null }
	val map = mutableMapOf<Int, MutableList<Rule>>()
	for (i in yourTicket.indices) {
		map[i] = rules.filter { rule ->
			valids
				.map { it[i] }
				.all { field -> rule.valid(field) }
		}.toMutableList()
	}

	var i = map.entries.find { it.value.size == 1 }
	while (i != null) {
		val rule = i.value[0]
		fields[i.key] = rule

		map.forEach { it.value.remove(rule) }
		i = map.entries.find { it.value.size == 1 }
	}
	val departureFields = yourTicket.filterIndexed { idx, _ -> fields[idx]!!.name.startsWith("departure") }
	println(departureFields.map { it.toLong() }.reduce { acc, i -> acc * i })

}

fun isValid(rules: List<Rule>, ticket: List<Int>): Boolean {
	val ranges = rules.flatMap { it.ranges }

	return ticket.all { field ->
		ranges.any { range -> range.contains(field) }
	}

}

fun invalids(rules: List<Rule>, ticket: List<Int>): List<Int> {
	val ranges = rules.flatMap { it.ranges }

	val invalids = mutableListOf<Int>()
	for (tik in ticket) {
		if (ranges.none { it.contains(tik) }) invalids.add(tik)
	}
	return invalids
}