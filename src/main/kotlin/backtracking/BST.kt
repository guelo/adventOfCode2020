package backtracking

class BST {
	private val root: Node? = null

	private fun size() = size(root)
	private fun size(node: Node?) = node?.N

	fun get(key: Int) = this.get(key, root)?.value

	private fun get(key: Int, node: Node?): Node? {
		if (node == null) return null

		return when {
			key < node.value -> get(key, node.left)
			key < node.value -> get(key, node.right)
			else -> node
		}
	}

	fun put(key: Int, value: Int) {
		put(key, value, root)
	}

	private fun put(key: Int, value: Int, node: Node?): Node {
		if (node == null) return Node(key, value, 1, null, null)

		if (key < node.value) node.left = put(key, value, node.left)
		if (key > node.value) node.right = put(key, value, node.right)

		node.N = node.left?.N ?: 0 + (node.right?.N ?: 0)
		return node
	}

	private fun put(key: Node, node: Node?): Node {
		if (node == null) return key

		if (key.value < node.value) node.left = put(key, node.left)
		if (key.value > node.value) node.right = put(key, node.right)

		node.N = node.left?.N ?: 0 + (node.right?.N ?: 0)

		return node
	}

	fun delete(key: Int) {
		val parent = getParent(key, root)
		if (parent != null) {
			val toDelete: Node
			if (parent.right?.value == key) {
				toDelete = parent.right!!
				parent.right = null
			} else {
				toDelete = parent.left!!
				parent.left = null
			}
			if (toDelete.left != null) put(toDelete.left!!, root)
			if (toDelete.right != null) put(toDelete.left!!, root)
		}
	}

	fun getParent(key: Int, node: Node?): Node? {
		if (node == null) return null

		return if (node.left?.value == key || node.right?.value == key) node
		else if (key < node.value) getParent(key, node.left)
		else if (key > node.value) getParent(key, node.right)
		else null
	}

}

class Node(val key: Int, val value: Int, var N: Int, var left: Node?, var right: Node?)


