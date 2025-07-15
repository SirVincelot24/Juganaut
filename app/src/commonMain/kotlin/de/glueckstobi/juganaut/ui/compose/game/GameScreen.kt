package de.glueckstobi.juganaut.ui.compose.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.glueckstobi.juganaut.ui.compose.states.GameStateHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .configureTouchInput(touchInputHandler)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TitleBar(onClickBack)
            }

            gameState.game?.let { game ->
                WorldRenderer(game.world, worldRendererConfig, gameState.accessTickCount())
            }
        }
        TouchHandlerCross(touchInputHandler)
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
                    touchInputHandler?.onTouchEvent(event)
                }
            }
        }
    }
}

@Composable
fun TitleBar(onClickBack: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickBack)
            .background(Color.LightGray)
            .padding(5.dp)
    ) {
        Text(
            "Stop",
            color = Color.Black,
            fontSize = 20.sp,
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
