package com.mehedi.game_of_life.hilt.services

import com.mehedi.game_of_life.model.Cell
import java.util.*

class World(var height: Int, var width: Int) {

    var board: Array<Array<Cell?>> = Array(width) {
        arrayOfNulls(
            height
        )
    }

    private fun init() {
        for (i in 0 until width) {
            for (j in 0 until height) {
                board[i][j] = Cell(i, j, random.nextBoolean())
            }
        }
    }

   operator fun get(i: Int, j: Int): Cell? {
        return board[j][i]
    }

    fun nbNeighboursOf(i: Int, j: Int): Int {
        var nb = 0
        for (k in i - 1..i + 1) {
            for (l in j - 1..j + 1) {
                if ((k != i || l != j)
                    && k >= 0 && k < width && l >= 0 && l < height
                ) {
                    val cell = board[k][l]
                    if (cell!!.alive) {
                        nb++
                    }
                }
            }
        }
        return nb
    }

  suspend  fun nextGeneration() {
        val liveCells: MutableList<Cell?> = ArrayList()
        val deadCells: MutableList<Cell?> = ArrayList()
        for (i in 0 until width) {
            for (j in 0 until height) {
                val cell = board[i][j]
                val nbNeighbours = nbNeighboursOf(cell!!.x, cell.y)

                // Dead Rule
                if (cell.alive && (nbNeighbours < 2 || nbNeighbours > 3)) {
                    deadCells.add(cell)
                }

                //Alive Rule
                if (cell.alive && nbNeighbours == 2 || nbNeighbours == 3
                    ||
                    !cell.alive && nbNeighbours == 3
                ) {
                    liveCells.add(cell)
                }
            }
        }
        for (cell in liveCells) {
            cell!!.reborn()
        }
        for (cell in deadCells) {
            cell!!.die()
        }
    }

    companion object {
        val random = Random()
    }

    init {
        init()
    }
}