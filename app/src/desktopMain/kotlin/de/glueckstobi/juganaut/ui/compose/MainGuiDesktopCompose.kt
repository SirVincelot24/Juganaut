package de.glueckstobi.juganaut.ui.compose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.ui.audio.AudioPlayerAudioCue
import de.glueckstobi.juganaut.ui.compose.game.KeyInputHandler
import kotlin.system.exitProcess


object MainGuiDesktopCompose {

    fun startPlaying() {
        val keyInputHandler = KeyInputHandler()
        AudioPlayerAudioCue.initMusicPlayers()
        application {
            Window(
                title = "Juganaut",
                onCloseRequest = {
                    exitProcess(0)
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