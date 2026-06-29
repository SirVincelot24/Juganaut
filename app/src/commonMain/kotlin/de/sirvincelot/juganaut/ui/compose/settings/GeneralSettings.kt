package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import de.sirvincelot.juganaut.ui.compose.states.GeneralConfigHolder
import de.sirvincelot.juganaut.ui.theme.AppTheme
import de.sirvincelot.juganaut.ui.theme.ThemeMode
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.dark
import juganaut.app.generated.resources.general
import juganaut.app.generated.resources.light
import juganaut.app.generated.resources.system
import juganaut.app.generated.resources.theme
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true,
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GeneralPreview() {
    AppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.surfaceContainer)){
            GeneralSettings()
        }
    }
}

@Composable
fun GeneralSettings() {
    val generalData = remember { GeneralConfigHolder() }

    Column(
        Modifier.fillMaxWidth()
    ) {
        SettingsHeadline(stringResource(Res.string.general))
        DarkModeSetting(generalData)
    }
}

@Composable
private fun DarkModeSetting(generalData: GeneralConfigHolder) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(stringResource(Res.string.theme), color = MaterialTheme.colorScheme.onBackground)
        SingleChoiceSegmentedButton(
            generalData = generalData)
    }
}

@Composable
fun SingleChoiceSegmentedButton(modifier: Modifier = Modifier, generalData: GeneralConfigHolder) {
    val options = listOf(stringResource(Res.string.light),stringResource(Res.string.dark),stringResource(Res.string.system))

    SingleChoiceSegmentedButtonRow(modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { generalData.themeMode = ThemeMode.from(index)
                            },
                selected = index == generalData.themeMode.value,
                label = { Text(label) }
            )
        }
    }
}