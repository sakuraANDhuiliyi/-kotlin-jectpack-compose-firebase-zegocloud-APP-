package com.example.app1.music

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


    @Composable
    fun SongSearchScreen(songs:List<SongInfo>,navController: NavController) {
        Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn {
                    items(songs) { song ->
                        SongItem(song = song, navController = navController)
                    }
                }
            }
        }
    @Composable
    fun SongItem(song: SongInfo, navController: NavController) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            val songJson = Gson().toJson(song)
            // URL 编码，确保字符串在 URL 中安全传输
            val encodedSongJson = URLEncoder.encode(songJson, StandardCharsets.UTF_8.toString())
            // 导航到 MusicPlayerScreen 并传递序列化的 SongInfo
            navController.navigate("musicPlayer/$encodedSongJson")
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 歌曲封面
                AsyncImage(
                    model = song.coverImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop,
                )
                // 歌曲名称和专辑名称
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = song.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = song.albumName, style = MaterialTheme.typography.bodySmall)
                }
                // 喜欢和更多按钮
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }

