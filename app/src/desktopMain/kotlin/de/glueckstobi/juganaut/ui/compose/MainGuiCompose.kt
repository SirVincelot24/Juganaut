package de.glueckstobi.juganaut.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration

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
                windowContent(game, tickCount)
            }
        }
    }

    @Composable
    private fun windowContent(game: Game, tickCount: MutableIntState) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Viel Spaß!")
            WorldRenderer(game.world, tickCount)
        }
    }
}