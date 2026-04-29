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
private fun SettingsSlider(
    itemCountRange: IntRange,
    range: ClosedFloatingPointRange<Float>,
    title: String,
    updateHolder: (IntRange) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("$title: ${itemCountRange.start}-${itemCountRange.endInclusive}")
        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = itemCountRange.start.toFloat()..itemCountRange.endInclusive.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val newRange = newValue.start.roundToInt()..newValue.endInclusive.roundToInt()
                updateHolder(newRange)
            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemCountSettings(holder: WorldBuilderConfigHolder) {
    val diamonds = holder.worldBuilderConfig.value.diamondsCountRange
    SettingsSlider(diamonds, 1f..200f, "Diamanten") { newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(diamondsCountRange = newRange)
    }

    val monsters = holder.worldBuilderConfig.value.monsterCountRange
    SettingsSlider(monsters, 0f..200f, "Monster") { newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(monsterCountRange = newRange)
    }

    val bombs = holder.worldBuilderConfig.value.bombsCountRange
    SettingsSlider(bombs, 0f..200f, "Bomben"){ newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(bombsCountRange = newRange)
    }

    val rocks = holder.worldBuilderConfig.value.rockCountRange
    SettingsSlider(rocks, 0f..200f, "Steine") { newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(rockCountRange = newRange)
    }
}