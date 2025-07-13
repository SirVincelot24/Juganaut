package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.GameState
import de.glueckstobi.juganaut.ui.compose.game.KeyInputHandler
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle


object MainGuiDesktopCompose {
    fun startPlaying(configuration: WorldBuilderConfiguration) {
        application {
            val gameState = remember {
                GameState().also { state ->
                    state.configuration.value = configuration
                }
            }
            val renderCycle = remember {
                RenderCycle().also {
                    it.startRenderCycle(gameState::tick)
                }
            }
            val keyInputHandler = remember() {
                KeyInputHandler(gameState.game)
            }
            keyInputHandler.game = gameState.game

            Window(
                title = "Juganaut",
                onCloseRequest = {
                    renderCycle.stopRenderCycle()
                    exitApplication()
                },
                onKeyEvent = keyInputHandler::onKeyEvent
            ) {
                MainGuiCommon(gameState, null)
            }
        }
    }

}