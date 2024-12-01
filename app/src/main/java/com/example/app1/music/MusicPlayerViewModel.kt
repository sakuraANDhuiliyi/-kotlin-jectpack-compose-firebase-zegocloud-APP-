package com.example.app1.music

import android.app.Application
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private var mediaPlayer: MediaPlayer? = null

    // 播放状态
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    // 当前播放时间（秒）
    private val _currentTime = MutableStateFlow(0f)
    val currentTime: StateFlow<Float> = _currentTime

    // 播放进度（0f 到 1f）
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    // 当前播放的URL
    private var currentUrl: String? = null

    // 初始化 MediaPlayer 并开始播放
    fun play(mp3url: String) {
        if (mp3url.isBlank()) {
            Toast.makeText(context, "URL不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        // 如果当前URL与传入的不一致，或者MediaPlayer为null，重新初始化MediaPlayer
        if (currentUrl != mp3url || mediaPlayer == null) {
            currentUrl = mp3url
            initializeMediaPlayer(mp3url)
        } else {
            // 如果MediaPlayer已存在且未播放，则继续播放
            mediaPlayer?.let { player ->
                if (!player.isPlaying) {
                    player.start()
                    _isPlaying.value = true
                    startProgressUpdater()
                }
            }
        }
    }

    // 初始化MediaPlayer
    private fun initializeMediaPlayer(mp3url: String) {
        // 释放之前的MediaPlayer
        releaseMediaPlayer()
        viewModelScope.launch {
            try {
                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(mp3url)
                    prepareAsync()
                    setOnPreparedListener {
                        start()
                        _isPlaying.value = true
                        startProgressUpdater()
                    }
                    setOnCompletionListener {
                        _isPlaying.value = false
                        releaseMediaPlayer()
                    }
                    setOnErrorListener { _, what, extra ->
                        Toast.makeText(context, "播放错误: what=$what, extra=$extra", Toast.LENGTH_SHORT).show()
                        _isPlaying.value = false
                        releaseMediaPlayer()
                        true
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "无法播放该URL", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
    // 更新播放进度
    private fun startProgressUpdater() {
        viewModelScope.launch {
            while (_isPlaying.value) {
                mediaPlayer?.let { player ->
                    _progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
                    _currentTime.value = player.currentPosition / 1000f
                }
                delay(100) // 每100毫秒更新一次
            }
        }
    }

    // 暂停播放
    fun pause() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                _isPlaying.value = false
            }
        }
    }

    // 停止播放
    fun stop() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.stop()
                _isPlaying.value = false
                releaseMediaPlayer()
            }
        }
    }

    // 跳转到指定进度
    fun seekTo(progress: Float) {
        mediaPlayer?.let { player ->
            val newPosition = (player.duration * progress).toInt()
            player.seekTo(newPosition)
            _progress.value = progress
            _currentTime.value = newPosition / 1000f
        }
    }

    // 释放 MediaPlayer 资源
    private fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onCleared() {
        super.onCleared()
        releaseMediaPlayer()
    }
}
