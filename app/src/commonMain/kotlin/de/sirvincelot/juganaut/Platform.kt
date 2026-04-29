package de.sirvincelot.juganaut

import de.sirvincelot.juganaut.ui.audio.AudioPlayer
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder

interface Platform {
    val name: String

    val audioPlayer: AudioPlayer?

    val rendererConfigHolder: WorldRendererConfigHolder
}

expect fun getPlatform(): Platform