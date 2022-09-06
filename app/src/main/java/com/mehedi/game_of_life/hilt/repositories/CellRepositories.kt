package com.mehedi.game_of_life.hilt.repositories

import com.mehedi.game_of_life.hilt.services.World
import javax.inject.Inject

class CellRepositories  @Inject constructor ( private  val world: World) {

    suspend fun nextGeneration()= world.nextGeneration();





}