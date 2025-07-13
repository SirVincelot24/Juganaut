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
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.glueckstobi.juganaut.bl.Game

@Composable
fun GameScreen(game: Game, tickCount: MutableIntState, inputHandler: TouchInputHandler?, onClickBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
            .pointerInput(keys = emptyArray<Any>()) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        inputHandler?.onTouchEvent(event)
                    }
                }
            },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TitleBar(onClickBack)
            }

            WorldRenderer(game.world, tickCount)
        }
        if (inputHandler != null) {
            val screenWidth = LocalWindowInfo.current.containerSize.width
            val screenHeight = LocalWindowInfo.current.containerSize.height
            inputHandler.setDisplaySize(screenWidth, screenHeight)

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
