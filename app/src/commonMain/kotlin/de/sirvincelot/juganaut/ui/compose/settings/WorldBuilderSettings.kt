package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import de.sirvincelot.juganaut.ui.compose.states.WorldBuilderConfigHolder
import de.sirvincelot.juganaut.ui.theme.AppTheme
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.bombs
import juganaut.app.generated.resources.diamonds
import juganaut.app.generated.resources.height
import juganaut.app.generated.resources.monster
import juganaut.app.generated.resources.rocks
import juganaut.app.generated.resources.width
import juganaut.app.generated.resources.world
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt


@Preview(showBackground = true,
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WorldBuilderPreview() {
    AppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.surfaceContainer)){
            WorldBuilderSettings(WorldBuilderConfigHolder())
        }
    }
}

@Composable
fun WorldBuilderSettings(worldBuilderConfig: WorldBuilderConfigHolder) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsHeadline(stringResource(Res.string.world)){
            OutlinedIconButton(onClick = {
                worldBuilderConfig.reset()
            }, shape = ButtonDefaults.shape,
                colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
            ){
                Text("R", color = Color.White)
//                Icon(vectorResource(Res.drawable.reset_settings_24px), contentDescription = stringResource(Res.string.reset))
            }
        }
        SizeSettings(worldBuilderConfig)
        ItemCountSettings(worldBuilderConfig)
    }
}

@Composable
private fun SizeSettings(holder: WorldBuilderConfigHolder) {
    val size = holder.worldBuilderConfig.value.worldSize
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(Res.string.width) + ": ${size.width}", color = MaterialTheme.colorScheme.onBackground)
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
        Text(stringResource(Res.string.height) + ": ${size.height}", color = MaterialTheme.colorScheme.onBackground)
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

@Composable
private fun SettingsSlider(
    itemCountRange: IntRange,
    range: ClosedFloatingPointRange<Float>,
    title: String,
    updateHolder: (IntRange) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("$title: ${itemCountRange.first}-${itemCountRange.last}", color = MaterialTheme.colorScheme.onBackground)
        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = itemCountRange.first.toFloat()..itemCountRange.last.toFloat(),
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            onValueChange = { newValue ->
                val newRange = newValue.start.roundToInt()..newValue.endInclusive.roundToInt()
                updateHolder(newRange)
            },
        )
    }
}

@Composable
private fun ItemCountSettings(holder: WorldBuilderConfigHolder) {
    val diamonds = holder.worldBuilderConfig.value.diamondsCountRange
    SettingsSlider(diamonds, 1f..100f, stringResource(Res.string.diamonds)) { newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(diamondsCountRange = newRange)
    }

    val monsters = holder.worldBuilderConfig.value.monsterCountRange
    SettingsSlider(monsters, 0f..100f, stringResource(Res.string.monster)) { newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(monsterCountRange = newRange)
    }

    val bombs = holder.worldBuilderConfig.value.bombsCountRange
    SettingsSlider(bombs, 0f..100f, stringResource(Res.string.bombs)){ newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(bombsCountRange = newRange)
    }

    val rocks = holder.worldBuilderConfig.value.rockCountRange
    SettingsSlider(rocks, 0f..100f, stringResource(Res.string.rocks)) { newRange ->
        holder.worldBuilderConfig.value =
            holder.worldBuilderConfig.value.copy(rockCountRange = newRange)
    }
}