package minesweeper

import kotlin.random.Random

fun main() {
    println("How many mines do you want on the field?")
    val mineCount = readLine()!!.toInt()
    val size = 9
    val totalCells = size * size

    if (mineCount !in 0..totalCells) throw IllegalStateException("Invalid number of mines")

    val mines = mutableSetOf<Pair<Int, Int>>()
    while (mines.size < mineCount) {
        val row = Random.nextInt(size)
        val col = Random.nextInt(size)
        mines.add(Pair(row, col))
    }

    for (i in 0 until size) {
        val row = StringBuilder()
        for (j in 0 until size) {
            row.append(if (mines.contains(i to j)) 'X' else '.')
        }
        println(row.toString())
    }
}