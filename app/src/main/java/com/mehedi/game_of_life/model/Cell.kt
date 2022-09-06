package com.mehedi.game_of_life.model

class Cell(var x: Int, var y: Int, var alive: Boolean) {

    fun die() {
        alive = false
    }

    fun reborn() {
        alive = true
    }

    fun invert() {
        alive = !alive
    }
}