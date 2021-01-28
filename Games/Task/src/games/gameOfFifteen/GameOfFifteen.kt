package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val values = initializer.initialPermutation
        board.getAllCells().forEachIndexed { idx, cell ->
            if (idx > values.size - 1) {
                board[cell] = null
            } else {
                board[cell] = values[idx]
            }
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon():Boolean {
        val cellRealValues = board.getAllCells().map { cell -> board[cell] }.toList().filterNotNull()
        val referenceValues = (1..15).toList()
        val x= mutableListOf<Boolean>()
         cellRealValues.forEachIndexed{idx, ref -> if(referenceValues[idx] == ref){
              x.add(true)
         } }
        return x.all { it }
    }

    override fun processMove(direction: Direction) {
        board.moveValues(direction)
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
* Update the values stored in a board,
* so that the values were "moved" to the specified direction
* following the rules of the 15 game .
* Return 'true' if the values were moved and 'false' otherwise.
*/
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    val allCellValueMap = this.getAllCells().associateWith { cell -> this[cell] }
    val emptyCell = allCellValueMap.entries.first { it.value == null }.key
    val cellToBeEmptied: Cell? = when (direction) {
        Direction.RIGHT -> {
            this.getCellOrNull(emptyCell.i, emptyCell.j - 1)
        }
        Direction.LEFT -> {
            this.getCellOrNull(emptyCell.i, emptyCell.j + 1)
        }
        Direction.UP -> {
            this.getCellOrNull(emptyCell.i + 1, emptyCell.j)
        }
        Direction.DOWN -> {
            this.getCellOrNull(emptyCell.i - 1, emptyCell.j)
        }
    }
    return if (cellToBeEmptied == null) {
        false
    } else {
        moveCellValues(cellToBeEmptied)
    }
}

fun GameBoard<Int?>.moveCellValues(cellToBeEmptied: Cell): Boolean {
    val allCellValueMap = this.getAllCells().associateWith { cell -> this[cell] }
    val emptyCell = allCellValueMap.entries.first { it.value == null }.key
    val newValueToBeAssigned = allCellValueMap[cellToBeEmptied]
    this[emptyCell] = newValueToBeAssigned
    this[cellToBeEmptied] = null
    return true
}




