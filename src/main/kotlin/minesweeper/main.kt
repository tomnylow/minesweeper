package minesweeper

const val WIDTH = 9
const val HEIGHT = 9

fun main() {
    try {
        print("How many mines do you want on the field? ")
        val numberMines = readLine()!!.toInt()
        val board = Board(WIDTH, HEIGHT, numberMines)

        while (!board.isGameOver() && !board.isWin()) {
            Drawer.draw(board)
            print("Command (open x y / flag x y): ")
            val input = readLine()?.split(" ") ?: continue
            if (input.size != 3) continue
            val action = input[0]
            val x = input[1].toIntOrNull() ?: continue
            val y = input[2].toIntOrNull() ?: continue

            when (action) {
                "open" -> board.open(x - 1, y - 1)
                "flag" -> board.toggleFlag(x - 1, y - 1)
            }
        }
        Drawer.draw(board, true)
        if (board.isGameOver()) print("You stepped on a mine and failed!")
        if (board.isWin()) print("Congratulations! You found all the mines!")

    } catch (e: Exception) {
        println("Exception caught $e")
    }
}
