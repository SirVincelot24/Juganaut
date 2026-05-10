package de.sirvincelot.juganaut

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import de.sirvincelot.juganaut.ui.audio.AndroidAudioPlayer.initSfx
import de.sirvincelot.juganaut.ui.audio.AndroidAudioPlayer.musicPlayer
import de.sirvincelot.juganaut.ui.audio.AndroidAudioPlayer.musicVolume
import de.sirvincelot.juganaut.ui.audio.AndroidAudioPlayer.sfxSoundPool
import de.sirvincelot.juganaut.ui.audio.AudioSample
import de.sirvincelot.juganaut.ui.compose.MainGuiAndroidCompose
import juganaut.app.generated.resources.Res

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        MainGuiAndroidCompose.startPlaying(this)
    }

    val playbackStateListener: Player.Listener = playbackStateListener()
    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    override fun onStart() {
        initPlayer(this)
        super.onStart()
    }


    override fun onResume() {
        initPlayer(this)
        hideSystemUi()
        super.onResume()
    }

    override fun onPause() {
        releasePlayer()
        super.onPause()
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


    fun initPlayer(context: Context) {
        musicPlayer = ExoPlayer.Builder(context).build().also { exoPlayer ->
            exoPlayer.setMediaItem(MediaItem.fromUri(Res.getUri(AudioSample.MainLoop.path)))
            exoPlayer.volume = musicVolume
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
            exoPlayer.prepare()
            exoPlayer.addListener(playbackStateListener)
        }

        sfxSoundPool = SoundPool.Builder().setMaxStreams(5).setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_GAME).build()
        ).build()


        initSfx(context)
    }

    fun releasePlayer() {
        musicPlayer.let { player ->
            playbackPosition = player.currentPosition
            mediaItemIndex = player.currentMediaItemIndex
            playWhenReady = player.playWhenReady
            player.removeListener(playbackStateListener)
            player.release()
        }
        sfxSoundPool.release()
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.d("PlayerActivity", "changed state to $stateString")
        }
    }

}