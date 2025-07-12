package de.glueckstobi.juganaut.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.GameScreen
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle
import de.glueckstobi.juganaut.ui.compose.game.UserInputHandler
import de.glueckstobi.juganaut.ui.compose.game.WorldRenderer

enum class CurrentScreen {
    Init,
    Game,
//    Settings
}

object MainGuiCompose {
    fun startPlaying(configuration: WorldBuilderConfiguration) {
        val game = WorldBuilder().createGame(configuration)
        val inputHandler = UserInputHandler(game)
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
                onKeyEvent = inputHandler::onKeyEvent
            ) {
                WindowContent(game, tickCount)
            }
        }
    }

    @Composable
    private fun WindowContent(game: Game, tickCount: MutableIntState) {
        val currentScreen = remember { mutableStateOf(CurrentScreen.Init) }
        when (currentScreen.value) {
            CurrentScreen.Init -> InitScreen(
                onClickStart = {currentScreen.value = CurrentScreen.Game},
                onClickSettings = { TODO() },
                onClickQuit = {System.exit(0)}
            )
            CurrentScreen.Game -> GameScreen(game, tickCount)
        }
    }

}