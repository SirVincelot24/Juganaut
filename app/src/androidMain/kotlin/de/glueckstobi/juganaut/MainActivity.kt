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
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.compose.game.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val config = WorldBuilderConfiguration()
        val game = WorldBuilder().createGame(config)
        val tickCount = mutableIntStateOf(1)

        setContent {
            GameScreen(game, tickCount)
        }
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