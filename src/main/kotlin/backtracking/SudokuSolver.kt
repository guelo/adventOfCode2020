package backtracking

import java.util.*

fun main() {
	SudokuSolver(
		intArrayOf(
			0,1,0,0,0,8,5,7,0,
			6,0,7,0,5,0,0,0,9,
			0,5,2,1,7,0,0,0,0,
			0,0,1,0,0,3,7,0,6,
			0,7,0,0,0,0,0,4,0,
			8,0,3,7,0,0,9,0,0,
			0,0,0,0,1,7,2,6,0,
			1,0,0,0,2,0,4,0,7,
			0,2,4,3,0,0,0,9,0
		)
	)
}

class SudokuSolver(grid: IntArray) {

	var solution: IntArray? = null
	val a = grid.clone()
	private val stack = Stack<Int>()

	init {
		enumerate(0)
		solution?.printSudoku()
	}

	fun enumerate(k: Int) {
		stack.add(k)
	//	println(stack)
		if (k == 81) {
			proccess()
			stack.pop()
			return
		}

		if (a[k] != 0) {
			enumerate(k + 1)
			stack.pop()
			return
		}
		for (i in 1..9) {
			a[k] = i
			if (!backtrack()) {
				enumerate(k + 1)
			}
		}
		a[k] = 0
		stack.pop()
	}

	fun IntArray.printSudoku() {
		println("=====================")
		a.forEachIndexed { index, i ->
			if (index % 3 == 0) print(" ")
			if (index % 9 == 0) print("\n")
			print(a[index])
		}
		println("\n=====================")
	}

	private fun backtrack(): Boolean {
		rows().forEach {
			if (it.hasDupes()) {
				//	a.printSudoku()
				return true
			}
		}
		(0..8).forEach {
			if (verticals(it).hasDupes()) {
				//	a.printSudoku()
				return true
			}
		}
		listOf(0, 3, 6, 27, 30, 33, 54, 57, 60).forEach {
			if (box(it).hasDupes()) {
				return true
			}
		}

		return false
	}

	fun rows(): List<IntArray> = listOf(
		a.sliceArray(0..8),
		a.sliceArray(9..17),
		a.sliceArray(18..26),

		a.sliceArray(27..35),
		a.sliceArray(36..44),
		a.sliceArray(45..53),

		a.sliceArray(54..62),
		a.sliceArray(63..71),
		a.sliceArray(72..80),
	)

	fun horizontals(i: Int): IntArray {
		return a.sliceArray(i / 9 * 9..i / 9 * 9 + 9)
	}

	fun verticals(i: Int): IntArray {
		val col = i % 9
		return a.sliceArray((0..8).map { row -> row * 9 + col })
	}

	fun box(i: Int): IntArray {
		fun colThird(j: Int) = (j % 9) / 3
		fun rowThird(j: Int) = (j / 9) / 3

		val iColThird = colThird(i)
		val iRowThird = rowThird(i)

		return a.sliceArray((0..80).filter { colThird(it) == iColThird && rowThird(it) == iRowThird })

	}

	fun proccess() {
		a.printSudoku()
	}
}

private fun IntArray.hasDupes() =
	this.filterNot { it == 0 }.let {
		it.toSet().size != it.size
	}
