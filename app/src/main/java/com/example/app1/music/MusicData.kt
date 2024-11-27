package com.example.app1.music


data class Song(val id: Long)

data class Artist(
    val id: Int,
    val name: String,
    val img1v1Url: String
)

data class Album(
    val name: String,
    val picUrl: String // 封面图片地址
)

data class SearchResponse(
    val result: SearchResult
)

data class SearchResult(
    val songs: List<Song>
)
data class SongDetailResponse(val songs: List<SongDetail>)

data class SongDetail(
    val id: Long,
    val name: String,
    val album: Album
)
// 定义歌曲信息类
data class SongInfo(
    val id: Long,
    val name: String,
    val albumName: String,
    val coverImageUrl: String,
    val mp3Url: String
)


data class Lrc(
    val lyric: String? = null
)
data class Lyric(
    val timeInSeconds: Float, // 歌词时间，以秒为单位
    val text: String // 歌词文本
)