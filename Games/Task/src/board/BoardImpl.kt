package board

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl<T>(width)

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    protected val cells: List<Cell>

    init {
        cells = IntRange(1, width).flatMap { i -> IntRange(1, width).map { Cell(i, it) } }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        val index = cells.indexOf(Cell(i, j))
        return when {
            index >= 0 -> cells[index]
            else -> null
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        val index = cells.indexOf(Cell(i, j))
        return when {
            index >= 0 -> cells[index]
            else -> throw IllegalArgumentException("Invalid values of i and j")
        }
    }

    internal fun getIndex(i: Int, j: Int): Int {
        return cells.indexOf(Cell(i, j))
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        var indices: List<Int> = mutableListOf()
        for (j in jRange) {
            val index = getIndex(i, j)
            if (index >= 0) {
                indices = indices.plus(index)
            }
        }
        return indices.map { cells[it] }.toList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        var indices: List<Int> = mutableListOf()
        for (i in iRange) {
            val index = getIndex(i, j)
            if (index >= 0) {
                indices = indices.plus(index)
            }
        }
        return indices.map { cells[it] }.toList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val neighbourForDirection: Cell = when (direction) {
            Direction.UP -> {
                Cell(this.i - 1, this.j)
            }
            Direction.DOWN -> Cell(this.i + 1, this.j)
            Direction.LEFT -> Cell(this.i, this.j - 1)
            Direction.RIGHT -> Cell(this.i, this.j + 1)
        }
        return cells.find { it == neighbourForDirection }
    }
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width),GameBoard<T> {
    private val cellValueMap = mutableMapOf<Cell, T?>()

    init {
        cells.forEach {  cell -> cellValueMap[cell] = null  }
    }

    override fun get(cell: Cell): T? {
        return cellValueMap[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellValueMap[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValueMap.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellValueMap.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellValueMap.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellValueMap.values.all(predicate)
    }
}
