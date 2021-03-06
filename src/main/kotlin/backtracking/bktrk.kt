package backtracking

import javax.swing.text.html.HTML.Attribute.*


fun main() {

	/*println(fibonacci(80))
	println(factorial(9))
	BinaryCounter(4).enumerate()*/
	Permute(4)
}

class Permute(private val n: Int) {
	val a = IntArray(n) { it }

	init {
		enumerate(0)
	}

	private fun enumerate(k: Int) {
		if (k == n) {
			process()
			return
		}

		(k until n).forEach{
			exch(k, it)
			enumerate(k + 1)
			exch(it, k)
		}
	}

	private fun process() {
		println(a.contentToString())
	}

	private fun exch(i: Int, j: Int) {
		val t = a[i]
		a[i] = a[j]
		a[j] = t
	}


}

class BinaryCounter(private val n: Int) {
	private val a = IntArray(n)
	private val list = mutableListOf<IntArray>()

	fun enumerate(): List<IntArray> {
		enumerate(0)
		return list
	}

	private fun enumerate(k: Int) {
		if (k == n) {
			process()
			return
		}

		enumerate(k + 1)
		a[k] = 1
		enumerate(k + 1)
		a[k] = 0
	}

	private fun process() {
		list.add(a.clone())
	}
}

// 0, 1, 1, 2, 3, 5
fun fibonacci(n: Int): Long {
	var oneBack = 1L
	var twoBack = 0L
	var p = 0L
	(2..n).forEach {
		p = oneBack + twoBack
		twoBack = oneBack
		oneBack = p

	}
	return p
}

fun factorial(n: Int): Long {
	if (n == 0) return 1

	return n * factorial(n - 1)

}