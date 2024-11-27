package com.example.app1.music

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

data class LyricsResponse(
    @SerializedName("songStatus") val songStatus: Int,
    @SerializedName("lyricVersion") val lyricVersion: Int,
    @SerializedName("lyric") val lyric: String,
    @SerializedName("code") val code: Int
)

// 获取歌词的函数
suspend fun fetchLyrics(songId: Long): List<Lyric> = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val url = "https://music.163.com/api/song/media?id=$songId"
    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()
    if (!response.isSuccessful) throw Exception("请求失败：$response")

    val responseBody = response.body?.string() ?: throw Exception("响应体为空")
    val gson = Gson()
    val lyricsResponse = gson.fromJson(responseBody, LyricsResponse::class.java)
    val lyricText = lyricsResponse.lyric

    // 解析歌词文本
    val lyricLines = lyricText.lines()
    val regex = Regex("""\[(\d{2}):(\d{2}\.\d{2})\](.*)""")
    val lyricsList = mutableListOf<Lyric>()

    for (line in lyricLines) {
        val matchResult = regex.matchEntire(line)
        if (matchResult != null) {
            val (minutesStr, secondsStr, text) = matchResult.destructured
            val minutes = minutesStr.toInt()
            val seconds = secondsStr.toFloat()
            val timeInSeconds = minutes * 60 + seconds
            lyricsList.add(Lyric(timeInSeconds, text.trim()))
        }
    }

    lyricsList
}

// 格式化时间为字符串
fun formatTime(timeInSeconds: Float): String {
    val totalSeconds = timeInSeconds.toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val millis = ((timeInSeconds - totalSeconds) * 100).toInt()
    return String.format("%02d:%02d.%02d", minutes, seconds, millis)
}






