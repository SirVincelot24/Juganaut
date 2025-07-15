package de.glueckstobi.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.glueckstobi.juganaut.ui.compose.states.AudioConfigHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldBuilderConfigHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder
import kotlin.math.roundToInt


@Composable
fun WorldBuilderSettings(worldBuilderConfig: WorldBuilderConfigHolder) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsHeadline("Spielwelt")
        SizeSettings(worldBuilderConfig)
//        ItemCountSettings(worldBuilderConfig)
    }
}

@Composable
private fun SizeSettings(holder: WorldBuilderConfigHolder) {
    val size = holder.worldBuilderConfig.value.worldSize
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Breite: ${size.width}")
        val range = 5f..50f
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = size.width.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val player = holder.worldBuilderConfig.value.playerCoord
                val width = newValue.roundToInt()
                holder.worldBuilderConfig.value = holder.worldBuilderConfig.value.copy(
                    worldSize = size.copy(width = width),
                    playerCoord = player.copy(x = width / 2)
                )
            },
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Höhe: ${size.height}")
        val range = 5f..50f
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = size.height.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val player = holder.worldBuilderConfig.value.playerCoord
                val height = newValue.roundToInt()
                holder.worldBuilderConfig.value = holder.worldBuilderConfig.value.copy(
                    worldSize = size.copy(height = height),
                    playerCoord = player.copy(y = height / 2)
                )
            },
        )
    }
}
