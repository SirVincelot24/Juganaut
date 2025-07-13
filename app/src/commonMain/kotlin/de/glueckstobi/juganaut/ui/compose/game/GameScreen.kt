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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.glueckstobi.juganaut.bl.Game

@Composable
fun GameScreen(game: Game, tickCount: MutableIntState, inputHandler: TouchInputHandler?, onClickBack: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .pointerInput(keys = emptyArray<Any>()) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        inputHandler?.onTouchEvent(event)
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()){
                TitleBar(onClickBack)
            }

            WorldRenderer(game.world, tickCount)
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
