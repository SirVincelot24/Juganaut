package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.ui.audio.AudioPlayer

interface Platform {
    val name: String

    val audioPlayer: AudioPlayer?
}

expect fun getPlatform(): Platform