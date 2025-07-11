package de.glueckstobi.juganaut.ui.compose

import de.glueckstobi.juganaut.bl.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

class RenderCycle(val game: Game) : Runnable {

    companion object {
        /**
         * Die Zeit einer Spiel-Runde (in Millisekunden)
         */
        val tickDurationMs = 500.toLong()
    }

    /**
     * True, wenn die Spiel-Uhr nicht weiterlaufen soll (weil das Spiel beendet ist)
     */
    @Volatile
    private var clockStopped = false

    private var tickCallback: () -> Unit = {}

    /**
     * Wird regelmäßig für jede neue Spiel-Runde aufgerufen.
     */
    override fun run() {
        var previousTickMs = nanoToMilli(System.nanoTime())
        while (!clockStopped && !Thread.currentThread().isInterrupted) {
            val now = nanoToMilli(System.nanoTime())
            val nextTick = previousTickMs + tickDurationMs
            val sleepTime = nextTick - now
            Thread.sleep(sleepTime)
            previousTickMs = nextTick
            tickRenderCycleAsync()
        }
    }

    /**
     * Umrechnung von Nanosekunden in Millisekunden.
     */
    private fun nanoToMilli(nano: Long): Long = nano / 1_000_000

    fun startRenderCycle(tickCallback: () -> Unit) {
        this.tickCallback = tickCallback
        Thread(this, "TickThread").start()
    }

    fun stopRenderCycle() {
        clockStopped = true
    }

    private fun tickRenderCycleAsync() {
        Dispatchers.Main.immediate.asExecutor().execute {
            tickRenderCycle()
        }
    }

    /**
     * Wird für jede Spiel-Runde aufgerufen.
     * Führt die Spiel-Logik aus und zeichnet danach das Ergebnis neu.
     */
    private fun tickRenderCycle() {
        game.turnController.tick()
        tickCallback()
    }
}