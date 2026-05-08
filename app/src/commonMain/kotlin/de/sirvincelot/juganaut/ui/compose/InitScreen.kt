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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.sirvincelot.juganaut.getPlatform
import de.sirvincelot.juganaut.ui.theme.AppTheme
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.menu_background
import juganaut.app.generated.resources.menu_background_phone
import juganaut.app.generated.resources.quit
import juganaut.app.generated.resources.settings
import juganaut.app.generated.resources.start_game
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true,
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun InitPreview() {
    AppTheme {
        InitScreen({}, {}, {})
    }
}

@Composable
fun InitScreen(
    onClickStart: () -> Unit,
    onClickSettings: () -> Unit,
    onClickQuit: () -> Unit,
) {

    getPlatform().audioPlayer?.startMenuMusic()

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
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(5.dp, MaterialTheme.colorScheme.primaryContainer)
            .border(10.dp, MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(30.dp)
    ) {
        Text(
            stringResource(Res.string.start_game),
            color = /*Color(0f, 0.7f, 0f)*/MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 30.sp,
        )
    }
}

@Composable
private fun QuitButton(onClickQuit: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickQuit)
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(10.dp)
    ) {
        Text(
            stringResource(Res.string.quit),
            color = MaterialTheme.colorScheme.onErrorContainer,
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
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp)
    ) {
        Text(
            stringResource(Res.string.settings),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = 30.sp,
        )
    }
}

@Composable
private fun Background() {
    if (getPlatform().name.contains("Android")) {
        Image(
            painter = painterResource(Res.drawable.menu_background_phone),
            contentDescription = "Menu Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    } else {
        Image(
            painter = painterResource(Res.drawable.menu_background),
            contentDescription = "Menu Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

}