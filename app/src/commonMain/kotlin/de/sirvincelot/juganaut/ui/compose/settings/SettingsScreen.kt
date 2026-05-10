package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.sirvincelot.juganaut.getPlatform
import de.sirvincelot.juganaut.ui.compose.states.WorldBuilderConfigHolder
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder
import de.sirvincelot.juganaut.ui.theme.AppTheme
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.back
import juganaut.app.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true,
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsPreview() {
    AppTheme {
    SettingsScreen(getPlatform().rendererConfigHolder, WorldBuilderConfigHolder()) {}
    }
}

@Composable
fun SettingsScreen(
    worldRendererConfigHolder: WorldRendererConfigHolder,
    worldBuilderConfigHolder: WorldBuilderConfigHolder,
    onClickBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainer)) {
        TitleBar(onClickBack)
        Column(modifier = Modifier.verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.displayCutout)
            .padding(5.dp)) {
            WorldRendererSettings(worldRendererConfigHolder)
            WorldBuilderSettings(worldBuilderConfigHolder)
            AudioSettings()
            Spacer(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp))
        }
    }
}

@Composable
fun TitleBar(onClickBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .background(/*color = Color(0.5f, 0.9f, 0.5f)*/MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(WindowInsets.displayCutout)
    ) {
        BackButton(onClickBack)
        Text(
            stringResource(Res.string.settings),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun BackButton(onClickBack: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClickBack)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp)
    ) {
        Text(
            stringResource(Res.string.back),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = 20.sp,
        )
    }
}
