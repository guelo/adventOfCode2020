    package day2

    import java.io.File

    fun main() {
        val file = File("src/main/kotlin/day2/day2.input")
        println(part1(file))

        println(part2(file))
    }


    fun part1(file: File) = split(file)
        .filter { rule ->
            val count = rule.password.count { it == rule.letter }
            count >= rule.num1 && count <= rule.num2
        }
        .fold(0) { acc, _ -> acc + 1 }

    fun part2(file: File) = split(file)
        .filter { rule ->
            val firstContains = rule.password[rule.num1 - 1] == rule.letter
            val secondContains = rule.password[rule.num2 - 1] == rule.letter

            firstContains xor secondContains
        }
        .fold(0) { acc, _ -> acc + 1 }

    data class Rule(val num1: Int, val num2: Int, val letter: Char, val password: String)

    fun split(file: File) = file
        .readLines()
        .map {
            val strings = it.split(" ", ": ", "-")
            Rule(strings[0].toInt(), strings[1].toInt(), strings[2].first(), strings[3])
        }
