package com.example.app1.videoPlayer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BottomControlBar(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    progress: Float,
    totalDuration: Long,
    onProgressChanged: (Float, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // 状态管理：是否正在拖动进度条
    var isUserDragging by remember { mutableStateOf(false) }
    // 拖动时的临时进度
    var draggingProgress by remember { mutableFloatStateOf(progress) }
    // 当前实际显示的进度
    val displayedProgress = if (isUserDragging) draggingProgress else progress
    // 计算当前时间和总时间
    val displayedTime = (displayedProgress * totalDuration).toLong()
    val currentTimeFormatted = formatTime(displayedTime)
    val totalDurationFormatted = formatTime(totalDuration)

    // 屏幕尺寸
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    // 延迟更新进度条（防止频繁更新）
    LaunchedEffect(isUserDragging, draggingProgress) {
        if (isUserDragging) {
            delay(50) // 延迟50ms更新进度，优化性能
            onProgressChanged(draggingProgress, false)
        }
    }

    // 主容器
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.03f))
            .height((screenHeight / 3).dp) // 控制底部控制栏的高度
    ) {

        // 时间显示
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(bottom = (screenHeight * 0.26f).dp) // 设置底部间距
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = (screenWidth / 36).dp) // 左侧内边距
            ) {
                Text(
                    text = currentTimeFormatted,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
                Text(
                    text = "/",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    modifier = Modifier.padding(horizontal = (screenWidth / 128).dp) // 字符间距
                )
                Text(
                    text = totalDurationFormatted,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
            }
        }

        // 自定义进度条
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(
                    start = (screenWidth / 64).dp,
                    end = (screenWidth / 64).dp,
                    bottom = (screenHeight * 0.14f).dp // 进度条的底部内边距
                )
                .height((screenHeight * 0.1f).dp) // 进度条高度
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            // 点击进度条时更新进度
                            val clickedPercentage = (offset.x / size.width).coerceIn(0f, 1f)
                            draggingProgress = clickedPercentage
                            onProgressChanged(draggingProgress, true)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isUserDragging = true },
                        onDragEnd = {
                            isUserDragging = false
                            onProgressChanged(draggingProgress, true)
                        },
                        onDragCancel = { isUserDragging = false },
                        onDrag = { change, dragAmount ->
                            // 拖动时更新进度
                            val delta = dragAmount.x / size.width
                            draggingProgress = (draggingProgress + delta).coerceIn(0f, 1f)
                            onProgressChanged(draggingProgress, false)
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val activeWidth = size.width * displayedProgress
                val barHeight = size.height / 5 // 进度条高度
                val verticalOffset = (size.height - barHeight) / 2 // 垂直居中

                // 绘制已播放部分
                drawRoundRect(
                    color = Color(0xFFFF0000),
                    size = androidx.compose.ui.geometry.Size(activeWidth, barHeight),
                    topLeft = Offset(0f, verticalOffset),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
                )

                // 绘制未播放部分
                drawRoundRect(
                    color = Color(0xFFB3B3B3),
                    size = androidx.compose.ui.geometry.Size(size.width - activeWidth, barHeight),
                    topLeft = Offset(activeWidth, verticalOffset),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
                )

                // 绘制浮标
                drawCircle(
                    color = Color.White,
                    radius = barHeight, // 浮标半径
                    center = Offset(activeWidth, size.height / 2) // 浮标位置
                )
            }
        }

        // 播放控制按钮
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = (screenWidth / 64).dp,
                    bottom = (screenHeight * 0.01f).dp // 控制按钮底部间距
                )
        ) {
            val pauseButtonSize = screenHeight * 0.12f
            val lastNextButtonSize = screenHeight * 0.1f

            // 上一集按钮
            IconButton(onClick = onPrevious, modifier = Modifier.size(pauseButtonSize.dp)) {
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = Color.White,
                    modifier = Modifier.size(lastNextButtonSize.dp)
                )
            }

            Spacer(modifier = Modifier.width((screenHeight / 32).dp))

            // 播放/暂停按钮
            IconButton(onClick = onPlayPause, modifier = Modifier.size(pauseButtonSize.dp)) {
                Icon(
                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(pauseButtonSize.dp)
                )
            }

            Spacer(modifier = Modifier.width((screenHeight / 32).dp))

            // 下一集按钮
            IconButton(onClick = onNext, modifier = Modifier.size(pauseButtonSize.dp)) {
                Icon(
                    Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = Color.White,
                    modifier = Modifier.size(lastNextButtonSize.dp)
                )
            }
        }
    }
}

// 格式化时间函数
fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60)) % 60
    val hours = (milliseconds / (1000 * 60 * 60)) % 24
    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
