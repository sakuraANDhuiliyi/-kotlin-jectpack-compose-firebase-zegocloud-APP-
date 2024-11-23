package com.example.app1.videoPlayer

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import android.content.pm.ActivityInfo
import android.view.View


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
        useController = true // 确保播放器控制器可见

        // 设置全屏按钮的点击监听
        setFullscreenButtonClickListener { isFullScreen ->
            if (isFullScreen) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                // 强制隐藏系统UI，确保全屏播放
                activity.window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        )
            } else {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                // 恢复系统UI显示
                activity.window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_VISIBLE
                        )
            }
        }
    }

    return playerView
}



}