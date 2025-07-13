package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.getPlatform
import de.glueckstobi.juganaut.ui.audio.AudioPlayer
import de.glueckstobi.juganaut.ui.compose.game.GameScreen
import de.glueckstobi.juganaut.ui.compose.settings.SettingsScreen

enum class CurrentScreen {
    Init,
    Game,
    Settings
}

@Composable
fun MainGuiRoot(game: Game, tickCount: MutableIntState) {
    val currentScreen = remember { mutableStateOf(CurrentScreen.Init) }
    when (currentScreen.value) {
        CurrentScreen.Init -> InitScreen(
            onClickStart = {
                currentScreen.value = CurrentScreen.Game
                getPlatform().audioPlayer.startMusic()
            },
            onClickSettings = { currentScreen.value = CurrentScreen.Settings },
            onClickQuit = { System.exit(0) }
        )

        CurrentScreen.Settings -> SettingsScreen(
            onClickBack = { currentScreen.value = CurrentScreen.Init },
        )

        CurrentScreen.Game -> GameScreen(game, tickCount)
    }
}