package com.example.app1.chatWithUser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import com.example.app1.ui.theme.App1Theme
import com.zegocloud.zimkit.common.ZIMKitRouter
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType
import com.zegocloud.zimkit.components.conversation.ui.ZIMKitConversationFragment
import com.zegocloud.zimkit.services.ZIMKit
import com.zegocloud.zimkit.services.ZIMKitConfig
import im.zego.zim.enums.ZIMErrorCode

class ChatActivity : FragmentActivity() {
    private var openConversations by mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        init()
        setContent {
            App1Theme {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                innerPadding ->
                Screen(modifier = Modifier.padding(innerPadding))
            }
        } }
    }
    @Composable
    fun Screen(modifier: Modifier = Modifier){
        var userid by remember { mutableStateOf("") }
        var userpasswd by remember { mutableStateOf("") }
        if (openConversations){
            ConversationsScreen()
        }else{
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){

                TextField(
                    value = userpasswd,
                    onValueChange = {userpasswd = it},
                    label = { Text("用户名") },
                    modifier=Modifier.align(Alignment.CenterHorizontally)
                )
                TextField(
                    value = userid,
                    onValueChange = {userid = it},
                    label = { Text("密码") },
                    modifier=Modifier.align(Alignment.CenterHorizontally)
                )
                Button(onClick = {
                    connectUser(userid,userpasswd)
                },Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "聊天")
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ConversationsScreen(modifier: Modifier = Modifier) {
        var expanded by remember { mutableStateOf(false) }
        val fabSize = 56.dp
        var showdialog by remember { mutableStateOf(false) }
        var newUser by remember { mutableStateOf("") }
        Scaffold(modifier = Modifier.fillMaxSize()) {
            padding->
            TopAppBar(title = { Text("") })
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            val fragmentManager = remember {
                this@ChatActivity.supportFragmentManager
            }

            val fragment = remember {
                ZIMKitConversationFragment()
            }

            AndroidView(
                modifier = modifier,
                factory = {
                    FrameLayout(it).apply {
                        id = View.generateViewId()
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                update = {
                    fragmentManager.beginTransaction().replace(
                        it.id, fragment
                    ).commit()
                }
            )
            FloatingActionButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {
                expanded = !expanded
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false},modifier=Modifier.align(Alignment.CenterEnd),
                offset = DpOffset(0.dp,y =fabSize)) {
                DropdownMenuItem(text = { Text(text = "添加聊天") }, onClick = {expanded = false;showdialog = true})
                DropdownMenuItem(text = { Text(text = "待完成") }, onClick = {})
                DropdownMenuItem(text = { Text(text = "待完成") }, onClick = {})
                DropdownMenuItem(text = { Text(text = "待完成") }, onClick = {})
            }

            if (showdialog) {
                Dialog(
                    onDismissRequest = { showdialog = false }
                ) {
                    Box(
                        modifier = Modifier
                            .size(400.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(text="请输入要输入的内容")
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                            TextField(
                                value =newUser ,
                                onValueChange = {
                                    newUser = it
                                },
                                label = { Text(text = "输入UserId：") },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Button(
                                onClick = {
                                    ZIMKitRouter.toMessageActivity(this@ChatActivity,newUser,ZIMKitConversationType.ZIMKitConversationTypePeer)
                                    showdialog = false
                                    newUser=""
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("确认添加")
                            }
                        }
                    }
                }
            }
        }
        }

        }


    private fun connectUser(useId: String,useName:String){
        val userImage = "https://i0.hdslb.com/bfs/new_dyn/73a9b3639fe514fdd017f82cfbd1ec6e333497172.jpg"
        ZIMKit.connectUser(useId, useName, userImage) { info ->
            if (info.code == ZIMErrorCode.SUCCESS) {
                openConversations = true
            } else {
                Log.e("ChatActivity", "Connect user failed: ${info.message}, code: ${info.code}")
            }
        }
    }
    private fun init(){
        // val appID = 678584385
        // val appSign="071ecf0af4034066a0a5a330529bc0cd8b4353d6249ce3c37c0f2bd510a01a52"
        // ZIMKit.initWith(application, appID.toLong(),appSign, ZIMKitConfig())
        // ZIMKit.initNotifications()
    }
}