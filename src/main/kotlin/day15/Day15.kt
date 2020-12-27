package day15

fun main() {
	val list = mutableListOf(10,16,6,0,1,17)

	for (i in list.size .. 2020) {
		var j = i-2
		while (j>-1) {
			if (list[j]==list[i-1]){
				list.add(i-1-j)
				break
			}
			j--
		}
		if (j==-1) list.add(0)
	}
	println(list)
	part2()
}


fun part2() {
	val list = mutableListOf(10,16,6,0,1,17)
//print(list)
	val map = mutableMapOf<Int, Int>()
	list.take(list.size-1).forEachIndexed { index, i -> map[i]=index }
	var last = list.last()
	for (i in list.size-1 until 30000000-1) {
		//print("$last, ")
		val next = if (!map.containsKey(last)) {
			0
		} else{
			i-map[last]!!
		}
		map[last]=i
		last=next
	}
	println(last)
}