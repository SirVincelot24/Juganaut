package de.sirvincelot.juganaut.ui.compose.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import de.sirvincelot.juganaut.bl.Game
import de.sirvincelot.juganaut.bl.logic.PlayerInput
import de.sirvincelot.juganaut.bl.logic.PlayerMovement
import de.sirvincelot.juganaut.bl.space.Direction
import kotlin.math.abs

/**
 * Verwaltet die Benutzer-Eingaben und leitet sie an die Spiel-Logik weiter.
 */
class TouchInputHandler(val game: Game) {

    var screenWidth: Int = 1
    var screenHeight: Int = 1

    /**
     * Wird aufgerufen, wenn ein Finger auf dem Display verändert wird.
     * @param e Enthält Informationen über die Touch-Aktion
     */
    fun onTouchEvent(e: PointerEvent): Boolean {
        when (e.type) {
            PointerEventType.Press -> onTouchPress(e)
            PointerEventType.Move -> onTouchMove(e)
            PointerEventType.Release -> onTouchRelease(e)
        }
        return true
    }

    /**
     * Wird aufgerufen, wenn der Finger auf das Display aufgelegt wird.
     * @param e Enthält Informationen über die Touch-Aktion
     */
    fun onTouchPress(e: PointerEvent) {
        val input = getPlayerInput(e)
        input?.let {
            game.turnController.playerController.playerInputPressed(input)
        }
    }

    /**
     * Wird aufgerufen, wenn der Finger auf Display bewegt wird.
     * @param e Enthält Informationen über die Touch-Aktion
     */
    fun onTouchMove(e: PointerEvent) {
        val input = getPlayerInput(e)
        input?.let {
            game.turnController.playerController.playerInputChanged(input)
        }
    }

    /**
     * Wird aufgerufen, wenn der Finger vom das Display abgehoben wird.
     * @param e Enthält Informationen über die Touch-Aktion
     */
    fun onTouchRelease(e: PointerEvent) {
        game.turnController.playerController.playerInputReleased()
    }

    /**
     * Gibt das richtige Spieler-Kommando für die Taste zurück.
     * Wenn es kein entsprechendes Spieler-Kommando gibt, wird null zurückgegeben.
     * @param e Enthält Informationen über die Taste
     */
    private fun getPlayerInput(e: PointerEvent): PlayerInput? {
        val position = e.changes.firstOrNull()?.position
        if (position == null){
            return null
        }
        return getDirection(position)?.let { PlayerMovement(it) }
    }

    /**
     * Gibt die Richtung zurück, in die der Spieler laufen will,
     * wenn er den Finger an der angegebenen Position auf das Display legt.
     * @param position Die Position des Fingers auf dem Display.
     */
    private fun getDirection(position: Offset): Direction? {
        val centerX = screenWidth / 2
        val centerY = screenHeight / 2
        val distVertical = position.y - centerY
        val distanceHoriz = position.x - centerX
        val vertical = abs(distVertical) > abs(distanceHoriz)
        return if (vertical) {
            if (distVertical < 0) {
                Direction.Up
            }else {
                Direction.Down
            }
        }else {
            if (distanceHoriz < 0) {
                Direction.Left
            }else {
                Direction.Right
            }

        }
    }

    fun setDisplaySize(screenWidth: Int, screenHeight: Int) {
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
    }
}