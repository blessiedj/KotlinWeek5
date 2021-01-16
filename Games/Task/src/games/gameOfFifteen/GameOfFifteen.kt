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
        val randomIdx =  Pair((1..4).random(),(1..4).random())
        var leftOutIndex = 14
        board[Cell(randomIdx.first, randomIdx.second)] = null
        board.getAllCells().forEachIndexed{ idx, cell ->
            if(cell.i==randomIdx.first && cell.j==randomIdx.second){
                board[cell] = null
                leftOutIndex = idx
            }
            else{
                if(idx > values.size-1){
                    board[cell] = values[leftOutIndex]
                }
                else{
                board[cell] = values[idx]
                }
            }
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.getAllCells().hashCode()

    override fun processMove(direction: Direction) {

    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}


