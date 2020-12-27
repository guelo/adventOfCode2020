package backtracking

fun main() {
	println(Permute2(intArrayOf(0, 1, 2, 3, 4, 5))
		.arrs.map { it.contentToString() + "\n" })
}

class Permute2(val array: IntArray) {
	private val N = array.size
	val a = array.clone()
	val arrs = mutableListOf<IntArray>()

	init {
		proc(0)
	}

	private fun copy() {
		arrs.add(a.clone())
	}

	fun proc(k: Int) {
		if (k == N) {
			copy()
			return
		}

		for (i in k until N) {
			exch(i, k)
			proc(k + 1)
			exch(k, i)
		}
	}

	private fun exch(i: Int, k: Int) {
		val swp = a[i]
		a[i] = a[k]
		a[k] = swp
	}
}