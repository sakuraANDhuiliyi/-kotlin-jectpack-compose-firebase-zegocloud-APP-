package com.example.app1.music


import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// 获取歌曲 ID 列表
suspend fun getSongIds(query: String): List<Long>? = withContext(Dispatchers.IO) {
    val url = "https://music.163.com/api/search/get?s=$query&type=1&offset=0&limit=15"
    val client = OkHttpClient()

    val request = Request.Builder().url(url).build()
    try {
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                val searchResponse = Gson().fromJson(responseData, SearchResponse::class.java)
                searchResponse.result.songs.map { it.id }
            } else {
                null
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// 获取歌曲的详细信息
suspend fun getSongDetails(songIds: List<Long>): MutableList<SongInfo> = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val songInfoList = mutableListOf<SongInfo>()

    for (songId in songIds) {
        val url = "https://music.163.com/api/song/detail?ids=[$songId]"
        val request = Request.Builder().url(url).build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    val songDetailResponse = Gson().fromJson(responseData, SongDetailResponse::class.java)
                    val songDetail = songDetailResponse.songs.firstOrNull()

                    if (songDetail != null) {
                        val songInfo = SongInfo(
                            id = songDetail.id,
                            name = songDetail.name,
                            albumName = songDetail.album.name,
                            coverImageUrl = songDetail.album.picUrl,
                            mp3Url = "https://music.163.com/song/media/outer/url?id=${songDetail.id}.mp3"
                        )
                        songInfoList.add(songInfo)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    songInfoList
}



