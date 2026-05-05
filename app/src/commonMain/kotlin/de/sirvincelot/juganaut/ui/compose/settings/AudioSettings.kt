package de.sirvincelot.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import de.sirvincelot.juganaut.ui.compose.states.AudioConfigHolder


@Composable
fun AudioSettings() {
    val audioData = remember { AudioConfigHolder() }

    if (!audioData.hasAudio()){
        return
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsHeadline("Audio")
        MusicVolumeSetting(audioData)
        SFXVolumeSetting(audioData)
    }
}

@Composable
private fun MusicVolumeSetting(audioData: AudioConfigHolder) {
    Text("Musik Lautstärke")
    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = audioData.musicVolume,
        valueRange = 0f..1f,
        onValueChange = { newValue ->
            audioData.musicVolume = newValue
        },
    )
}

@Composable
private fun SFXVolumeSetting(audioData: AudioConfigHolder) {
    Text("Effekt Lautstärke")
    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = audioData.sfxVolume,
        valueRange = 0f..1f,
        onValueChange = { newValue ->
            audioData.sfxVolume = newValue
        }
    )
}