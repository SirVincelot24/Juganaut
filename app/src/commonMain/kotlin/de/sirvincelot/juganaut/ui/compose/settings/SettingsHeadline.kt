package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsHeadline(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 50.dp, bottom = 20.dp),
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        fontSize = 20.sp,
    )
}

@Composable
fun SettingsHeadline(
    text: String,
    content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 50.dp, bottom = 20.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontSize = 20.sp,
        )
        content()
    }
}