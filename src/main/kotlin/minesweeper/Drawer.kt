package minesweeper

object Drawer {
    fun draw(board: Board, drawMines: Boolean = false) {
        print(" |")
        for (x in 0 until board.width) print("${x + 1}")
        println("|")
        println("—│—————————│")
        for (y in 0 until board.height) {
            print("${y + 1}│")
            for (x in 0 until board.width) {
                val cell = board.getCell(x, y)
                val display = when (cell.state) {
                    CellState.CLOSED -> if (drawMines && cell.isMine) "X" else "."
                    CellState.OPENED -> if (cell.minesAround != 0) cell.minesAround.toString() else "/"
                    CellState.FLAGGED -> "*"
                }
                print(display)
            }
            println("|")
        }
        println("—│—————————│")
    }
}
