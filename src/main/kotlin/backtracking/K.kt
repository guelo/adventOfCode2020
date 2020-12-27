package backtracking

fun main() {
	println(countS2ubstrings("aabacdc"))
}

fun countS2ubstrings(s: String): Int {
	var count = 0
	s.forEachIndexed { index, c ->
		val pair = if (index + 1 < s.length && s[index + 1] == s[index]) true else false
		count += expandFromSingle(index, s)
		if (pair) count += expandFromPair(index, s)
	}
	return count
}

fun expandFromSingle(index: Int, s: String): Int {
	var count = 0
	var ptrL = index
	var ptrR = index
	while (ptrL >= 0 && ptrR < s.length && s[ptrL] == s[ptrR]) {
		println(s.slice(ptrL..ptrR))
		count++
		ptrL--
		ptrR++
	}
	return count
}


fun expandFromPair(index: Int, s: String): Int {
	var count = 0
	var ptrL = index
	var ptrR = index + 1
	while (ptrL >= 0 && ptrR < s.length && s[ptrL] == s[ptrR]) {
		println(s.slice(ptrL..ptrR))
		count++
		ptrL--
		ptrR++
	}
	return count
}

