package de.sirvincelot.juganaut.ui.compose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import de.sirvincelot.juganaut.ui.audio.AudioPlayerAudioCue
import de.sirvincelot.juganaut.ui.compose.game.KeyInputHandler
import de.sirvincelot.juganaut.ui.theme.AppTheme
import kotlin.system.exitProcess


object MainGuiDesktopCompose {

    fun startPlaying() {
        val keyInputHandler = KeyInputHandler()
        AudioPlayerAudioCue.initMusicPlayers()
        application {
            Window(
                title = "Juganaut",
                state = rememberWindowState(WindowPlacement.Fullscreen),
                onCloseRequest = {
                    exitProcess(0)
                },
                onKeyEvent = { event ->
                    keyInputHandler.onKeyEvent(event)
                }
            ) {
                AppTheme {
                    MainGuiCommon(false, { game ->
                        keyInputHandler.game = game
                    })
                }
            }
        }
    }

}