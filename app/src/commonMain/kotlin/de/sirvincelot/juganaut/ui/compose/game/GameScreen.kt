package de.sirvincelot.juganaut.ui.compose.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.sirvincelot.juganaut.bl.logic.AllDiamondsCollected
import de.sirvincelot.juganaut.bl.logic.Explosion
import de.sirvincelot.juganaut.bl.logic.GameOverReason
import de.sirvincelot.juganaut.bl.logic.MonsterCatchesPlayer
import de.sirvincelot.juganaut.bl.logic.PlayerWalksIntoMonster
import de.sirvincelot.juganaut.bl.logic.RockHitsPlayer
import de.sirvincelot.juganaut.bl.logic.WinningReason
import de.sirvincelot.juganaut.getPlatform
import de.sirvincelot.juganaut.ui.compose.states.GameStateHolder
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder
import de.sirvincelot.juganaut.ui.theme.AppTheme
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.all_diamonds_collected
import juganaut.app.generated.resources.death_explosion
import juganaut.app.generated.resources.death_monster_catches_player
import juganaut.app.generated.resources.death_player_walks_into_monster
import juganaut.app.generated.resources.death_rock_hits_player
import juganaut.app.generated.resources.diamonds
import juganaut.app.generated.resources.game_over
import juganaut.app.generated.resources.stop
import juganaut.app.generated.resources.won
import org.jetbrains.compose.resources.stringResource


@Preview(showBackground = true,
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsPreview() {
    AppTheme {
        GameScreen(GameStateHolder(), getPlatform().rendererConfigHolder, false){}
    }
}

@Composable
fun GameScreen(
    gameState: GameStateHolder,
    worldRendererConfig: WorldRendererConfigHolder,
    supportTouchInput: Boolean,
    onClickBack: () -> Unit
) {
    val touchInputHandler = remember(gameState.game) {
        createTouchInputHandler(supportTouchInput, gameState)
    }

    val gameOverReason = remember { derivedStateOf { gameState.game?.gameOverReason } }
    val winReason = remember { derivedStateOf { gameState.game?.winningReason } }
    val diamondsCollected = remember { derivedStateOf { gameState.game?.diamondCount ?: 0 } }
    val diamondsTotal = remember { derivedStateOf { gameState.game?.diamondsInGame ?: 0 } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .configureTouchInput(touchInputHandler)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)) {
                TitleBar(diamondsCollected.value, diamondsTotal.value, onClickBack)
            }


            WorldRenderer(gameState, worldRendererConfig)
        }
        TouchHandlerCross(touchInputHandler)
        GameEnd(gameOverReason.value, winReason.value)
    }
}

private fun createTouchInputHandler(supportTouchInput: Boolean, gameState: GameStateHolder): TouchInputHandler? =
    if (supportTouchInput) {
        gameState.game?.let { game ->
            TouchInputHandler(game)
        }
    } else {
        null
    }

private fun Modifier.configureTouchInput(touchInputHandler: TouchInputHandler?): Modifier {
    return if (touchInputHandler == null) {
        this
    } else {
        this.pointerInput(keys = emptyArray<Any>()) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    touchInputHandler.onTouchEvent(event)
                }
            }
        }
    }
}

@Composable
fun TitleBar(diamondsCollected: Int, diamondsTotal: Int, onClickBack: () -> Unit) {
    Row(
        modifier = Modifier.padding(end = 10.dp).windowInsetsPadding(WindowInsets.displayCutout),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable(onClick = onClickBack)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(5.dp)
        ) {
            Text(
                stringResource(Res.string.stop),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 20.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            stringResource(Res.string.diamonds) + ": $diamondsCollected / $diamondsTotal",
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
private fun TouchHandlerCross(touchInputHandler: TouchInputHandler?) {
    if (touchInputHandler == null) {
        return
    }

    val screenWidth = LocalWindowInfo.current.containerSize.width
    val screenHeight = LocalWindowInfo.current.containerSize.height
    touchInputHandler.setDisplaySize(screenWidth, screenHeight)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind() {
                val color = Color.LightGray
                val crossLength = 200f
                val touchCenter = Offset(screenWidth / 2f, screenHeight / 2f)
                drawLine(color, start = touchCenter, end = touchCenter - Offset(crossLength, crossLength))
                drawLine(color, start = touchCenter, end = touchCenter + Offset(crossLength, crossLength))
                drawLine(color, start = touchCenter, end = touchCenter - Offset(-crossLength, crossLength))
                drawLine(color, start = touchCenter, end = touchCenter + Offset(-crossLength, crossLength))
            }
    )
}

@Composable
private fun GameEnd(gameOver: GameOverReason?, won: WinningReason?) {
    if (gameOver != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.rotate(30f)) {
                Text(
                    stringResource(Res.string.game_over),
                    color = Color.Red,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    formatGameOverReason(gameOver),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
    if (won != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.rotate(-30f)) {
                Text(
                    stringResource(Res.string.won),
                    color = Color.Green,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    formatGameOverReason(won),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun formatGameOverReason(reason: GameOverReason): String {
    return when (reason) {
        is MonsterCatchesPlayer -> stringResource(Res.string.death_monster_catches_player)
        is PlayerWalksIntoMonster -> stringResource(Res.string.death_player_walks_into_monster)
        is RockHitsPlayer -> stringResource(Res.string.death_rock_hits_player)
        is Explosion -> stringResource(Res.string.death_explosion)
    }
}

@Composable
private fun formatGameOverReason(reason: WinningReason): String {
    return when (reason) {
        is AllDiamondsCollected -> stringResource(Res.string.all_diamonds_collected, reason.diamondsCollected)
    }
}