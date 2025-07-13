package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.ui.audio.AudioPlayer
import de.glueckstobi.juganaut.ui.audio.AudioPlayerAudioCue

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"

    override val audioPlayer: AudioPlayer = AudioPlayerAudioCue
}

actual fun getPlatform(): Platform = JVMPlatform()