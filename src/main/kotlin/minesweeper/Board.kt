package minesweeper

import kotlin.random.Random

class Board(
    val width: Int,
    val height: Int,
    private val mineCount: Int
) {
    private var cells: Array<Array<Cell>> = Array(height) { Array(width) { Cell(false, 0, CellState.CLOSED) } }
    private val mines = mutableSetOf<Pair<Int, Int>>()
    private var isBoom = false
    private var minesGenerated = false

    init {
        require (mineCount < width * height) { "Too many mines"}
    }

    private fun generateMinesAvoiding(x: Int, y: Int) {

        while (mines.size < mineCount) {
            val row = Random.nextInt(height)
            val col = Random.nextInt(width )
            if (row == y && col == x) continue
            if (mines.add(Pair(row, col))) cells[row][col] = cells[row][col].copy(isMine = true)
        }
    }

    private fun calculateMinesAround() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (!cells[y][x].isMine) {
                    val count = countMines(x, y)
                    cells[y][x] = cells[y][x].copy(minesAround = count)
                }
            }
        }
    }

    private fun countMines(x: Int, y: Int): Int {
        var count = 0
        for (j in y - 1..y + 1)
            for (i in x - 1..x + 1)
                if (mines.contains(Pair(j, i))) count++
        return count
    }

    fun open(x: Int, y: Int) {
        require( x in 0 until width || y in 0 until height) {
            "out of bounds"
        }

        if (!minesGenerated){
            generateMinesAvoiding(x, y)
            calculateMinesAround()
            minesGenerated = true
        }
        val cell = cells[y][x]
        if (cell.state != CellState.CLOSED) return

        if (cell.isMine) {
            isBoom = true
            return
        }

        cells[y][x] = cell.copy(state = CellState.OPENED)

        if (cell.minesAround == 0) {
            for (j in y - 1..y + 1) {
                for (i in x - 1..x + 1) {
                    open(i, j)
                }
            }
        }
    }

    fun toggleFlag(x: Int, y: Int) {
        require( x in 0 until width || y in 0 until height) {
            "out of bounds"
        }
        val cell = cells[y][x]
        if (cell.state == CellState.CLOSED) {
            cells[y][x] = cell.copy(state = CellState.FLAGGED)
        } else if (cell.state == CellState.FLAGGED) {
            cells[y][x] = cell.copy(state = CellState.CLOSED)
        }
    }

    fun getCell(x: Int, y: Int): Cell = cells[y][x]

    fun isGameOver(): Boolean = isBoom

    fun isWin(): Boolean {
        return cells.flatten().all { cell ->
            cell.isMine || cell.state == CellState.OPENED
        }
    }
}