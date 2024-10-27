package com.example.app1.videoPlayer

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView



@OptIn(UnstableApi::class)
class VideoPlayViewModel : ViewModel() {
    private var exoPlayer: ExoPlayer? = null
    var url : String = ""



    fun initializePlayer(context: Context) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        exoPlayer?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY && exoPlayer?.playWhenReady == true) {
                    Log.d("PlayerState", "Player is ready and playing")
                }
            }
        })
        // 开始播放
        exoPlayer?.playWhenReady = true
    }
    fun getCurrentPlaybackTime(): Long {
        return exoPlayer?.currentPosition ?: 0L
    }
    fun releasePlayer() {
        Log.d("VideoPlayViewModel", "Releasing player")
        exoPlayer?.playWhenReady = false
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null
        Log.d("VideoPlayViewModel", "Player released")
    }

    fun playVideo() {
        exoPlayer?.let { player ->
            player.apply {
                stop()
                clearMediaItems()
                setMediaItem(MediaItem.fromUri(url))
                playWhenReady = true
                prepare()
                play()
            }
        }
    }

    fun playerViewBuilder(context: Context): PlayerView {
        val activity = context as Activity
        val playerView = PlayerView(context).apply {
            player = exoPlayer
            controllerAutoShow = true
            keepScreenOn = true
            setFullscreenButtonClickListener { isFullScreen ->
                if (isFullScreen){
                    activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
                }else{
                    activity.requestedOrientation = SCREEN_ORIENTATION_USER
                }
            }
        }
        return playerView
    }

}