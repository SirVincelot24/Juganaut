package de.glueckstobi.juganaut.ui.audio

/**
 * Ein No-Operation AudioPlayer, der einfach nichts macht.
 */
class AudioPlayerNoop: AudioPlayer {

    override var sfxVolume: Float = 0f

    override var musicVolume: Float = 0f

    override fun startMusic() {
        // do nothing
    }

    override fun playSfx(sample: AudioSample, volume: Float) {
        // do nothing
    }

    override fun stopMusic() {
        // do nothing
    }

    override fun stopAll() {
        // do nothing
    }
}