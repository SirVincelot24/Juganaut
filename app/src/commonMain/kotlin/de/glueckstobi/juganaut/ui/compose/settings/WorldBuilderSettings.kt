package de.glueckstobi.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.glueckstobi.juganaut.ui.compose.states.WorldBuilderConfigHolder
import kotlin.math.roundToInt


@Composable
fun WorldBuilderSettings(worldBuilderConfig: WorldBuilderConfigHolder) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsHeadline("Spielwelt")
        SizeSettings(worldBuilderConfig)
        ItemCountSettings(worldBuilderConfig)
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemCountSettings(holder: WorldBuilderConfigHolder) {
    val diamonds = holder.worldBuilderConfig.value.diamondsCountRange
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Diamanten: ${diamonds.start}-${diamonds.endInclusive}")
        val range = 1f..200f
        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = diamonds.start.toFloat()..diamonds.endInclusive.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val newRange = newValue.start.roundToInt()..newValue.endInclusive.roundToInt()
                holder.worldBuilderConfig.value = holder.worldBuilderConfig.value.copy(
                    diamondsCountRange = newRange,
                )
            },
        )
    }

    val monsters = holder.worldBuilderConfig.value.monsterCountRange
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Monster: ${monsters.start}-${monsters.endInclusive}")
        val range = 0f..200f
        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = monsters.start.toFloat()..monsters.endInclusive.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val newRange = newValue.start.roundToInt()..newValue.endInclusive.roundToInt()
                holder.worldBuilderConfig.value = holder.worldBuilderConfig.value.copy(
                    monsterCountRange = newRange,
                )
            },
        )
    }

    val bombs = holder.worldBuilderConfig.value.bombsCountRange
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Bomben: ${bombs.start}-${bombs.endInclusive}")
        val range = 0f..200f
        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = bombs.start.toFloat()..bombs.endInclusive.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val newRange = newValue.start.roundToInt()..newValue.endInclusive.roundToInt()
                holder.worldBuilderConfig.value = holder.worldBuilderConfig.value.copy(
                    bombsCountRange = newRange,
                )
            },
        )
    }
    val rocks = holder.worldBuilderConfig.value.rockCountRange
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Steine: ${rocks.start}-${rocks.endInclusive}")
        val range = 0f..200f
        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = rocks.start.toFloat()..rocks.endInclusive.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val newRange = newValue.start.roundToInt()..newValue.endInclusive.roundToInt()
                holder.worldBuilderConfig.value = holder.worldBuilderConfig.value.copy(
                    rockCountRange = newRange,
                )
            },
        )
    }
}