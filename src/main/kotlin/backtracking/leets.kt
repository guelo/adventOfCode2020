package backtracking

class Solution {

	var a = BooleanArray(0)
	var N = 0
	val list = mutableListOf<List<Int>>()

	fun subsets(nums: IntArray): List<List<Int>> {
		a = BooleanArray(nums.size)
		N = nums.size
		calc(0, nums)
		return list
	}

	fun calc(k: Int, nums: IntArray) {
		if (k == N) {
			list.add(
				nums.filterIndexed { idx, _ -> a[idx] }
			)
		}

		calc(k + 1, nums)
		a[k] = true
		calc(k + 1, nums)
		a[k] = false
	}

	fun topKFrequent(nums: IntArray, k: Int): IntArray {
		val map = mutableMapOf<Int, Int>()
		nums.forEach {
			map[it] = map.getOrPut(it) { 0 } + 1
		}
		val es = map.entries.toList().sortedByDescending { it.value }

		return es.take(k).map { it.key }.toIntArray()

	}

	fun productExceptSelf(nums: IntArray): IntArray {
		var lastProduct = 1
		val runninLeft = nums.map {
			val next = it * lastProduct
			lastProduct = next
			next
		}
		lastProduct = 1
		val runninRight = nums.reversed().map {
			val next = it * lastProduct
			lastProduct = next
			next
		}.reversed()
		return (nums.indices).map {
			val prodLeft = if (it == 0) 1 else runninLeft[it - 1]
			val prodRight = if (it == nums.size - 1) 1 else runninRight[it + 1]
			prodLeft * prodRight
		}.toIntArray()
	}
}

data class Node2(val valu: Int, var next: Node2?)

// 3->4->5->6->null
// null<-3<-4<-5<-6
fun reverseList(node: Node2?): Node2? {
	if (node?.next == null) return node

	val l = reverseList(node.next)

	node.next!!.next = node
	node.next = null

	return l
}


fun main() {
	/*println(Solution().productExceptSelf(intArrayOf(1, 2, 3, 4)).contentToString())

	println(reverseList(Node(3, Node(4, Node(5, Node(6, Node(7, null)))))))*/

	LRUCache(3).apply {
		put(1, 1)
		println(this)
		put(2, 2)
		put(3, 3)
		put(4, 4)
		get(4)
		get(3)
		get(2)
		get(1)
		put(5, 5)
		get(1)
		get(2)
		get(3)
		get(4)
		get(5)
	}
	//[null,null,null,null,null,4,3,2,-1,null,-1,2,3,-1,5]
}

class LRUCache(val capacity: Int) {
	class Node(val key: Int, var value: Int, var next: Node?) {
		override fun toString(): String {
			return "[$key, $value] ${next?.toString()}"
		}
	}

	override fun toString() = head.toString()

	var size = 0
	var head: Node? = null

	fun get(key: Int): Int {
		//	println("get($key")
		val node = find(key)
		return if (node == null) -1 else {
			moveToHead(node)
			node.value
		}
	}

	fun put(key: Int, value: Int) {
		//println("put($key, $value")
		val found = find(key)
		if (found != null) {
			found.value = value
			moveToHead(found)
		} else {
			val newHead = Node(key, value, head)
			head = newHead
			size++
			if (size > capacity) {
				var tailParent = head!!
				while (tailParent.next?.next != null) {
					tailParent = tailParent.next!!
				}
				tailParent.next = null
				size--
			}

		}
	}

	private fun find(key: Int): Node? {
		//println("find($key")
		var node = head
		while (node != null) {
			if (node.key == key) {
				return node
			}
			node = node.next
		}
		return null
	}

	private fun moveToHead(node: Node) {
		if (node == head) return
		var parent = head!!
		while (parent.next != node) parent = parent.next!!

		parent.next = node.next
		node.next = head
		head = node
	}

}

/**
 * Your LRUCache object will be instantiated and called as such:
 * var obj = LRUCache(capacity)
 * var param_1 = obj.get(key)
 * obj.put(key,value)
 */


