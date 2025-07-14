package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.GameState
import de.glueckstobi.juganaut.ui.compose.game.KeyInputHandler


object MainGuiDesktopCompose {
    fun startPlaying(configuration: WorldBuilderConfiguration) {
        application {
            val gameState = remember {
                GameState().also { state ->
                    state.configuration.value = configuration
                }
            }
            val keyInputHandler = remember(gameState.game) {
                gameState.game?.let { game ->
                    KeyInputHandler(game)
                }
            }

            Window(
                title = "Juganaut",
                onCloseRequest = {
                    gameState.stopGame()
                    exitApplication()
                },
                onKeyEvent = { event ->
                    keyInputHandler?.onKeyEvent(event) ?: false
                }
            ) {
                MainGuiCommon(gameState, null)
            }
        }
    }

}