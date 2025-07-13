package de.glueckstobi.juganaut.ui.compose

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalWindowInfo
import de.glueckstobi.juganaut.MainActivity
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle
import de.glueckstobi.juganaut.ui.compose.game.TouchInputHandler

object MainGuiAndroidCompose {

    private var renderCycle: RenderCycle? = null

    fun startPlaying(activity: MainActivity) {
        val game = createGame()

        renderCycle = RenderCycle(game)
        val tickCount = mutableIntStateOf(1)
        renderCycle?.startRenderCycle() {
            tickCount.value = tickCount.value + 1
        }

        activity.setContent {
            MainGuiRoot(game, tickCount)
        }

    }

    private fun createGame(): Game {
        val config = WorldBuilderConfiguration()
        val game = WorldBuilder().createGame(config)
        return game
    }
}

@Composable
fun MainGuiRoot(game: Game, tickCount: MutableIntState) {
    val inputHandler = remember { TouchInputHandler(game) }

    val screenWidth = LocalWindowInfo.current.containerSize.width
    val screenHeight = LocalWindowInfo.current.containerSize.height
    inputHandler.setDisplaySize(screenWidth, screenHeight)
    MainGuiCommon(game, tickCount, inputHandler)
}
