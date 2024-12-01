package com.example.app1.chatWithAI

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app1.pages.TodoListPage
import com.example.app1.roomDb.viewModel.TodoViewModel

@SuppressLint("SetJavaScriptEnabled")
@RequiresApi(35)
@Composable
fun NewChatPage(chatViewModel: ChatViewModel){
    var selectedCategory by remember { mutableStateOf("AI") }
    val categories = listOf("AI", "网络搜索","闹钟")
    var isAI by remember { mutableStateOf(false) }
    var isInternet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var webView by remember { mutableStateOf<WebView?>(null) }
    val todoViewModel :TodoViewModel = viewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "搜索", fontSize = 10.sp) },
                leadingIcon = {
                    TextButton(onClick = {
                       expanded = true
                    }) {
                        Row{
                            Text(text = selectedCategory)
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(onClick = {
                                selectedCategory = category
                                expanded = false
                            }, text = { Text(category) })
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 8.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp)),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
            TextButton(
                onClick = {
                    when (selectedCategory) {
                        "AI" -> {
                            isAI = !isAI
                            chatViewModel.sendMessage(text)
                            text = ""
                        }
                        "网络搜索" -> {
                            isInternet = !isInternet
                            text = ""
                        }
                        "闹钟"->{
                            text = ""
                        }
                    }
                },
                modifier = Modifier.height(56.dp)
            ) {
                Text("搜索")
            }
        }
        Column {
            when (selectedCategory) {
                "AI" -> {
                    MessageList(
                        modifier = Modifier.weight(1f),
                        messageList = chatViewModel.messageList
                    )
                }
                "网络搜索" -> {
                    AndroidView(modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp), factory = { context ->
                        WebView(context).apply {
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = true
                            loadUrl("https://www.yandex.com")
                            webView = this
                        }
                    }, update = {
                        webView?.loadUrl("https://www.yandex.com")
                    })
                }
                "闹钟"->{
                 TodoListPage(todoViewModel)
                }
            }
        }

    }
}