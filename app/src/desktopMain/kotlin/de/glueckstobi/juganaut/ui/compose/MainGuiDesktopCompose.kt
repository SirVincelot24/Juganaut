package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.KeyInputHandler
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle


object MainGuiDesktopCompose {
    fun startPlaying(configuration: WorldBuilderConfiguration) {
        val game = WorldBuilder().createGame(configuration)
        val keyInputHandler = KeyInputHandler(game)
        val renderCycle = RenderCycle(game)

        val tickCount = mutableIntStateOf(1)

        renderCycle.startRenderCycle() {
            tickCount.value = tickCount.value + 1
        }

        application {
            Window(
                title = "Juganaut",
                onCloseRequest = {
                    renderCycle.stopRenderCycle()
                    exitApplication()
                },
                onKeyEvent = keyInputHandler::onKeyEvent
            ) {
                MainGuiCommon(game, tickCount, null)
            }
        }
    }

}