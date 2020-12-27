package backtracking

fun reconstructQueue(people: Array<IntArray>): Array<IntArray> {
	val newList: Array<IntArray> = Array(people.size) {
		IntArray(2).apply {
			this[0] = -1
		}
	}

	fun IntArray.score() = this[0] + this[1].toFloat() / people.size
	val placedIdx = mutableSetOf<Int>()
	fun findNextMin(): Int {
		var min: Int? = null
		people.forEachIndexed { i, pair ->
			if (!placedIdx.contains(i)) {
				if (min == null) min = i
				if (pair.score() < people[min!!].score()) {
					min = i
				}
			}
		}
		return min!!
	}

	fun place(min: Int) {
		val toPlace = people[min]
		var i = 0
		var count = 0
		while (i < people.size) {
			if (toPlace[1] <= count && newList[i][0] == -1) {
				break
			}
			if (newList[i][0] == -1 || newList[i][0] >= toPlace[0]) {
				count++
			}
			i++
		}
		newList[i] = people[min]
		placedIdx.add(min)
	}

	while (placedIdx.size < people.size) {
		place(findNextMin())
	}

	return newList
}

fun main() {
	//[[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
//	[[2,4]
	println(
		reconstructQueue(
			arrayOf(
				intArrayOf(3, 4),
				intArrayOf(9, 0),
				intArrayOf(0, 6),
				intArrayOf(7, 1),
				intArrayOf(6, 0),
				intArrayOf(7, 3),
				intArrayOf(2, 5),
				intArrayOf(1, 1),
				intArrayOf(8, 0),
			)
		).contentDeepToString()
	)
}
//

