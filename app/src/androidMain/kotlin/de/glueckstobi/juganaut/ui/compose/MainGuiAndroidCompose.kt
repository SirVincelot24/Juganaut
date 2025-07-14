package de.glueckstobi.juganaut.ui.compose

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import de.glueckstobi.juganaut.MainActivity
import de.glueckstobi.juganaut.ui.compose.game.GameState
import de.glueckstobi.juganaut.ui.compose.game.TouchInputHandler

object MainGuiAndroidCompose {

    fun startPlaying(activity: MainActivity) {
        activity.setContent {
            val gameState = remember { GameState() }

            val touchInputHandler = remember(gameState.game) {
                gameState.game?.let { game ->
                    TouchInputHandler(game)
                }
            }

            MainGuiCommon(gameState, touchInputHandler)
        }
    }
}

