package de.glueckstobi.juganaut.ui.audio

import com.adonax.audiocue.AudioCue
import juganaut.app.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.net.URL

/**
 * Audio-Player.
 */
object AudioPlayerAudioCue : AudioPlayer {

    override var sfxVolume = 1f
    override var musicVolume = 0.5f

    private var musicAudioCue: AudioCue? = null

    /**
     * Startet die Spiel-Musik.
     */
    override fun startMusic() {
        val audioCue = makeStereoCue(AudioSample.MainLoop)
        audioCue.open()
        audioCue.play(musicVolume.toDouble())
        audioCue.setLooping(audioCue.obtainInstance(), -1)
        musicAudioCue = audioCue
    }

    /**
     * Spielt einen einzelnen Audio-Effekt ab.
     * @param sample der Audio-Effekt
     * @param volume die Lautstärke, zwischen 0 und 1
     */
    override fun playSfx(sample: AudioSample, volume: Float) {
        val sfxAudioCue = makeStereoCue(sample)
        sfxAudioCue.open()
        sfxAudioCue.play((volume * sfxVolume).toDouble())
    }

    /**
     * Stoppt die Spiel-Musik
     */
    override fun stopMusic() {
        musicAudioCue?.let { audioCue ->
            if (audioCue.getIsActive(audioCue.obtainInstance())) {
                try {
                    audioCue.close()
                } catch (e: IllegalStateException) {
                    System.err.println("Already closed AudioCue!")
                }
            }
        }
    }

    /**
     * Stoppt alle Töne.
     */
    override fun stopAll() {
        try {
            musicAudioCue?.close()
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