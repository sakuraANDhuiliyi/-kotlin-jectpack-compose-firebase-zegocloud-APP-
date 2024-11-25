package com.example.app1.videoPlayer

import android.net.Uri
import android.os.Build
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.app.Activity
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app1.ChatPage
import com.example.app1.ChatViewModel
import com.example.app1.videoPlayer.BottomControlBar
import com.example.app1.videoPlayer.TopControlBar
import kotlinx.coroutines.Job

@RequiresApi(35)
@Composable
fun VideoPlayer(
    videoUri: String,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onPlaybackStateChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current
    // 创建并管理 ExoPlayer 实例
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    // 控制栏可见性状态
    var controlsVisible by remember { mutableStateOf(true) }

    // 播放进度状态
    var progress by remember { mutableFloatStateOf(0f) }

    // 用户交互状态
    var isUserInteracting by remember { mutableStateOf(false) }

    // 协程作用域与隐藏任务句柄
    val coroutineScope = rememberCoroutineScope()
    var hideJob by remember { mutableStateOf<Job?>(null) }

    // 初始化播放器并监听状态变化
    DisposableEffect(videoUri) {
        exoPlayer.setMediaItem(MediaItem.fromUri(videoUri))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                onPlaybackStateChanged(playbackState == Player.STATE_READY && exoPlayer.playWhenReady)
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // 更新播放进度逻辑
    LaunchedEffect(exoPlayer) {
        while (true) {
            progress = if (exoPlayer.duration > 0) {
                exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()
            } else {
                0f
            }
            delay(500)
        }
    }

    // 自动隐藏逻辑
    fun startHideTimer() {
        hideJob?.cancel() // 取消之前的隐藏任务
        hideJob = coroutineScope.launch {
            delay(5000) // 设置统一的隐藏延迟
            if (!isUserInteracting) { // 确保非交互状态
                controlsVisible = false
            }
        }
    }

    // 显示控制栏并重启隐藏逻辑
    fun showControls() {
        controlsVisible = true
        startHideTimer()
    }

    // 隐藏系统状态栏和导航栏
    fun hideSystemBars(view: View) {
        val activity = view.context as? Activity ?: return
        val window = activity.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(WindowInsets.Type.systemBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    // 隐藏系统栏
    val contextView = LocalView.current
    LaunchedEffect(Unit) {
        hideSystemBars(contextView)
        showControls()
    }

    // 视频播放器主布局
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                controlsVisible = !controlsVisible
                if (controlsVisible) showControls()
            }
    ) {
        var showAiDialog by remember { mutableStateOf(false) } // 控制AI消息对话框的显示
        val chatViewModel: ChatViewModel = viewModel()
        AndroidView(
            factory = { context ->
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    setUseController(false)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        if (showAiDialog) {
            Dialog(
                onDismissRequest = {
                    showAiDialog = false
                }
            ) {
                // 使用 Surface 来设置背景颜色和圆角
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.background, // 设置背景颜色
                    modifier = Modifier
                        .fillMaxWidth(1f) // 设置弹窗宽度为屏幕宽度的90%
                        .fillMaxHeight(0.9f) // 设置弹窗高度为屏幕高度的80%
                ) {
                    // 嵌套您的 ChatPage Composable
                    ChatPage(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        chatViewModel
                    )
                }
            }
        }
        if (controlsVisible) {
            TopControlBar(
                title = "Playing Video",
                onBackClicked = onBackClicked,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        if (controlsVisible) {
            BottomControlBar(
                isPlaying = exoPlayer.playWhenReady,
                onPlayPause = {
                    exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                    showControls()
                },
                onNext = { /* 下一集逻辑（未实现） */ },
                onPrevious = { /* 上一集逻辑（未实现） */ },
                progress = progress,
                totalDuration = exoPlayer.duration,
                onProgressChanged = { newProgress, isUserDragging -> // 接收两个参数
                    // 计算目标播放时间（总时长 * 进度百分比）
                    val targetTime = (exoPlayer.duration * newProgress).toLong()

                    // 跳转到目标时间
                    exoPlayer.seekTo(targetTime)

                    // 更新交互状态
                    isUserInteracting = isUserDragging
                    if (!isUserInteracting) {
                        startHideTimer() // 恢复自动隐藏控制栏的逻辑
                    }
                },
                modifier = Modifier.align(Alignment.BottomStart),
                onAI = {
                    showAiDialog = true
                    exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                    showControls()
                }
            )
        }
    }
}



