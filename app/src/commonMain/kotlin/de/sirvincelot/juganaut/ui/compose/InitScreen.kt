package de.sirvincelot.juganaut.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.menu_background
import org.jetbrains.compose.resources.painterResource

@Composable
fun InitScreen(
    onClickStart: () -> Unit,
    onClickSettings: () -> Unit,
    onClickQuit: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Background()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TitleText()

            Spacer(modifier = Modifier.weight(1f))

            StartGameButton(onClickStart)
            SettingsButton(onClickSettings)
            QuitButton(onClickQuit)

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun TitleText() {
    Text(
        "Juganaut",
        color = Color(1f, 0f, 0.5f),
        fontSize = 60.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun StartGameButton(onClickStart: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clickable(onClick = onClickStart)
            .background(Color.White)
            .border(5.dp, Color.White)
            .border(10.dp, Color.Green)
            .padding(30.dp)
    ) {
        Text(
            "Start Game",
            color = Color(0f, 0.7f, 0f),
            fontSize = 30.sp,
        )
    }
}

@Composable
private fun QuitButton(onClickQuit: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickQuit)
            .background(Color.Red)
            .padding(10.dp)
    ) {
        Text(
            "Quit",
            color = Color.White,
            fontSize = 30.sp,
        )
    }
}

@Composable
private fun SettingsButton(onClickSettings: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = onClickSettings)
            .background(Color.Yellow)
            .padding(10.dp)
    ) {
        Text(
            "Settings",
            color = Color.Black,
            fontSize = 30.sp,
        )
    }
}

@Composable
private fun Background() {
    Image(
        painter = painterResource(Res.drawable.menu_background),
        contentDescription = "Menu Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}