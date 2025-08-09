package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.ui.audio.AudioPlayer
import de.glueckstobi.juganaut.ui.audio.AudioPlayerAudioCue
import de.glueckstobi.juganaut.ui.compose.DesktopWorldRendererConfigHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"

    override val audioPlayer: AudioPlayer? = AudioPlayerAudioCue

    override val rendererConfigHolder: WorldRendererConfigHolder = DesktopWorldRendererConfigHolder
}

actual fun getPlatform(): Platform = JVMPlatform()