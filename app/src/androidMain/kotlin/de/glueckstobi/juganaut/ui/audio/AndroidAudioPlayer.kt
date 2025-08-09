package de.glueckstobi.juganaut.ui.audio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object AndroidAudioPlayer : AudioPlayer, ComponentActivity() {
    override var sfxVolume = 1f
    override var musicVolume = 0.5f

    private var musicPlayer: ExoPlayer? = null
    private var sfxPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        musicPlayer = ExoPlayer.Builder(this).build()
        sfxPlayer = ExoPlayer.Builder(this).build()
    }

    override fun onDestroy() {
        musicPlayer?.release()
        sfxPlayer?.release()
        super.onDestroy()
    }

    override fun startMusic() {
        musicPlayer?.setMediaItem(MediaItem.fromUri(AudioSample.MainLoop.path))
        musicPlayer?.volume = musicVolume
        musicPlayer?.repeatMode = androidx.media3.common.Player.REPEAT_MODE_ONE
        musicPlayer?.prepare()
        musicPlayer?.play()
    }

    override fun playSfx(
        sample: AudioSample,
        volume: Float
    ) {
        sfxPlayer?.addMediaItem(MediaItem.fromUri(sample.path))
        sfxPlayer?.volume = volume * sfxVolume
        sfxPlayer?.repeatMode = androidx.media3.common.Player.REPEAT_MODE_OFF
        sfxPlayer?.prepare()
        sfxPlayer?.play()
    }

    override fun stopMusic() {
        musicPlayer?.stop()
    }

    override fun stopAll() {
        musicPlayer?.stop()
        sfxPlayer?.stop()
    }
}