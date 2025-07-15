package de.glueckstobi.juganaut

import android.os.Build
import de.glueckstobi.juganaut.ui.audio.AudioPlayer

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val audioPlayer: AudioPlayer? = null

}

actual fun getPlatform(): Platform = AndroidPlatform()