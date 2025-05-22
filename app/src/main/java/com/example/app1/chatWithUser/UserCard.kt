package com.example.app1.chatWithUser

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.zegocloud.zimkit.common.ZIMKitRouter
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType
import com.zegocloud.zimkit.services.ZIMKit.connectUser
import im.zego.zim.enums.ZIMErrorCode

@Composable
fun UserProfilePage(currentName:String,navController: NavController) {
    val userBio = "躺平"
    val userContact = "111111111111"
    val followersCount = 100
    val followingCount = 100
    val context = LocalContext.current
    var isFollowed by remember { mutableStateOf(true) }
    var imageURL by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    val userDocRef = db.collection("users").document(currentName)
    userDocRef.get().addOnSuccessListener { document ->
        imageURL = if (document.exists()) {
            // 获取 favoriteVideos 列表 (DocumentReference 类型)
            (document.get("imageUrl") as? String).toString()
        } else {
            "https://c-ssl.duitang.com/uploads/item/201803/19/20180319200326_3HvLA.jpeg"
        }
    }.addOnFailureListener { e ->
        Log.w("Firebase", "Error fetching user document", e)
    }
        // 顶部内容：头像、用户名、简介等
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(16.dp)
            ) {
                // 用户头像
                AsyncImage(
                    model = imageURL,
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 用户名
                Text(
                    text = currentName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 用户简介
                Text(
                    text = userBio,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 用户联系方式
                Text(
                    text = "Contact: $userContact",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    // 关注人数
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$followingCount",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "关注",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(24.dp)) // 间隔
                    // 粉丝人数
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$followersCount",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "粉丝",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
            // 操作按钮
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 编辑按钮
                TextButton(
                    onClick = {
                        isFollowed = !isFollowed
                        Toast.makeText(
                            context,
                            if (isFollowed) "已关注" else "已取消关注",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (isFollowed) "已关注" else "关注")
                }
                TextButton(
                    onClick = {
                        if (isFollowed) {
                            // connectUser(currentName,currentName, imageURL) { info ->
                            //     if (info.code == ZIMErrorCode.SUCCESS) {
                            //     } else {
                            //         Log.e("ChatActivity", "Connect user failed: ${info.message}, code: ${info.code}")
                            //     }
                            // }
                            // navController.navigate("main") // Removed navigation to main chat list screen
                            ZIMKitRouter.toMessageActivity(context, currentName, ZIMKitConversationType.ZIMKitConversationTypePeer)
                        } else {
                            Toast.makeText(context, "请先关注", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "发消息")
                }
            }
    }
}
