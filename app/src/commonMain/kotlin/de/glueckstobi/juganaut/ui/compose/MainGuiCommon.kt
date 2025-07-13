package de.glueckstobi.juganaut.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.getPlatform
import de.glueckstobi.juganaut.ui.compose.game.GameScreen
import de.glueckstobi.juganaut.ui.compose.game.TouchInputHandler
import de.glueckstobi.juganaut.ui.compose.settings.SettingsScreen

enum class CurrentScreen {
    Init,
    Game,
    Settings
}

@Composable
fun MainGuiCommon(game: Game, tickCount: MutableIntState, touchInputHandler: TouchInputHandler?) {
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

        CurrentScreen.Settings -> WithInsetPadding() {
            SettingsScreen(
                onClickBack = { currentScreen.value = CurrentScreen.Init },
            )
        }

        CurrentScreen.Game -> WithInsetPadding() {
            GameScreen(
                game, tickCount, touchInputHandler,
                onClickBack = { currentScreen.value = CurrentScreen.Init },
            )
        }
    }
}

@Composable
fun WithInsetPadding(content: @Composable () -> Unit) {
    val layoutDirection = LocalLayoutDirection.current
    val displayCutout = WindowInsets.displayCutout.asPaddingValues()
    val topPadding = displayCutout.calculateTopPadding()
    val bottomPadding = displayCutout.calculateBottomPadding()
    val startPadding = displayCutout.calculateStartPadding(layoutDirection)
    val endPadding = displayCutout.calculateEndPadding(layoutDirection)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = topPadding,
                bottom = bottomPadding,
                start = startPadding,
                end = endPadding
            ),
    ) {
        content()
    }
}
