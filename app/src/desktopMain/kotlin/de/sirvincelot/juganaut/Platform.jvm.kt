package de.sirvincelot.juganaut

import de.sirvincelot.juganaut.ui.audio.AudioPlayer
import de.sirvincelot.juganaut.ui.audio.AudioPlayerAudioCue
import de.sirvincelot.juganaut.ui.compose.DesktopWorldRendererConfigHolder
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"

    override val audioPlayer: AudioPlayer = AudioPlayerAudioCue

    override val rendererConfigHolder: WorldRendererConfigHolder = DesktopWorldRendererConfigHolder
}

actual fun getPlatform(): Platform = JVMPlatform()