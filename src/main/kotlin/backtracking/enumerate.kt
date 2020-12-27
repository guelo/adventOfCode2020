package backtracking

import java.util.*


fun main() {
	println(BaseRCounter(5, 4).enumerate().map { it.contentToString() + "\n" })
}


fun bitString(i: Int): List<List<Int>> {
	if (i == 0) return listOf(emptyList())


	val subArray = bitString(i - 1)

	return subArray.map {
		listOf(0).plus(it)
	}.plus(subArray.map {
		listOf(1).plus(it)
	})
}


class BaseRCounter(private val n: Int, private val R: Int) {
	private val a = IntArray(n)
	val list = mutableListOf<IntArray>()

	fun enumerate(): List<IntArray> {
		enumerate(0)
		return list
	}

	private fun enumerate(k: Int) {
		if (k == n) {
			process()
			return
		}

		for (i in 0..R) {
			a[k] = i
			enumerate(k + 1)
		}
	}

	private fun process() {
		list.add(a.clone())
	}
}

