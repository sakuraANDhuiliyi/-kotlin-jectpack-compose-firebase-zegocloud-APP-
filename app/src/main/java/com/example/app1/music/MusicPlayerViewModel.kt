package com.example.app1.music

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = getApplication<Application>().applicationContext

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

    // 当前播放的歌曲信息
    private var currentSongInfo: SongInfo? = null

    // Gson 实例
    private val gson = Gson()

    init {
        // 应用启动时恢复播放状态和歌曲信息
        restorePlaybackInfo()
        restoreSongInfo()
    }

    // 保存歌曲信息
    private fun saveSongInfo(songInfo: SongInfo) {
        currentSongInfo = songInfo
        saveSongInfo(context, songInfo)
    }

    // 初始化 MediaPlayer 并开始播放
    fun play(songInfo: SongInfo) {
        if (songInfo.mp3Url.isBlank()) {
            Toast.makeText(context, "URL不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        // 如果当前歌曲与传入的不一致，或者MediaPlayer为null，重新初始化MediaPlayer
        if (currentSongInfo?.mp3Url != songInfo.mp3Url || mediaPlayer == null) {
            currentSongInfo = songInfo
            saveSongInfo(songInfo)
            initializeMediaPlayer(songInfo.mp3Url)
        } else {
            // 如果MediaPlayer已存在且未播放，则继续播放
            mediaPlayer?.let { player ->
                if (!player.isPlaying) {
                    player.start()
                    _isPlaying.value = true
                    startProgressUpdater()
                    savePlaybackInfo()
                }
            }
        }
    }

    // 初始化MediaPlayer
    private fun initializeMediaPlayer(mp3url: String, seekPosition: Int = 0, shouldPlay: Boolean = true) {
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
                        if (seekPosition > 0) {
                            seekTo(seekPosition)
                        }
                        if (shouldPlay) {
                            start()
                            _isPlaying.value = true
                            startProgressUpdater()
                        }
                        savePlaybackInfo()
                    }
                    setOnCompletionListener {
                        _isPlaying.value = false
                        releaseMediaPlayer()
                        savePlaybackInfo()
                    }
                    setOnErrorListener { _, what, extra ->
                        Toast.makeText(context, "播放错误: what=$what, extra=$extra", Toast.LENGTH_SHORT).show()
                        _isPlaying.value = false
                        releaseMediaPlayer()
                        savePlaybackInfo()
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
            savePlaybackInfo()
        }
    }

    // 暂停播放
    fun pause() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                _isPlaying.value = false
                savePlaybackInfo()
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
                savePlaybackInfo()
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
            savePlaybackInfo()
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
        savePlaybackInfo()
    }

    // 保存播放信息到 SharedPreferences
    private fun savePlaybackInfo() {
        val playbackInfo = PlaybackInfo(
            currentPosition = mediaPlayer?.currentPosition ?: 0,
            isPlaying = _isPlaying.value
        )
        savePlaybackInfo(context, playbackInfo)
    }

    // 恢复播放信息
    private fun restorePlaybackInfo() {
        val playbackInfo = getPlaybackInfo(context)
        playbackInfo?.let {
            // 暂时存储恢复的播放信息，稍后在初始化 MediaPlayer 时使用
            restoredPlaybackInfo = it
        }
    }

    // 恢复歌曲信息
    private fun restoreSongInfo() {
        val songInfo = getSongInfo(context)
        songInfo?.let {
            currentSongInfo = it
            // 恢复 MediaPlayer
            val playbackInfo = restoredPlaybackInfo
            initializeMediaPlayer(
                mp3url = it.mp3Url,
                seekPosition = playbackInfo?.currentPosition ?: 0,
                shouldPlay = playbackInfo?.isPlaying ?: false
            )
        }
    }

    // 存储恢复的播放信息
    private var restoredPlaybackInfo: PlaybackInfo? = null
}
