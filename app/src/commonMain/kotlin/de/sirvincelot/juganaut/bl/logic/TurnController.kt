package de.sirvincelot.juganaut.bl.logic

import de.sirvincelot.juganaut.bl.Game

/**
 * Steuer die Spiel-Runden.
 */
class TurnController(val game: Game) {

    val playerController = PlayerController(game)
    val rockController = RockController(game)
    val monsterController = MonsterController(game)
    val bombController = BombController(game)

    /**
     * Wird einmal pro Runde aufgerufen, wenn die nächste Runde ausgeführt werden soll.
     */
    fun tick() {
        if (game.isRunning) {
            playerController.applyPlayerInput()
            rockController.rocksFall()
            monsterController.monstersMove()
            bombController.bombsFall()
            bombController.bombsTick()
        }
        playerController.applyPlayerActions()
    }

}