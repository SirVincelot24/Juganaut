package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.GameState
import de.glueckstobi.juganaut.ui.compose.game.KeyInputHandler


object MainGuiDesktopCompose {

    private var globalKeyInputHandler: KeyInputHandler? = null

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

            // hm, doesn't know why we need this globalKeyHandler,
            // but doesn't work just with the local keyHandler
            globalKeyInputHandler = keyInputHandler

            Window(
                title = "Juganaut",
                onCloseRequest = {
                    gameState.stopGame()
                    exitApplication()
                },
                onKeyEvent = { event ->
                    globalKeyInputHandler?.onKeyEvent(event) ?: false
                }
            ) {
                MainGuiCommon(gameState, null)
            }
        }
    }

}