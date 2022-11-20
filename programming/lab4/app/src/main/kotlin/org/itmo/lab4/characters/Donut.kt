package org.itmo.lab4.characters

class Donut(relief: Double = 0.0): Character(relief) {
    override val face: Face = object: Face() {
        override fun showScaredEmotion() {
            println("У ${getName()} затряслись и губы, и щеки, и даже уши, а из глаз потекли слезы")
        }
    }

    override fun getName(): String {
        return "Пончик"
    }
}