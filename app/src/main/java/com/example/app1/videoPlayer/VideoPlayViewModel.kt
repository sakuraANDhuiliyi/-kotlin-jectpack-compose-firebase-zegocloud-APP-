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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(UnstableApi::class)
class VideoPlayViewModel : ViewModel() {
    private var exoPlayer: ExoPlayer? = null
    var url : String = ""
    private val _isFullScreenRequested = MutableStateFlow(false)
    val isFullScreenRequested: StateFlow<Boolean> = _isFullScreenRequested

    fun resetFullScreenRequest() {
        viewModelScope.launch {
            _isFullScreenRequested.value = false
        }
    }
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
    val playerView = PlayerView(context).apply {
        player = exoPlayer
        controllerAutoShow = true
        keepScreenOn = true
        useController = true // 确保播放器控制器可见
        setFullscreenButtonClickListener { isFullScreen ->
            if (isFullScreen) {
                // 更新状态，表示请求进入全屏
                viewModelScope.launch {
                    _isFullScreenRequested.value = true
                }
            }
        }
    }

    return playerView
}
}