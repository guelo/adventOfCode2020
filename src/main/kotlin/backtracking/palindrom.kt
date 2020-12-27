package backtracking


fun main() {
	println(countSubstrings("aba"))

}

fun countSubstrings(s: String): Int {

TODO()}

fun pairs(s: String): Pair<Char, Char> {
	TODO("Not yet implemented")
}

fun isPalindrome(a: CharArray): Boolean {
	if (a.size==1) return true
	val isOdd = a.size % 2 == 1

	var ptrL = a.size/2-1
	var ptrR = if(isOdd) ptrL+2 else ptrL+1

	while (ptrR<a.size) {
		if (a[ptrL]!=a[ptrR]) return false

		ptrL--
		ptrR++
	}
	return true

}
