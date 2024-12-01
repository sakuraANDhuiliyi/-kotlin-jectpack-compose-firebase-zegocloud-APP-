package com.example.app1.music

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(songInfo: SongInfo,navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text(songInfo.name) }
            )
        },
        content = { padding -> Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            ) {

        }
            MusicPlayer(songInfo = songInfo)}
   )
}



@Composable
fun MusicPlayer(songInfo: SongInfo) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var rotation by remember { mutableFloatStateOf(0f) }
        // 每隔16ms更新旋转角度，模拟持续旋转
        LaunchedEffect(key1 = true) {
            while (true) {
                rotation += 1f // 每次递增1度
                if (rotation >= 360f) {
                    rotation = 0f // 角度达到360时重置
                }
                delay(16) // 每16ms更新一次，形成平滑旋转
            }
        }

        val uiController = rememberSystemUiController()
        // 定义颜色列表
        val colors = listOf(
            Color(0xFFFF5A5A),
            Color(0xFFFFBE3D),
            Color(0xFFD3FF5A),
            Color(0xFF5AFFB8),
            Color(0xFF5AFAFF),
            Color(0xFF5A9CFF),
            Color(0xFF6A5AFF),
            Color(0xFFC55AFF),
            Color(0xFFFF5A94),
        )
        val darkColors = listOf(
            Color(0xFFBD3030),
            Color(0xFFAF8024),
            Color(0xFF83A525),
            Color(0xFF2B8D63),
            Color(0xFF288D91),
            Color(0xFF294E85),
            Color(0xFF2E248D),
            Color(0xFF5C1F7E),
            Color(0xFF812344),
        )

        // 动态选择颜色的索引
        var colorIndex by remember { mutableIntStateOf(0) }

        // 动态颜色切换逻辑
        LaunchedEffect(Unit) {
            while (true) {
                delay(2100) // 每2.1秒切换一次颜色
                colorIndex = (colorIndex + 1) % colors.size
            }
        }

        val animatedColor by animateColorAsState(
            targetValue = colors[colorIndex],
            animationSpec = tween(durationMillis = 2000), label = ""
        )
        val animatedDarkColor by animateColorAsState(
            targetValue = darkColors[colorIndex],
            animationSpec = tween(durationMillis = 2000), label = ""
        )

        // 设置状态栏和导航栏颜色
        SideEffect {
            uiController.setStatusBarColor(animatedColor, darkIcons = false)
            uiController.setNavigationBarColor(animatedDarkColor)
        }
        // UI 布局
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 歌曲封面
            Image(
                painter = rememberAsyncImagePainter(songInfo.coverImageUrl),
                contentDescription = "专辑封面",
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape) // 裁剪为圆形
                    .graphicsLayer(rotationZ = rotation) // 添加旋转效果
            )
            // 歌曲名称和专辑名称
            Text(
                text = songInfo.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = songInfo.albumName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            val lyrics = remember { mutableStateOf<List<Lyric>?>(null) }

            // 获取歌词
            LaunchedEffect(songInfo.id) {
                lyrics.value = fetchLyrics(songInfo.id) // 异步获取歌词
            }
            MusicPlayerApp(songInfo,lyrics.value)
        }
    }

    }

@Composable
fun MusicPlayerApp(
    songInfo: SongInfo,
    lyrics: List<Lyric>?,
    viewModel: MusicPlayerViewModel = viewModel()
) {
    // 收集 ViewModel 中的状态
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val progress by viewModel.progress.collectAsState()
    // 记录当前 SongInfo 是否已经被播放
    var isSongInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(songInfo) {
        if (!isSongInitialized) {
            viewModel.play(songInfo)
            isSongInitialized = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 显示歌曲标题
        Text(
            text = songInfo.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        lyrics?.let {
            Text(
                text = getCurrentLyric(it, currentTime),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = progress,
            onValueChange = { newProgress ->
                // 防止进度条值无效
                if (!newProgress.isNaN() && newProgress in 0f..1f) {
                    viewModel.seekTo(newProgress)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            valueRange = 0f..1f,
            steps = 0
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 控制按钮区
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /* 上一曲逻辑 */ }) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "上一曲"
                )
            }
            IconButton(onClick = {
                if (isPlaying) {
                    viewModel.pause()
                } else {
                    viewModel.play(songInfo)
                }
            }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "暂停" else "播放"
                )
            }
            IconButton(onClick = { /* 下一曲逻辑 */ }) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "下一曲"
                )
            }
        }
    }
}
fun getCurrentLyric(lyrics: List<Lyric>, currentTime: Float): String {
    val currentLyric = lyrics.filter { it.timeInSeconds <= currentTime }
        .maxByOrNull { it.timeInSeconds } // 获取最近的歌词
    return currentLyric?.text ?: "正在加载歌词..."
}

