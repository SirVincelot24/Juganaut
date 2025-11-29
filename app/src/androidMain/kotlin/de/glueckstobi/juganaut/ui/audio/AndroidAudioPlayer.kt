package de.glueckstobi.juganaut.ui.audio

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import de.glueckstobi.juganaut.MainActivity
import juganaut.app.generated.resources.Res

object AndroidAudioPlayer : AudioPlayer {
    override var sfxVolume = 1f
    override var musicVolume = 0.5f

    var musicPlayer: ExoPlayer? = null
    var sfxPlayer: ExoPlayer? = null


    override fun startMusic() {
        Log.d("Juganaut", "Started music")
        musicPlayer?.play()
    }

    override fun playSfx(
        sample: AudioSample,
        volume: Float
    ) {
        sfxPlayer?.addMediaItem(MediaItem.fromUri(Res.getUri(sample.path)))
        sfxPlayer?.volume = volume * sfxVolume
        sfxPlayer?.prepare()
        sfxPlayer?.play()
        sfxPlayer?.release()
    }

    override fun stopMusic() {
        musicPlayer?.stop()
        musicPlayer?.removeListener(MainActivity().playbackStateListener)
        musicPlayer?.release()
    }

    override fun stopAll() {
        musicPlayer?.stop()
        sfxPlayer?.stop()
        MainActivity().releasePlayer()
    }
}