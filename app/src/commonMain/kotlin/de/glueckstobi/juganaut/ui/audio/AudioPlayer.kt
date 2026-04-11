package de.glueckstobi.juganaut.ui.audio

/**
 * Audio-Player.
 */
interface AudioPlayer {

    var sfxVolume: Float
    var musicVolume: Float

    /**
     * Startet die Spiel-Musik.
     */
    fun startMusic()

    /**
     * Spielt einen einzelnen Audio-Effekt ab.
     * @param sample der Audio-Effekt
     * @param volume die Lautstärke, zwischen 0 und 1
     */
    fun playSfx(sample: SFXAudioSample, volume: Float = 1f)

    /**
     * Stoppt die Spiel-Musik
     */
    fun stopMusic()

    /**
     * Stoppt alle Töne.
     */
    fun stopAll()
}