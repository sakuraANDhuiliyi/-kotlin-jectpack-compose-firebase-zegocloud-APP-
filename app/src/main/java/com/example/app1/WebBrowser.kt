package com.example.app1

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebBrowser(modifier: Modifier) {
    var url by remember { mutableStateOf("https://www.yandex.com") }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var textState by remember { mutableStateOf(TextFieldValue(url)) }
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(title = { Text(text = "智慧引擎") }, actions = {
                IconButton(onClick = {
                    val inputUrl = textState.text
                    url = if (!inputUrl.startsWith("http://") && !inputUrl.startsWith("https://")) {
                        "https://$inputUrl"
                    } else {
                        inputUrl
                    }
                    webView?.loadUrl(url)
                }) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "")
                }
                IconButton(onClick = { webView?.goBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Page")
                }
                IconButton(onClick = { webView?.goForward() }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Page")
                }
            })
        },
        content = { paddingValues ->
            Column(Modifier.padding(paddingValues)) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .border(
                            2.dp, Color.DarkGray,
                            RoundedCornerShape(8.dp)
                        ),
                ) {
                    BasicTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        decorationBox = { innerTextField ->
                            if (textState.text.isEmpty()) {
                                Text(text = "Enter URL", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                }

                AndroidView(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp), factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        loadUrl(url)
                        webView = this
                    }
                }, update = {
                    webView?.loadUrl(url)
                })
            }
        })
}