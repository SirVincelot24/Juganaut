package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsHeadline(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 50.dp, bottom = 20.dp),
        color = Color.Blue,
        fontSize = 20.sp,
    )
}