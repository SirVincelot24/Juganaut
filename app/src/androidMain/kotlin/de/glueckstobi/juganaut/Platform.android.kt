package de.glueckstobi.juganaut

import android.os.Build
import de.glueckstobi.juganaut.ui.audio.AndroidAudioPlayer
import de.glueckstobi.juganaut.ui.audio.AudioPlayer
import de.glueckstobi.juganaut.ui.compose.AndroidWorldRendererConfigHolder
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val audioPlayer: AudioPlayer = AndroidAudioPlayer

    override val rendererConfigHolder: WorldRendererConfigHolder = AndroidWorldRendererConfigHolder

}

actual fun getPlatform(): Platform = AndroidPlatform()