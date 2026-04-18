package de.glueckstobi.juganaut.ui.audio

import android.content.Context
import android.media.SoundPool
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import de.glueckstobi.juganaut.MainActivity

object AndroidAudioPlayer : AudioPlayer {
    override var sfxVolume = 1f
    override var musicVolume = 0.5f

    lateinit var musicPlayer: ExoPlayer
    lateinit var sfxPlayer: ExoPlayer
    lateinit var sfxSoundPool: SoundPool

    private val soundIDs = mutableMapOf<SFXAudioSample, Int>()


    override fun initMusicPlayers() {
        MainActivity().initPlayer()
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