package day11

import java.io.File

fun main() {
	val chars = File("src/main/kotlin/day11/day11.input")
		.readLines().map {
			it.toCharArray().toList()
		}

	var root = buildCellGrid(chars)

	root.calculateNextPart1()
	while (root.step()) {
		root.calculateNextPart1()
	}
	println(root.queryOccupied())

	// reset for part 2
	root = buildCellGrid(chars)
	root.calculateNextPart2()
	while (root.step()) {
		println("...............................")
		root.print()
		root.calculateNextPart2()
	}
	println(root.queryOccupied())
}

class Cell(var state: Char, val north: Cell?, var south: Cell?, var east: Cell?, val west: Cell?) {
	var next = state
	fun calculateNextPart1() {
		val adjacents = listOfNotNull(north, east, west, south, north?.west, north?.east, south?.west, south?.east)
			.map { it.state }

		next = if (state == 'L' && adjacents.none { it == '#' }) '#'
		else if (state == '#' && adjacents.count { it == '#' } > 3) 'L'
		else state

		east?.calculateNextPart1()
		if (west == null) {
			south?.calculateNextPart1()
		}
	}

	fun calculateNextPart2() {
		val adjacents = listOfNotNull(
			east?.findChairEast(),
			west?.findChairWest(),
			north?.findChairNorth(),
			south?.findChairSouth(),
			north?.east?.findChairNorthEast(),
			north?.west?.findChairNorthWest(),
			south?.east?.findChairSouthEast(),
			south?.west?.findChairSouthWest()
		)

		next = if (state == 'L' && adjacents.none { it == '#' }) '#'
		else if (state == '#' && adjacents.count { it == '#' } > 4) 'L'
		else state


		east?.calculateNextPart2()
		if (west == null) {
			south?.calculateNextPart2()
		}
	}

	fun findChairEast(): Char? = if (state != '.') state else east?.findChairEast()
	fun findChairNorth(): Char? = if (state != '.') state else north?.findChairNorth()
	fun findChairSouth(): Char? = if (state != '.') state else south?.findChairEast()
	fun findChairWest(): Char? = if (state != '.') state else west?.findChairEast()
	fun findChairNorthEast(): Char? = if (state != '.') state else north?.east?.findChairNorthEast()
	fun findChairNorthWest(): Char? = if (state != '.') state else north?.west?.findChairNorthWest()
	fun findChairSouthEast(): Char? = if (state != '.') state else south?.east?.findChairSouthEast()
	fun findChairSouthWest(): Char? = if (state != '.') state else south?.west?.findChairSouthWest()

	fun step(): Boolean {
		var changes = state != next
		state = next
		if (west == null) changes = changes or (south?.step() ?: false)
		changes = changes or (east?.step() ?: false)
		return changes
	}

	fun print() {
		print("$state ")
		east?.print()
		if (west == null) south?.print()
		if (east == null) println("")
	}

	fun queryOccupied(): Int {
		return (if (state == '#') 1 else 0) +
				(east?.queryOccupied() ?: 0) +
				(if (west == null) south?.queryOccupied() ?: 0 else 0)

	}
}

private fun buildCellGrid(chars: List<List<Char>>): Cell {
	val grid = mutableListOf<List<Cell>>()
	chars.forEachIndexed { y, list ->
		val cells = mutableListOf<Cell>()
		list.forEachIndexed { x, c ->
			val n = if (y == 0) null else grid[y - 1][x]
			val w = if (x == 0) null else cells[x - 1]
			val newCell = Cell(c, n, null, null, w)
			n?.south = newCell
			w?.east = newCell
			cells.add(newCell)
		}
		grid.add(cells)
	}

	return grid[0][0]
}
