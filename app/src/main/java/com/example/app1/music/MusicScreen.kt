package com.example.app1.music


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MusicScreen(navController: NavHostController) {
    // 状态变量
    var query by remember { mutableStateOf("") }
    var songInfoList by remember { mutableStateOf<List<SongInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }  // 是否正在加载
    var errorMessage by remember { mutableStateOf<String?>(null) } // 错误消息
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // 搜索输入框和按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text(text="搜索歌曲", fontSize = 10.sp) },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 8.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp))
                    ,
                maxLines = 1,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        performSearch(
                            query = query,
                            onSuccess = { results ->
                                songInfoList = results
                                isLoading = false
                            },
                            onError = { error ->
                                errorMessage = error
                                isLoading = false
                            },
                            setLoading = { loading ->
                                isLoading = loading
                            },
                            coroutineScope = coroutineScope
                        )
                    }
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    performSearch(
                        query = query,
                        onSuccess = { results ->
                            songInfoList = results
                            isLoading = false
                        },
                        onError = { error ->
                            errorMessage = error
                            isLoading = false
                        },
                        setLoading = { loading ->
                            isLoading = loading
                        },
                        coroutineScope = coroutineScope
                    )
                },
                modifier = Modifier.height(56.dp)
            ) {
                Text("搜索")
            }
        }

        // 显示加载指示器
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        // 显示错误消息
        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        // 显示搜索结果
        if (!isLoading && errorMessage == null) {
            SongSearchScreen(songInfoList, navController)
        }
    }
}

// 执行搜索函数
fun performSearch(
    query: String,
    onSuccess: (List<SongInfo>) -> Unit,
    onError: (String) -> Unit,
    setLoading: (Boolean) -> Unit,
    coroutineScope: CoroutineScope
) {
    if (query.isBlank()) {
        onError("搜索关键词不能为空")
        return
    }
    coroutineScope.launch {
        setLoading(true)
        try {
            val songIds = getSongIds(query)
            if (!songIds.isNullOrEmpty()) {
                val songDetails = getSongDetails(songIds)
                if (songDetails.isNotEmpty()) {
                    onSuccess(songDetails)
                } else {
                    onError("未找到相关歌曲")
                }
            } else {
                onError("未找到相关歌曲")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError("搜索过程中出现错误")
        } finally {
            setLoading(false)
        }
    }
}
