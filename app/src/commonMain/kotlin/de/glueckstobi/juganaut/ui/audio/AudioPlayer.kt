package de.glueckstobi.juganaut.ui.audio

import com.adonax.audiocue.AudioCue
import juganaut.app.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.net.URL

/**
 * Audio-Player.
 */
object AudioPlayer {

    var sfxVolume = 1.0
    var musicVolume = 0.5

    private var musicAudioCue = makeStereoCue(AudioSample.MainLoop)

    /**
     * Startet die Spiel-Musik.
     */
    fun startMusic() {
        musicAudioCue.open()
        musicAudioCue.play(musicVolume)
        musicAudioCue.setLooping(musicAudioCue.obtainInstance(), -1)
    }

    /**
     * Spielt einen einzelnen Audio-Effekt ab.
     * @param sample der Audio-Effekt
     * @param volume die Lautstärke, zwischen 0 und 1
     */
    fun playSfx(sample: AudioSample, volume: Double = 1.0) {
        val sfxAudioCue = makeStereoCue(sample)
        sfxAudioCue.open()
        sfxAudioCue.play(volume * sfxVolume)
    }

    /**
     * Stoppt die Spiel-Musik
     */
    fun stopMusic() {
        if (musicAudioCue.getIsActive(musicAudioCue.obtainInstance())) {
            try {
                musicAudioCue.close()
            } catch (e: IllegalStateException) {
                System.err.println("Already closed AudioCue!")
            }
        }
    }

    /**
     * Stoppt alle Töne.
     */
    fun stopAll() {
        try {
            musicAudioCue.close()
        } catch (e: IllegalStateException) {
            System.err.println("Already closed AudioCue!")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun makeStereoCue(sample: AudioSample): AudioCue {
        val url = URL(Res.getUri(sample.path))
        return AudioCue.makeStereoCue(url, 4)
    }
}