package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.ui.audio.AudioPlayer
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder

interface Platform {
    val name: String

    val audioPlayer: AudioPlayer?

    val rendererConfigHolder: WorldRendererConfigHolder
}

expect fun getPlatform(): Platform