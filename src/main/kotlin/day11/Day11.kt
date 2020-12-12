package day11

import java.io.File

fun main() {
	val grid = File("src/main/kotlin/day11/day11.input")
		.readLines().map {
			it.toCharArray().toList()
		}

	println(part1(grid))
	println(part2(grid))

}

private fun part2(grid: List<List<Char>>): Int {
	var grid1 = grid
	var nextGrid: List<List<Char>> = step2(grid1)
	while (grid1 != nextGrid) {
		grid1 = nextGrid
		nextGrid = step2(grid1)
	}

	return nextGrid
		.fold(0) { acc, list ->
			acc + list.count { it == '#' }
		}
}

fun step2(grid: List<List<Char>>): List<List<Char>> {
	val nextGrid = mutableListOf<List<Char>>()
	val height = grid.size - 1
	val width = grid[0].size - 1
	(0..height).forEach { i ->
		val arr = ArrayList<Char>(width + 1)
		(0..width).forEach { j ->
			val spot = grid[i][j]
			val adjacents = grid.adjacent2(j, i)
			arr.add(
				if (spot == 'L' && adjacents.none { it == '#' }) '#'
				else if (spot == '#' && adjacents.count { it == '#' } > 4) 'L'
				else spot
			)
		}
		nextGrid.add(arr)
	}
	return nextGrid
}

fun List<List<Char>>.adjacent2(x: Int, y: Int): List<Char> {
	val height = size - 1
	val width = this[0].size - 1

	val E = if (x == width) null else {
		this[y].slice(x + 1..width)
			.filterNot { it == '.' }
			.firstOrNull()
	}
	val W = if (x == 0) null else {
		this[y].slice(0..x - 1)
			.filterNot { it == '.' }
			.lastOrNull()
	}
	val N = if (y == 0) null else {
		(0 until y)
			.map { this[it][x] }
			.filterNot { it == '.' }
			.lastOrNull()
	}

	val S = if (y == height) null else {
		(y + 1..height)
			.map { this[it][x] }
			.filterNot { it == '.' }
			.firstOrNull()
	}

	var NW: Char? = null
	var ptrx = x - 1;
	var ptry = y - 1
	while (ptrx != -1 && ptry != -1) {
		if (this[ptry][ptrx] != '.') {
			NW = this[ptry][ptrx]
			break
		}
		ptrx--
		ptry--
	}

	var NE: Char? = null
	ptrx = x + 1;
	ptry = y - 1
	while (ptrx != width + 1 && ptry != -1) {
		if (this[ptry][ptrx] != '.') {
			NE = this[ptry][ptrx]
			break
		}
		ptrx++
		ptry--
	}

	var SW: Char? = null
	ptrx = x - 1;
	ptry = y + 1
	while (ptrx != -1 && ptry != height + 1) {
		if (this[ptry][ptrx] != '.') {
			SW = this[ptry][ptrx]
			break
		}
		ptrx--
		ptry++
	}

	var SE: Char? = null
	ptrx = x + 1;
	ptry = y + 1
	while (ptrx != width + 1 && ptry != height + 1) {
		if (this[ptry][ptrx] != '.') {
			SE = this[ptry][ptrx]
			break
		}
		ptrx++
		ptry++
	}

	return listOfNotNull(N, E, S, W, NE, SE, SW, NW)
}

private fun part1(grid: List<List<Char>>): Int {
	var grid1 = grid
	var nextGrid: List<List<Char>> = step1(grid1)
	while (grid1 != nextGrid) {
		grid1 = nextGrid
		nextGrid = step1(grid1)
	}

	return nextGrid
		.fold(0) { acc, list ->
			acc + list.count { it == '#' }
		}
}

fun step1(grid: List<List<Char>>): List<List<Char>> {
	val nextGrid = mutableListOf<List<Char>>()
	val height = grid.size - 1
	val width = grid[0].size - 1
	(0..height).forEach { i ->
		val arr = ArrayList<Char>(width + 1)
		(0..width).forEach { j ->
			val spot = grid[i][j]
			val adjacents = grid.adjacent1(j, i)
			arr.add(
				if (spot == 'L' && adjacents.none { it == '#' }) '#'
				else if (spot == '#' && adjacents.count { it == '#' } > 3) 'L'
				else spot
			)
		}
		nextGrid.add(arr)
	}
	return nextGrid
}

fun List<List<Char>>.adjacent1(x: Int, y: Int) =
	listOf(
		Pair(x - 1, y),
		Pair(x + 1, y),
		Pair(x, y - 1),
		Pair(x, y + 1),
		Pair(x + 1, y + 1),
		Pair(x - 1, y + 1),
		Pair(x + 1, y - 1),
		Pair(x - 1, y - 1),
	).filterNot {
		it.first == -1
				|| it.second == -1
				|| it.first == this[0].size
				|| it.second == size
	}.map {
		this[it.second][it.first]
	}