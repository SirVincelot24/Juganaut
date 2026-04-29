package de.sirvincelot.juganaut.bl

import de.sirvincelot.juganaut.bl.logic.GameOverReason
import de.sirvincelot.juganaut.bl.logic.TurnController
import de.sirvincelot.juganaut.bl.logic.WinningReason
import de.sirvincelot.juganaut.getPlatform
import de.sirvincelot.juganaut.ui.audio.SFXAudioSample

/**
 * Beschreibt das Spiel mit allen Daten und der Spiel-Logik.
 * @param world die Spielwelt mit allen Feldern.
 * @param diamondsInGame Die Anzahl der gesamten Diamanten in einem Spiel
 */
class Game(val world: World, val diamondsInGame : Int) {

    /**
     * Wenn der Spieler verloren hat, wird hier der Grund für das GameOver gespeichert
     */
    var gameOverReason: GameOverReason? = null

    /**
     * Hier wird gespeichert, ob der Spieler gewonnen hat
     */
    var winningReason: WinningReason? = null
    /**
     * Hier wird die Anzahl der gesammelten Diamanten gespeichert
     */
    var diamondCount : Int = 0
    /**
     * Enthält die Logik, um die Runden auszuführen.
     */
    val turnController = TurnController(this)

    /**
     * Wird aufgerufen, wenn der Spieler verloren hat.
     * @param reason der Grund für das GameOver
     */
    fun gameOver(reason: GameOverReason) {
        getPlatform().audioPlayer?.playSfx(SFXAudioSample.Lose)
        getPlatform().audioPlayer?.stopMusic()
        gameOverReason = reason
    }

    /**
     * Wird aufgerufen, wenn der Spieler gewonnen hat.
     * @param reason der Grund für den Gewinn
     */
    fun win(reason: WinningReason) {
        getPlatform().audioPlayer?.playSfx(SFXAudioSample.Win)
        getPlatform().audioPlayer?.stopMusic()
        winningReason = reason

    }
}