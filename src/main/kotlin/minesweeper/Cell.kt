package minesweeper

enum class CellState { CLOSED, OPENED, FLAGGED}

data class Cell(
    val isMine: Boolean,
    val minesAround: Int,
    val state: CellState

)

