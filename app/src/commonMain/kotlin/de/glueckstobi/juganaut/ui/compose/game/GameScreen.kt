package de.glueckstobi.juganaut.ui.compose.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.glueckstobi.juganaut.bl.Game

@Composable
fun GameScreen(game: Game, tickCount: MutableIntState) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Viel Spaß!")
        WorldRenderer(game.world, tickCount)
    }
}
