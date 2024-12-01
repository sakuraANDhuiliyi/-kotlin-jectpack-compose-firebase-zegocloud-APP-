package com.example.app1.music

import android.content.Context
import com.google.gson.Gson

fun saveSongInfo(context: Context, songInfo: SongInfo) {
    val sharedPreferences = context.getSharedPreferences("SongInfo", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // 将 SongInfo 对象转为 JSON 字符串
    val songInfoJson = Gson().toJson(songInfo)

    editor.putString("songInfo", songInfoJson)  // 存储 JSON 字符串
    editor.apply()
}
fun getSongInfo(context: Context): SongInfo? {
    val sharedPreferences = context.getSharedPreferences("SongInfo", Context.MODE_PRIVATE)
    // 获取存储的 JSON 字符串
    val songInfoJson = sharedPreferences.getString("songInfo", null)
    return if (songInfoJson != null) {
        // 将 JSON 字符串转回 SongInfo 对象
        Gson().fromJson(songInfoJson, SongInfo::class.java)
    } else {
        null  // 如果没有保存过数据，返回 null
    }
}

fun savePlaybackInfo(context: Context, playbackInfo: PlaybackInfo) {
    val sharedPreferences = context.getSharedPreferences("PlaybackInfo", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val playbackInfoJson = Gson().toJson(playbackInfo)
    editor.putString("playbackInfo", playbackInfoJson)
    editor.apply()
}

// 获取播放信息
fun getPlaybackInfo(context: Context): PlaybackInfo? {
    val sharedPreferences = context.getSharedPreferences("PlaybackInfo", Context.MODE_PRIVATE)
    val playbackInfoJson = sharedPreferences.getString("playbackInfo", null)
    return if (playbackInfoJson != null) {
        Gson().fromJson(playbackInfoJson, PlaybackInfo::class.java)
    } else {
        null
    }
}
