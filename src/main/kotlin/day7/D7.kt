	package day7

	import java.io.File

	/**
	 * Doubly linked graph
	 */
	data class Bag(val color: String) {
		val containsWithCount = mutableMapOf<Bag, Int>()
		val parents = mutableListOf<Bag>()
	}

	fun main() {
		val bags = parse(File("src/main/kotlin/day7/day7.input"))

		fun traverseParents(bag: Bag?): Set<Bag> =
			bag?.let { bag.parents.plus(bag.parents.flatMap { traverseParents(it) }).toSet() } ?: emptySet()
		println("Part1 " + traverseParents(bags["shiny gold"]).size)

		fun count(bag: Bag?): Int = bag?.let { 1 + bag.containsWithCount.map { it.value * count(it.key) }.sum() } ?: 0
		println("Part2 " + (count(bags["shiny gold"]) - 1))
	}

	fun parse(file: File): MutableMap<String, Bag> {
		val bags = mutableMapOf<String, Bag>()

		file.readLines()
			.forEach {
				val (bagString, other) = it.split(" bags contain ")

				bags.getOrPut(bagString) { Bag(bagString) }
					.apply {
						containsWithCount.putAll(
							other.split(" bags, ", " bag, ")
								.map {
									it.replace(" bags.", "")
										.replace(" bag.", "")
										.replace("no other", "")
								}
								.filter(CharSequence::isNotEmpty)
								.associate {
									val (count, color) = Regex("""(\d) (.*)""").find(it)!!.destructured

									val contained = bags.getOrPut(color) { Bag(color) }
									contained.parents.add(this)

									Pair(contained, count.toInt())
								}
						)
					}
			}

		return bags
	}
