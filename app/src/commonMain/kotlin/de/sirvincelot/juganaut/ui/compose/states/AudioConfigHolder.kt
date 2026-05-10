package de.sirvincelot.juganaut.ui.compose.states

import androidx.compose.runtime.mutableFloatStateOf
import de.sirvincelot.juganaut.getPlatform

/**
 * Enthält die Daten für die Audio-Einstellungen.
 */
class AudioConfigHolder {

    /**
     * Interner State für die Musik-Lautstärke
     */
    private var musicVolumeState = mutableFloatStateOf(getPlatform().audioPlayer?.musicVolume ?: 0f)

    /**
     * Interner State für die Effekt-Lautstärke
     */
    private var sfxVolumeState = mutableFloatStateOf(getPlatform().audioPlayer?.sfxVolume ?: 0f)

    /**
     * Musik-Lautstärke.
     */
    var musicVolume: Float
        get() = musicVolumeState.floatValue
        set(value) {
            musicVolumeState.floatValue = value
            getPlatform().audioPlayer?.musicVolume = value
        }

    /**
     * Effekt-Lautstärke.
     */
    var sfxVolume
        get() = sfxVolumeState.floatValue
        set(value) {
            sfxVolumeState.floatValue = value
            getPlatform().audioPlayer?.sfxVolume = value
        }

    fun hasAudio(): Boolean = getPlatform().audioPlayer != null
}