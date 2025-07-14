package de.glueckstobi.juganaut.ui.compose.game

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration

/**
 * Enthält den Zustand des Spiels für die Compose UI.
 */
class GameState() {

    /**
     * Die Konfiguration, wenn ein neues Spiel gestartet wird.
     */
    val configuration = mutableStateOf(WorldBuilderConfiguration())

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
    fun startNewGame() {
        val game = WorldBuilder().createGame(configuration.value)
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