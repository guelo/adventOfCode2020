package day6

import java.io.File

fun main() {
	val file = File("src/main/kotlin/day6/day6.input")

	println(
		"Part 1 " +
				file.readText()
					.split("""\n\n""".toRegex(RegexOption.MULTILINE))
					.map {
						it.toCharArray()
							.filter { it in 'a'..'z' }
							.toSet()
							.size
					}
					.reduce { acc, i -> acc + i }
	)

	println(
		"Part 2 " +
				file.readText()
					.split("""\n\n""".toRegex(RegexOption.MULTILINE))
					.map {
						val charCount = IntArray(26)
						val answers = it.split("""\n""".toRegex(RegexOption.MULTILINE))
						answers.map { it.toCharArray() }
							.forEach {
								it.forEach {
									charCount[(it - 'a')] = charCount[(it - 'a')] + 1
								}
							}

						val alls = mutableListOf<Char>()
						charCount.forEachIndexed { index, i ->
							if (i == answers.size) {
								alls.add('a' + index)
							}
						}
						alls
					}
					.map { it.size }
					.reduce { acc, i -> acc + i }
	)

	Day6()
}

fun Day6() {
	val file = File("src/main/kotlin/day6/day6.input")
	val groups = file.readText()
		.split("\n\n")
		.map { group ->
			group.split('\n').map { it.toSet() }
		}
	val part1 = groups
		.map { it.reduce{ a, i-> i.union(a) }.size }
		//.map { it.reduce(Set<Char>::union).size }
		.sum()
	val part2 =
		groups
			.map { it.reduce(Set<Char>::intersect).size }
			.sum()

	println(part1)
	println(part2)
}
