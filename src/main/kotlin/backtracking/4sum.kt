package backtracking


fun main() {
	println(fourSum(intArrayOf(1, 0, -1, 0, -2, 2), 0))
}

fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
//	val numsLookup = nums.mapIndexed { idx, n -> Pair(n, idx) }.associate { it }
	val twoSumLookup = mutableMapOf<Int, Pair<Int, Int>>()
	nums.forEachIndexed { index, i ->
		(index + 1 until nums.size).forEachIndexed { i2, n ->
			twoSumLookup[i + n] = Pair(index, i2)
		}
	}
	return twoSumLookup
		.entries
		.filter { twoSumLookup.containsKey(target - it.key) }
		.map {
			val pair1 = it.value
			val pair2 = twoSumLookup[target - it.key]!!
			setOf(pair1.first, pair1.second, pair2.first, pair2.second)
		}
		.toSet()
		.map { it.toList() }
		.toList()

}

val twoSCache = HashMap<Int, Int>()

fun twoSumIndices(nums: IntArray, target: Int): List<Pair<Int, Int>> {

	val ret = mutableListOf<Pair<Int, Int>>()
	for (i in nums.indices) {
		val complement = target - nums[i]
		if (twoSCache.contains(complement))
			ret.add(Pair(i, twoSCache[complement]!!))

		twoSCache[nums[i]] = i
	}

	return ret
}

fun arr(a: Int, b: Int): IntArray {
	return IntArray(2).apply { this[0] = a; this[1] = b }
}
