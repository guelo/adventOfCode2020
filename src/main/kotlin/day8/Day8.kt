	package day8

	import java.io.File
	import day8.Computer.Instr.CMD.*

	class Computer() {
		data class Instr(val cmd: CMD, val num: Int) {
			enum class CMD { jmp, acc, nop }
		}

		fun compile(file: File) {
			program = file.readLines()
				.map {
					it.split(" ").let { (cmd, numS) ->
							Instr(valueOf(cmd), numS.toInt())
						}
				}.toMutableList()
		}

		var program: MutableList<Instr> = mutableListOf()
		val executed = mutableListOf<Int>()

		var ptr = 0
		var acc = 0

		enum class STATE { RUNNING, DONE, INFINITE_LOOP }

		var state: STATE = STATE.DONE

		fun run() {
			state = STATE.RUNNING
			executed.clear()
			ptr = 0
			acc = 0

			while (!executed.contains(ptr) && ptr != program.size) {
				executed.add(ptr)
				val (cmd, num) = program[ptr]
				when (cmd) {
					Instr.CMD.jmp -> ptr += num
					Instr.CMD.acc -> {
						acc += num
						ptr++
					}
					Instr.CMD.nop -> ptr++
				}
			}

			state = if (ptr == program.size) STATE.DONE else STATE.INFINITE_LOOP

		}
	}

	fun main() {
		val computer = Computer()
		computer.compile(File("src/main/kotlin/day8/day8.input"))
		computer.run()
		println("Part1 " + computer.acc)

		print("Part 2 ")
		// Change the last executed negative jmp instruction to a nop and run again
		val originalProgram = ArrayList(computer.program)
		for (i in computer.executed.size - 2 downTo 0) {
			val execd = computer.executed[i]
			if (computer.program[execd].cmd == jmp) {
				computer.program[execd] = Computer.Instr(nop, computer.program[i].num)
				computer.run()
				if (computer.state == Computer.STATE.DONE) {
					println(computer.acc)
					break
				}
				computer.program = originalProgram
			}
		}

	}