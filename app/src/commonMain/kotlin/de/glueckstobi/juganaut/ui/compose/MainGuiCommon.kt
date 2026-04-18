package de.glueckstobi.juganaut.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.getPlatform
import de.glueckstobi.juganaut.ui.compose.game.GameScreen
import de.glueckstobi.juganaut.ui.compose.settings.SettingsScreen
import de.glueckstobi.juganaut.ui.compose.states.GameStateHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldBuilderConfigHolder
import kotlin.system.exitProcess

enum class CurrentScreen {
    Init,
    Game,
    Settings
}

@Composable
fun MainGuiCommon(
    supportTouchInput: Boolean,
    onGameStart: (game: Game) -> Unit = {},
    onGameStop: () -> Unit = {}
) {
    val currentScreen = remember { mutableStateOf(CurrentScreen.Init) }

    val gameState = remember { GameStateHolder() }
    val worldBuilderConfig = remember { WorldBuilderConfigHolder() }
    val worldRendererConfig = remember { getPlatform().rendererConfigHolder }

    when (currentScreen.value) {

        CurrentScreen.Init -> InitScreen(
            onClickStart = {
                gameState.startNewGame(worldBuilderConfig.worldBuilderConfig.value)
                getPlatform().audioPlayer?.initMusicPlayers()
                getPlatform().audioPlayer?.startMusic()
                onGameStart(gameState.game!!)
                currentScreen.value = CurrentScreen.Game
            },
            onClickSettings = { currentScreen.value = CurrentScreen.Settings },
            onClickQuit = { exitProcess(0) }
        )

        CurrentScreen.Settings -> WithSystemBarsPadding() {
            SettingsScreen(
                worldRendererConfig,
                worldBuilderConfig,
                onClickBack = { currentScreen.value = CurrentScreen.Init },
            )
        }

        CurrentScreen.Game -> WithSystemBarsPadding() {
            GameScreen(
                gameState,
                worldRendererConfig,
                supportTouchInput,
                onClickBack = {
                    onGameStop()
                    gameState.stopGame()
                    getPlatform().audioPlayer?.stopAll()
                    currentScreen.value = CurrentScreen.Init
                }
            )
        }
    }
}

@Composable
fun WithSystemBarsPadding(content: @Composable () -> Unit) {
    val layoutDirection = LocalLayoutDirection.current
    val systemBarPaddings = WindowInsets.systemBars.asPaddingValues()
    val topPadding = systemBarPaddings.calculateTopPadding()
    val bottomPadding = systemBarPaddings.calculateBottomPadding()
    val startPadding = systemBarPaddings.calculateStartPadding(layoutDirection)
    val endPadding = systemBarPaddings.calculateEndPadding(layoutDirection)

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
