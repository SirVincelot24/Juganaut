package de.glueckstobi.juganaut.ui.audio

import android.content.Context
import android.media.SoundPool
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import de.glueckstobi.juganaut.MainActivity
import juganaut.app.generated.resources.Res

object AndroidAudioPlayer : AudioPlayer {
    override var sfxVolume = 1f
    override var musicVolume = 0.5f

    lateinit var musicPlayer: ExoPlayer
    lateinit var sfxSoundPool: SoundPool

    private val soundIDs = mutableMapOf<SFXAudioSample, Int>()


    override fun initMusicPlayers() {
        MainActivity().initPlayer(MainActivity().applicationContext)
    }

    fun initSfx(context: Context) {
        for (sfx in SFXAudioSample.entries) {
//            Funktioniert nicht
//            val url = URL(Res.getUri(sfx.path)).path
//            soundIDs[sfx] = sfxSoundPool.load(url, 1)
//            Funktioniert auch nicht
//            val fd = resolver.openFileDescriptor(Res.getUri(sfx.path).toUri(), "r")
//            val afd = AssetFileDescriptor(fd, 0, AssetFileDescriptor.UNKNOWN_LENGTH)
//            soundIDs[sfx] = sfxSoundPool.load(afd, 1)

            val soundID = context.resources.getIdentifier(sfx.path.removePrefix("files/").removeSuffix(".wav"), "raw", context.packageName)
            soundIDs[sfx] = sfxSoundPool.load(context, soundID, 1)
        }
    }

    override fun startGameMusic() {
        musicPlayer.setMediaItem(MediaItem.fromUri(Res.getUri(AudioSample.MainLoop.path)))
        musicPlayer.volume = musicVolume
        musicPlayer.prepare()
        musicPlayer.play()
        Log.d("Juganaut", "Started Game music")
    }

    override fun playSfx(
        sample: SFXAudioSample,
        volume: Float
    ) {
        soundIDs[sample]?.let { soundID ->
            sfxSoundPool.play(soundID, volume, volume, 1, 0, 1f)
        } ?: Log.e("SoundPool", "Sound '$sample' not found")
        Log.d("SoundPool", "SFX $sample started with volume $volume")
    }

    override fun stopMusic() {
        musicPlayer.stop()
    }

    override fun stopAll() {
        musicPlayer.stop()
        MainActivity().releasePlayer()
    }
}