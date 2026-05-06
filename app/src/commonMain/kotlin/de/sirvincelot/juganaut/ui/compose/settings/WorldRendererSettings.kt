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
import kotlin.math.roundToInt


@Composable
fun WorldRendererSettings(worldRendererConfig: WorldRendererConfigHolder) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsHeadline("Game Renderer")
        ScaleSettings(worldRendererConfig)
        ScrollSettings(worldRendererConfig)
    }
}

@Composable
private fun ScaleSettings(worldRendererConfig: WorldRendererConfigHolder) {
    Text("Skalierung", color = MaterialTheme.colorScheme.onBackground)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = worldRendererConfig.autoScale.value,
            onCheckedChange = {checked ->
                worldRendererConfig.autoScale.value = checked
            }
        )
        Text("Automatisch", color = MaterialTheme.colorScheme.onBackground)
    }


    Text("Skalierungs-Faktor:", color = MaterialTheme.colorScheme.onBackground)
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
    Text("Min Abstand zum Rand für scrollen", color = MaterialTheme.colorScheme.onBackground)
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