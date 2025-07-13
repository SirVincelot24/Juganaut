package de.glueckstobi.juganaut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.MainGuiRoot
import de.glueckstobi.juganaut.ui.compose.game.GameScreen
import de.glueckstobi.juganaut.ui.compose.game.RenderCycle
import de.glueckstobi.juganaut.ui.compose.game.UserInputHandler

class MainActivity : ComponentActivity() {

    private var inputHandler: UserInputHandler? = null
    private var renderCycle: RenderCycle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val game = createGame()
        inputHandler = UserInputHandler(game)
        renderCycle = RenderCycle(game)

        val tickCount = mutableIntStateOf(1)

        renderCycle?.startRenderCycle() {
            tickCount.value = tickCount.value + 1
        }

        setContent {
            MainGuiRoot(game, tickCount)
        }
    }

    private fun createGame(): Game {
        val config = WorldBuilderConfiguration()
        val game = WorldBuilder().createGame(config)
        return game
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val config = WorldBuilderConfiguration()
    val game = WorldBuilder().createGame(config)
    val tickCount = remember { mutableIntStateOf(1) }
    GameScreen(game, tickCount)
}