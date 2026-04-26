package de.glueckstobi.juganaut.ui.compose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.glueckstobi.juganaut.ui.compose.states.WorldBuilderConfigHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder

@Composable
fun SettingsScreen(
    worldRendererConfigHolder: WorldRendererConfigHolder,
    worldBuilderConfigHolder: WorldBuilderConfigHolder,
    onClickBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(onClickBack)
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            WorldRendererSettings(worldRendererConfigHolder)
            WorldBuilderSettings(worldBuilderConfigHolder)
            AudioSettings()
        }
    }
}

@Composable
fun TitleBar(onClickBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .background(color = Color(0.5f, 0.9f, 0.5f))
    ) {
        BackButton(onClickBack)
        Text(
            "Settings",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun BackButton(onClickBack: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickBack)
            .background(Color.LightGray)
            .padding(10.dp)
    ) {
        Text(
            "Back",
            color = Color.Black,
            fontSize = 20.sp,
        )
    }
}
