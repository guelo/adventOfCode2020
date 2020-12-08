import java.io.File

    fun isValid1(fields: Map<String, String>) =
        fields.containsKey("byr") // (Birth Year)
                && fields.containsKey("iyr") // (Issue Year)
                && fields.containsKey("eyr") // (Expiration Year)
                && fields.containsKey("hgt") // (Height)
                && fields.containsKey("hcl") // (Hair Color)
                && fields.containsKey("ecl") // (Eye Color)
                && fields.containsKey("pid") // (Passport ID)

    val hgtPattern = Regex("""(\d+)(\w+)""")
    val pidPattern = Regex("""\d{9}""")
    val hclPattern = Regex("""#[0-9a-f]{6}""")

    fun isValid2(fields: Map<String, String>) =
        fields["byr"].let { it != null && it.toInt() in 1920..2002 }
                && fields["iyr"].let { it != null && it.toInt() in 2010..2020 }
                && fields["eyr"].let { it != null && it.toInt() in 2020..2030 }
                && fields["hgt"].let {
                    if (it == null) false
                    else {
                        if (hgtPattern.matches(it)) {
                            val (numString, units) = hgtPattern.find(it)!!.destructured
                            when (units) {
                                "cm" -> numString.toInt() in 150..193
                                "in" -> numString.toInt() in 59..76
                                else -> false
                            }
                        } else {
                            false
                        }
                    }
                }
                && fields["hcl"].let { it != null && hclPattern.matches(it) }
                && fields["ecl"].let { it!= null && setOf("amb", "blu" ,"brn", "gry", "grn", "hzl", "oth").contains(it) }
                && fields["pid"].let { it!= null && pidPattern.matches(it) }

    fun parse(file: File): List<Map<String, String>> {
        val all = mutableListOf<Map<String, String>>()
        val tmpLines = mutableListOf<String>()
        file.readLines().forEach {
            if (it.isEmpty()) {
                all.add(process(tmpLines))
                tmpLines.clear()
            } else tmpLines.add(it)
        }
        if (tmpLines.isNotEmpty()) all.add(process(tmpLines))
        return all
    }

    fun process(tmpLines: List<String>): Map<String, String> {
        return tmpLines
            .flatMap { it.split(" ") }
            .associate {
                val (key, value) = it.split(":")
                Pair(key, value)
            }
    }

    fun main() {
        val listOfFields = parse(File("src/main/kotlin/day4/day4.input"))

        println(
            listOfFields
                .filter { isValid1(it) }
                .count()
        )
        println(
            listOfFields
                .filter { isValid2(it) }
                .count()
        )
    }
