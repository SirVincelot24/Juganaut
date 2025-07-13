package de.glueckstobi.juganaut.ui.compose.game

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.logic.PlayerInput
import de.glueckstobi.juganaut.bl.logic.PlayerMovement
import de.glueckstobi.juganaut.bl.space.Direction

/**
 * Verwaltet die Benutzer-Eingaben und leitet sie an die Spiel-Logik weiter.
 */
class KeyInputHandler(var game: Game) {

    /**
     * Wird aufgerufen, wenn eine Taste gedrückt oder losgelassen wird.
     * @param e Enthält Informationen über die Taste
     */
    fun onKeyEvent(e: KeyEvent): Boolean {
        when (e.type) {
            KeyEventType.KeyDown -> onKeyPress(e)
            KeyEventType.KeyUp -> onKeyRelease(e)
        }
        return true
    }

    /**
     * Wird aufgerufen, wenn eine Taste gedrückt wird.
     * @param e Enthält Informationen über die Taste
     */
    fun onKeyPress(e: KeyEvent) {
        val input = getPlayerInput(e)
        input?.let {
            game.turnController.playerController.playerInputPressed(input)
        }
    }

    /**
     * Wird aufgerufen, wenn eine Taste losgelassen wird.
     * @param e Enthält Informationen über die Taste
     */
    fun onKeyRelease(e: KeyEvent) {
        game.turnController.playerController.playerInputReleased()
    }

    /**
     * Gibt das richtige Spieler-Kommando für die Taste zurück.
     * Wenn es kein entsprechendes Spieler-Kommando gibt, wird null zurückgegeben.
     * @param e Enthält Informationen über die Taste
     */
    private fun getPlayerInput(e: KeyEvent): PlayerInput? {
        return getDirection(e)?.let { PlayerMovement(it) }
    }

    /**
     * Gibt die Richtung zurück, in die der Spieler laufen will,
     * wenn er die angegebene Taste drückt.
     * Wenn es keine Bewegungs-Taste ist, wird null zurückgegeben.
     * @param e Enthält Informationen über die Taste
     */
    private fun getDirection(e: KeyEvent): Direction? {
        return when (e.key) {
            Key.DirectionUp -> Direction.Up
            Key.DirectionDown -> Direction.Down
            Key.DirectionLeft -> Direction.Left
            Key.DirectionRight -> Direction.Right

            Key.W -> Direction.Up
            Key.A -> Direction.Left
            Key.S -> Direction.Down
            Key.D -> Direction.Right

            else -> null
        }
    }
}