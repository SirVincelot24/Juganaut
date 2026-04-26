package de.glueckstobi.juganaut.ui.compose.states

import androidx.compose.runtime.mutableStateOf
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle

/**
 * Enthält den Zustand des Spiels für die Compose UI.
 */
class GameStateHolder() {

    // Don't use "data class", so each new instance is not equal the previous instance.
    internal class GameHolder(val game: Game)

    private var renderCycle: RenderCycle? = null

    /**
     * Das aktuelle Spiel.
     */
    private val gameInternal = mutableStateOf<GameHolder?>(null)

    /**
     * Zugriff von der UI auf das aktuelle Spiel.
     */
    val game: Game?
        get() = gameInternal.value?.game

    /**
     * Erzeugt ein neues Spiel.
     */
    fun startNewGame(worldBuilderConfig: WorldBuilderConfiguration) {
        val game = WorldBuilder().createGame(worldBuilderConfig)
        gameInternal.value = GameHolder(game)

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
        gameInternal.value = game?.let { GameHolder(it) }
    }
}