package de.glueckstobi.juganaut.ui.compose.states

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle

/**
 * Enthält den Zustand des Spiels für die Compose UI.
 */
class GameStateHolder() {

    /**
     * Das aktuelle Spiel.
     */
    private val gameInternal = mutableStateOf<Game?>(null)

    private var renderCycle: RenderCycle? = null

    /**
     * Interner Zähler, der bei jedem Zug hochgezählt wird.
     */
    private val tickCountInternal = mutableIntStateOf(1)

    /**
     * Zugriff von der UI auf das aktuelle Spiel.
     */
    val game: Game?
        get() = gameInternal.value

    /**
     * Erzeugt ein neues Spiel.
     */
    fun startNewGame(worldBuilderConfig: WorldBuilderConfiguration) {
        val game = WorldBuilder().createGame(worldBuilderConfig)
        gameInternal.value = game

        val renderCycle = RenderCycle()
        renderCycle.startRenderCycle(::tick)
        this.renderCycle = renderCycle
    }

    fun stopGame() {
        renderCycle?.stopRenderCycle()
    }

    /**
     * Schaltet den Spielzustand einen Zug weiter.
     */
    fun tick() {
        game?.turnController?.tick()
        tickCountInternal.intValue = tickCountInternal.intValue + 1
    }

    /**
     * Zugriff auf den Zug-zöhler.
     */
    fun accessTickCount(): MutableIntState = tickCountInternal
}