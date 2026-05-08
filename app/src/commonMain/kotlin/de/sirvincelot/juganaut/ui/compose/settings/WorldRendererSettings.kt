package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.automatic
import juganaut.app.generated.resources.game_renderer
import juganaut.app.generated.resources.min_edge_distance
import juganaut.app.generated.resources.scale
import juganaut.app.generated.resources.scale_factor
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt


@Composable
fun WorldRendererSettings(worldRendererConfig: WorldRendererConfigHolder) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsHeadline(stringResource(Res.string.game_renderer))
        ScaleSettings(worldRendererConfig)
        ScrollSettings(worldRendererConfig)
    }
}

@Composable
private fun ScaleSettings(worldRendererConfig: WorldRendererConfigHolder) {
    Text(stringResource(Res.string.scale), color = MaterialTheme.colorScheme.onBackground)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = worldRendererConfig.autoScale.value,
            onCheckedChange = {checked ->
                worldRendererConfig.autoScale.value = checked
            }
        )
        Text(stringResource(Res.string.automatic), color = MaterialTheme.colorScheme.onBackground)
    }


    Text(stringResource(Res.string.scale_factor), color = MaterialTheme.colorScheme.onBackground)
    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = worldRendererConfig.scaleFactor.floatValue,
        valueRange = 0.3f..3f,
        onValueChange = { newValue ->
            worldRendererConfig.scaleFactor.floatValue = newValue
        },
        enabled = !worldRendererConfig.autoScale.value,
    )
}

@Composable
private fun ScrollSettings(worldRendererConfig: WorldRendererConfigHolder) {
    Text(stringResource(Res.string.min_edge_distance), color = MaterialTheme.colorScheme.onBackground)
    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = worldRendererConfig.edgeDistanceForScroll.intValue.toFloat(),
        valueRange = 0f..5f,
        steps = 4,
        onValueChange = { newValue ->
            worldRendererConfig.edgeDistanceForScroll.intValue = newValue.roundToInt()
        }
    )
}