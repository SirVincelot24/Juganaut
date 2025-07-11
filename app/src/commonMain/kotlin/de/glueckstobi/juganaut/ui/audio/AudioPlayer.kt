package de.glueckstobi.juganaut.ui.audio

import com.adonax.audiocue.AudioCue

/**
 * Audio-Player.
 */
object AudioPlayer {

    private var musicAudioCue = makeStereoCue(AudioSample.MainLoop)

    /**
     * Startet die Spiel-Musik.
     */
    fun startMusic() {
        musicAudioCue.open()
        musicAudioCue.setVolume(musicAudioCue.obtainInstance(), 0.1)
        musicAudioCue.play()
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
        sfxAudioCue.play(volume)
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

    private fun makeStereoCue(sample: AudioSample): AudioCue {
        return AudioCue.makeStereoCue(this.javaClass.getResource(sample.path), 4)
    }
}