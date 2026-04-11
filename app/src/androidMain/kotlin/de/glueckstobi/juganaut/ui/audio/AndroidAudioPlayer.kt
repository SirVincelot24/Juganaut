package de.glueckstobi.juganaut.ui.audio

import android.media.SoundPool
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import de.glueckstobi.juganaut.MainActivity
import juganaut.app.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

object AndroidAudioPlayer : AudioPlayer {
    override var sfxVolume = 1f
    override var musicVolume = 0.5f

    lateinit var musicPlayer: ExoPlayer
    lateinit var sfxPlayer: ExoPlayer
    lateinit var sfxSoundPool: SoundPool

    private val soundIDs = mutableMapOf<SFXAudioSample, Int>()

    @OptIn(ExperimentalResourceApi::class)
    fun initSfx() {
        for (sfx in SFXAudioSample.entries) {
            soundIDs[sfx] = sfxSoundPool.load(Res.getUri(sfx.path), 1)
//            ContentResolver(MainActivity()).openFileDescriptor(Uri.Builder().path(Res.getUri(sfx.path)).build(), "r")
//            soundIDs[sfx] = sfxSoundPool.load(fd.fileDescriptor, 0, file.length(), 1)
        }
    }

    override fun startMusic() {
        Log.d("Juganaut", "Started music")
        musicPlayer.volume = musicVolume
        musicPlayer.play()
    }

    override fun playSfx(
        sample: SFXAudioSample,
        volume: Float
    ) {
//        sfxPlayer.addMediaItem(MediaItem.fromUri(Res.getUri(sample.path)))
//        sfxPlayer.volume = volume * sfxVolume
//        sfxPlayer.prepare()
//        sfxPlayer.play()
//        sfxPlayer.release()
        soundIDs[sample]?.let { soundID ->
            sfxSoundPool.play(soundID, volume, volume, 1, 0, 1f)
        } ?: Log.e("SoundPool", "Sound '$sample' not found")
        Log.d("SoundPool", "SFX $sample started with volume $volume")
    }

    override fun stopMusic() {
        musicPlayer.stop()
        musicPlayer.removeListener(MainActivity().playbackStateListener)
        musicPlayer.release()
    }

    override fun stopAll() {
        musicPlayer.stop()
        sfxPlayer.stop()
        MainActivity().releasePlayer()
    }
}