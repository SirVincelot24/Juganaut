package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.states.GameStateHolder
import de.glueckstobi.juganaut.ui.compose.game.KeyInputHandler


object MainGuiDesktopCompose {

    fun startPlaying() {
        val keyInputHandler = KeyInputHandler()

        application {
            Window(
                title = "Juganaut",
                onCloseRequest = {
                    System.exit(0)
                },
                onKeyEvent = { event ->
                    keyInputHandler.onKeyEvent(event)
                }
            ) {
                MainGuiCommon(false, { game ->
                    keyInputHandler.game = game
                })
            }
        }
    }

}