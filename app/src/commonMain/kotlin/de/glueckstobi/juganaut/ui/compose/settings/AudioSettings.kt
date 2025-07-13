package de.glueckstobi.juganaut.ui.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import de.glueckstobi.juganaut.getPlatform

/**
 * Enthält die Daten für die Audio-Einstellungen.
 */
class AudioSettingsData() {

    /**
     * Interner State für die Musik-Lautstärke
     */
    private var musicVolumeState = mutableFloatStateOf(getPlatform().audioPlayer.musicVolume.toFloat())

    /**
     * Interner State für die Effekt-Lautstärke
     */
    private var sfxVolumeState = mutableFloatStateOf(getPlatform().audioPlayer.sfxVolume.toFloat())

    /**
     * Musik-Lautstärke.
     */
    var musicVolume: Float
        get() = musicVolumeState.value
        set(value) {
            musicVolumeState.value = value
            getPlatform().audioPlayer.musicVolume = value
        }

    /**
     * Effekt-Lautstärke.
     */
    var sfxVolume
        get() = sfxVolumeState.value
        set(value) {
            sfxVolumeState.value = value
            getPlatform().audioPlayer.sfxVolume = value
        }
}

@Composable
fun AudioSettings() {
    val audioData = remember { AudioSettingsData() }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MusicVolumeSetting(audioData)
        SFXVolumeSetting(audioData)
    }
}

@Composable
private fun MusicVolumeSetting(audioData: AudioSettingsData) {
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
private fun SFXVolumeSetting(audioData: AudioSettingsData) {
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