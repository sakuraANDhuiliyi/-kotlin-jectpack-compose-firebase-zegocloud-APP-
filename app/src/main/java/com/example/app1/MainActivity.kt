package com.example.app1

import com.example.app1.roomDb.viewModel.UserViewModel
import android.app.Activity
import android.app.AlarmManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app1.ui.theme.App1Theme
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.Room
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.app1.roomDb.LazycolumnDatabase
import com.example.app1.roomDb.viewModel.LazycolumnViewModel
import com.example.app1.roomDb.viewModel.Repository
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.LatLng
import com.example.app1.BiometricPromptManager.*
import com.example.app1.alarm.AlarmItem
import com.example.app1.alarm.AndroidAlarmScheduler
import com.example.app1.pages.TodoListPage
import com.example.app1.roomDb.Lazycolumn_1
import com.example.app1.roomDb.Lazycolumn_1Database
import com.example.app1.roomDb.viewModel.Lazycolumn_1Repository
import com.example.app1.roomDb.viewModel.Lazycolumn_1ViewModel
import com.example.app1.roomDb.viewModel.TodoViewModel
import com.example.app1.videoPlayer.JhVideoList
import com.example.app1.videoPlayer.Re0VideoList
import com.example.app1.videoPlayer.StreamerPlayer
import com.example.app1.videoPlayer.VideoPlayViewModel
import com.example.app1.videoPlayer.VideoPlayerViewModel
import com.example.app1.videoPlayer.YisiHougongVideoList
import com.example.app1.videoPlayer.mainVideoList
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.iflytek.sparkchain.core.LLM
import com.iflytek.sparkchain.core.LLMCallbacks
import com.iflytek.sparkchain.core.LLMConfig
import com.iflytek.sparkchain.core.LLMError
import com.iflytek.sparkchain.core.LLMEvent
import com.iflytek.sparkchain.core.LLMFactory
import com.iflytek.sparkchain.core.LLMResult
import com.iflytek.sparkchain.core.Memory
import com.iflytek.sparkchain.core.SparkChain
import com.iflytek.sparkchain.core.SparkChainConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.time.LocalDateTime
import java.io.IOException
import java.io.StringReader
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : AppCompatActivity(){
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            LazycolumnDatabase::class.java,
            name = "message.db"
        ).build()
    }
    private val viewModel by viewModels<LazycolumnViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LazycolumnViewModel(Repository(db))as T
                }
            }
        }
    )

    private val db_1 by lazy {
        Room.databaseBuilder(
            applicationContext,
            Lazycolumn_1Database::class.java,
            name = "Lazy message.db"
        ).build()
    }
    private val viewModel_1 by viewModels<Lazycolumn_1ViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return Lazycolumn_1ViewModel(Lazycolumn_1Repository(db_1))as T
                }
            }
        }
    )
    private val userViewModel by viewModels<UserViewModel>()
    private var mapView: MapView? = null
    //初始化讯飞星火大模型
    private var responseState = mutableStateOf("等待回答...")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = SparkChainConfig.builder()
            .appID("8c6b7b06")
            .apiKey("1e83b4dcb978bbb471aa62e6533b67ea")
            .apiSecret("YjMxY2NhYjRkNmNmOWRjYTU3NTk0M2E0")
        val ret = SparkChain.getInst().init(applicationContext, config)
        val chatLLMConfig = LLMConfig.builder()
            .domain("generalv3.5")
            .url("wss://spark-api.xf-yun.com/v3.5/chat")
            .maxToken(2048)
            .temperature(0.7f)
            .topK(4)
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        val memory = Memory.windowMemory(5)
        val chatLLM = LLMFactory.textGeneration(chatLLMConfig, memory)
        val llmCallbacks = object : LLMCallbacks {
            override fun onLLMResult(llmResult: LLMResult, usrContext: Any?) {
                responseState.value += llmResult.content
            }

            override fun onLLMEvent(event: LLMEvent, usrContext: Any?) {
                Log.d("LLMEvent", "Event ID: ${event.eventID}, Message: ${event.eventMsg}")
            }

            override fun onLLMError(error: LLMError, usrContext: Any?) {
                Log.e("LLMError", "Error Code: ${error.errCode}, Message: ${error.errMsg}")
            }
        }
        chatLLM.registerLLMCallbacks(llmCallbacks)
        enableEdgeToEdge()
        setContent {
            App1Theme {
                val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
                val authViewModel : AuthViewModel by viewModels()
                val navController = rememberNavController()
                val db = FirebaseFirestore.getInstance()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                var videoList by remember { mutableStateOf<List<VideoDescription>>(emptyList()) }
                LaunchedEffect(Unit) {
                    try {
                        val documents = db.collection("港剧")
                            .get()
                            .await()
                        val videos = documents.map { document ->
                            VideoDescription(
                                name = document.getString("name") ?: "",
                                type = document.getString("type") ?: "",
                                pic = document.getString("pic") ?: "",
                                lang = document.getString("lang") ?: "",
                                area = document.getString("area") ?: "",
                                year = document.getString("year") ?: "",
                                note = document.getString("note") ?: "",
                                actor = document.getString("actor") ?: "",
                                director = document.getString("director") ?: "",
                                videoUrl = document.get("videoUrl") as? List<String> ?: emptyList(),
                                dianzanCounts = document.getLong("dianzanCounts")?.toInt() ?: 0,
                                collectCounts = document.getLong("collectCounts")?.toInt() ?: 0
                            )
                        }
                        videoList = videos // 更新视频列表
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (currentBackStackEntry.value?.destination?.route != "splash"&&
                        currentBackStackEntry.value?.destination?.route != "login"&&
                        currentBackStackEntry.value?.destination?.route != "register"&&
                        currentBackStackEntry.value?.destination?.route != "selectLogin"&&
                        currentBackStackEntry.value?.destination?.route != "biometricAuth") {
                        ButtonNavigtion(navController)
                    } },
                ) {
                    innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "start") {
                            composable("start"){ StartScreen(navController) }
                            composable("thumbUpList"){ThumbUpList(navController)}
                            composable("collectList"){ CollectList(navController) }
                            composable("chaseList"){ ChaseList(navController) }
                            composable("person"){ PersonalProfilePage(navController) }
                            composable("videoPlay") { VideoScreen(navController = navController, videoList = videoList)}
                            composable("videoDescription/{videoName}"){//应该直接传递对象，viewModel
                                    backStackEntry ->
                                val videoName = backStackEntry.arguments?.getString("videoName")
                                // 使用名称查找视频详情
                                val videoItem = videoList.find { it.name == videoName }
                                if (videoItem != null) {
                                    VideoDescription(videoItem = videoItem, navController = navController)
                                }
                            }
                            composable("XiaoShuo"){ WebContentScreen(navController) }
                            composable("readChapter/{link}") { backStackEntry ->
                                val link = backStackEntry.arguments?.getString("link")
                                if (link != null) {
                                    val decodedLink = URLDecoder.decode(link, "UTF-8")
                                    ReadXiaoShuoChapter(link = decodedLink, navController = navController)
                                } else {
                                    // 处理 link 为空的情况，可以显示错误页面或默认值
                                    Text("链接无效或未找到")
                                }
                            }
                            composable("read/{link}"){
                                    backStackEntry ->
                                val link = backStackEntry.arguments?.getString("link")
                                if (link != null) {
                                    val decodedLink = URLDecoder.decode(link, "UTF-8")
                                    ReadXiaoShuo(link = decodedLink,navController)
                                } else {
                                    // 处理 link 为空的情况，可以显示错误页面或默认值
                                    Text("链接无效或未找到")
                                }
                            }
                            composable("videoSearch"){ SearchScreen(navController) }
                            composable("mainVideoScreen"){ MainVideoScreen(navController) }
                            composable("videoPlayScreen/{videoName}"){
                                    backStackEntry ->
                                val videoName = backStackEntry.arguments?.getString("videoName")
                                // 使用名称查找视频详情
                                val videoItem = videoList.find { it.name == videoName }
                                if (videoItem != null) {
                                    VideoPlayScreen(videoItem)
                                }
                            }//同理
                            composable("demo"){ VideoUrlListScreen() }
                            composable("jsoup") { demo1() }
                            composable("main") { ChatMainScreen() }
                            composable("video") { LoveVideo(navController = navController) }
                            composable("疑似后宫") { SteamingVideo() }
                            composable("Re0") { Re0SteamingVideo() }
                            composable("精幻") { JhSteamingVideo() }
                            composable("其他新番") { OtherSteamingVideo() }
                            composable("alarm") { alarm() }
                            composable("app") { OpenAppButton() }
                            composable("splash") { GifSplashScreen(navController = navController) }
                            composable("home") {
                                page(
                                    navController,
                                    viewModel_1,
                                    todoViewModel,
                                    authViewModel
                                )
                            }
                            composable("menu") { MenuScreen(navController = navController) }
                            composable("exam") { example(navController = navController) }
//                            composable("login") {
//                                LoginPage(
//                                    navController = navController,
//                                    authViewModel = authViewModel
//                                )
//                            }
//                            composable("signup") {
//                                SignupPage(
//                                    navController = navController,
//                                    authViewModel = authViewModel
//                                )
//                            }
                            composable("login"){ LoginDemo(navController = navController,userViewModel) }
                            composable("register"){ RegisterDemo(navController=navController,userViewModel) }
                            composable("image") { PictureDemo(navController, R.drawable.first) }
                            composable("AiBot") {
                                ChatApp(chatLLM, responseState)
                            }
                            composable("web") { WebBrowser(modifier = Modifier) }
                            composable("map") { MapScreen() }
                            composable("biometricAuth") {
                                BiometricAuthScreen(
                                    navController,
                                    this@MainActivity
                                )
                            }
                            composable("selectLogin") { selectLogin(navController) }
                        }

                      }
                    }

                }
            }
        }

    }


    data class TODoItem(
    var title:String,
    var isCompleted: Boolean = false
)
    data class TabItem(
        val title: String,
        val selectedIcon : ImageVector,
        val unSelectedIcon : ImageVector
    )
    data class Tab1Item(
       val title: String
    )
    data class Message(
        val author: String,
        val body: String,
        val imageURL: String,
        val fuiteName: String,
        val descriptionfuite:String
    )
    data class VideoDescription(
        val name:String="",
        val type:String="",
        val pic:String="",
        val lang:String="",
        val area:String="",
        val year:String="",
        val note:String="",
        val actor:String="",
        val director:String="",
        val videoUrl:List<String> = emptyList(),
        val dianzanCounts:Int=0,
        val collectCounts:Int=0
    )//添加计数：点赞数和收藏数
    data class Danmu(
    var time: Long = 0L,        // 初始化默认值，避免空指针异常
    var content: String = "",
    var videoName: String = "",
    var userId: String = "",
    var videoIndex: Int = 0
) {
    // 必须提供无参构造函数
    constructor() : this(0L, "", "", "", 0)
}//视频的名称，出现在视频的特定地点，发弹幕的用户名称，弹幕的内容；可以增加点赞数等功能
    //应该还需要添加一个用户对象，包含名称，密码，点赞收藏字段，字段值是一个数组，保存用户点赞和收藏的视频ID，头像，代办等
    data class XiaoShuo(
        val name: String,         // 小说名称
        val author: String,       // 作者名称
        val status: String,       // 小说状态 (如连载、完结)
        val description: String,  // 描述或简介
        val coverImageUrl: String, // 封面图片URL
        val link :String
    )//小说
    data class XiaoShuoChapter(
        val ChapterName :String,
        val link: String
    )
    data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val favoriteVideos: List<DocumentReference>? = null,
    val collectVideos: List<DocumentReference>? = null,
    val chaseVideos: List<DocumentReference>? = null
) {
    // 必须要有一个无参构造函数，供 Firestore 反序列化时使用
    constructor() : this("", "", "", "", "",null,null,null)
}
    data class Message1(val sender: String, val content: String)//Ai 聊天界面
    data class Comment(val username: String, val content: String, val avatar: String)//评论

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatApp(chatLLM: LLM, responseState: MutableState<String>) {
    var question by remember { mutableStateOf("") }
    val conversationList = remember { mutableStateListOf<Message1>() }
    val scrollState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("讯飞星火大模型") })
        },
        bottomBar = {
            // 输入栏在底部
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("输入你的问题") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (question.isNotEmpty()) {
                            // 添加新问题气泡
                            conversationList.add(Message1("你", question))
                            // 清空AI的响应状态
                            responseState.value = ""
                            // 发送问题给模型
                            val userQuestion = question
                            chatLLM.arun(userQuestion)
                            question = "" // 清空输入框
                            focusManager.clearFocus()
                        }
                    }
                ) {
                    Text("发送")
                }
            }
        }
    ) { innerPadding ->
        // 显示对话记录
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            reverseLayout = false // 最新对话显示在底部
        ) {
            // 遍历每个消息
            items(conversationList.size) { index ->
                ChatBubble(message = conversationList[index])
            }
        }

        // 当有新的 AI 回复时，更新当前对话的回复气泡内容
        LaunchedEffect(responseState.value) {
            if (responseState.value.isNotEmpty()) {
                val lastIndex = conversationList.lastIndex
                if (lastIndex >= 0 && conversationList[lastIndex].sender == "AI") {
                    // 更新 AI 的消息内容
                    conversationList[lastIndex] = conversationList[lastIndex].copy(content = responseState.value)
                } else {
                    // 添加新的 AI 消息
                    conversationList.add(Message1("AI", responseState.value))
                }
                // 自动滚动到最新消息
                scrollState.animateScrollToItem(conversationList.size - 1)
            }
        }
    }
}

    @Composable
    fun ChatBubble(message: Message1) {
    val isUser = message.sender == "你"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start // 用户的消息靠右，AI 的消息靠左
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isUser) Color(0xFFBBDEFB) else Color(0xFFD1E7DD),
                    RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(text = "${message.sender}: ${message.content}", color = Color.Black)
        }
    }
}
    @Composable
    fun VideoScreen(navController: NavHostController, videoList: List<VideoDescription>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(200.dp)
    ) {
        items(videoList.size) { index ->
            val video = videoList[index]
            Column(
                modifier = Modifier
                    .width(150.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    AsyncImage(
                        model = video.pic,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                val videoName = video.name
                                navController.navigate("videoDescription/${videoName}")
                            }
                    )
                }
                Text(
                    text = video.name,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp), // 添加顶部间距
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

    //视频简介页
    @Composable
    fun VideoDescription(videoItem: VideoDescription,navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // 添加整体的内边距
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = videoItem.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = videoItem.pic,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .wrapContentHeight()
                ) {
                    Text(text = "")
                    Text(text = "类型：${videoItem.type}")
                    Text(text = "地区：${videoItem.area}")
                    Text(text = "年份：${videoItem.year}")
                    Text(text = "语言：${videoItem.lang}")
                    Text(text = "导演：${videoItem.director}")
                    Text(text = "演员：${videoItem.actor}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = videoItem.note)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val videoName = videoItem.name
                            navController.navigate("videoPlayScreen/${videoName}")
                        },//点击后跳转到VideoPlayScreen
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        elevation = ButtonDefaults.buttonElevation(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "立即播放")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            // 这里可以添加评论、点赞、收藏、分享，相同类型的视频推荐等功能
            Text(text = "更多功能待实现")
        }
    }
}
    @Composable
    fun VideoPlayDescription(videoItem: VideoDescription,onPlayVideo: (String, Int) -> Unit){

    var clickState1 by remember { mutableStateOf(false) }
    var clickState2 by remember { mutableStateOf(false) }
    var clickState3 by remember { mutableStateOf(false) }
    var showDialog  by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var index  by remember { mutableIntStateOf(0) }
    val db = FirebaseFirestore.getInstance()
    val videoDocRef = db.collection(videoItem.type).document("${videoItem.name}_${videoItem.year}")
    var count1  by remember { mutableIntStateOf(videoItem.dianzanCounts) }//待存储,问题是会重复累计点赞，解决方案"likedUsers": ["userId1", "userId2"], "collectedUsers": ["userId3", "userId1"]
    var count2  by remember { mutableIntStateOf(videoItem.collectCounts) }
    val currentname = getLoggedInUsername(context =context)//bug:获取不到当前的用户
    LaunchedEffect(videoItem) {
            db.collection("videos").document("${videoItem.name}_${videoItem.year}").get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        count1 += document.getLong("dianzanCounts")?.toInt() ?: 0
                        count2 += document.getLong("collectCounts")?.toInt() ?: 0
                        Log.d("Firebase", "Dianzan count: $count1, Collect count: $count2")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", "Error getting data: ", exception)
                }
        }
    fun updateDianzanCount(newCount: Int) {
        db.collection(videoItem.type).document("${videoItem.name}_${videoItem.year}")
           .update("dianzanCounts", newCount)
           .addOnSuccessListener {
                  Log.d("Firebase", "Dianzan count updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error updating dianzan count", e)
                }
        }
    fun updateCollectCount(newCount: Int) {
            db.collection(videoItem.type).document("${videoItem.name}_${videoItem.year}")
                .update("collectCounts", newCount)
                .addOnSuccessListener {
                    Log.d("Firebase", "Collect count updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error updating collect count", e)
                }
        }
    fun updateFavoriteVideos(videoDocRef: DocumentReference) {
            val userDocRef = FirebaseFirestore.getInstance().collection("users").document(
               "Sakura"
            )
        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // 获取已收藏的视频列表 (DocumentReference 类型)
                val favoriteVideos = document.get("favoriteVideos") as? List<DocumentReference> ?: listOf()

                // 检查视频是否已存在于收藏列表中
                if (favoriteVideos.contains(videoDocRef)) {
                    // 如果已收藏，移除该视频
                    val updatedFavorites = favoriteVideos.filterNot { it == videoDocRef }

                    // 更新用户文档的 favoriteVideos 字段
                    userDocRef.update("favoriteVideos", updatedFavorites)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Video removed from favorites")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error removing favorite video", e)
                        }
                } else {
                    // 如果未收藏，添加该视频
                    val updatedFavorites = favoriteVideos + videoDocRef

                    // 更新用户文档的 favoriteVideos 字段
                    userDocRef.update("favoriteVideos", updatedFavorites)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Video added to favorites")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding favorite video", e)
                        }
                }
            }
        }.addOnFailureListener { e ->
                Log.w("Firebase", "Error fetching user data", e)
            }
        }
    fun updateCollectVideos(videoDocRef: DocumentReference) {
            val userDocRef = FirebaseFirestore.getInstance().collection("users").document(
                "Sakura"
            )
        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // 获取已收藏的视频列表 (DocumentReference 类型)
                val collectVideos = document.get("collectVideos") as? List<DocumentReference> ?: listOf()

                // 检查视频是否已存在于收藏列表中
                if (collectVideos.contains(videoDocRef)) {
                    // 如果已收藏，移除该视频
                    val updatedCollectVideos = collectVideos.filterNot { it == videoDocRef }

                    // 更新用户文档的 collectVideos 字段
                    userDocRef.update("collectVideos", updatedCollectVideos)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Video removed from collectVideos")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error removing collect video", e)
                        }
                } else {
                    // 如果未收藏，添加该视频
                    val updatedCollectVideos = collectVideos + videoDocRef

                    // 更新用户文档的 collectVideos 字段
                    userDocRef.update("collectVideos", updatedCollectVideos)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Video added to collectVideos")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding collect video", e)
                        }
                }
            }
        }.addOnFailureListener { e ->
            Log.w("Firebase", "Error fetching user data", e)
        }
        }
    fun updateChaseVideos(videoDocRef: DocumentReference) {
            val userDocRef = FirebaseFirestore.getInstance().collection("users").document(
                "Sakura"
            )
        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // 获取追剧列表 (DocumentReference 类型)
                val chaseVideos = document.get("chaseVideos") as? List<DocumentReference> ?: listOf()

                // 检查视频是否已存在于追剧列表中
                if (chaseVideos.contains(videoDocRef)) {
                    // 如果已追剧，移除该视频
                    val updatedChaseVideos = chaseVideos.filterNot { it == videoDocRef }

                    // 更新用户文档的 chaseVideos 字段
                    userDocRef.update("chaseVideos", updatedChaseVideos)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Video removed from chaseVideos")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error removing chase video", e)
                        }
                } else {
                    // 如果未追剧，添加该视频
                    val updatedChaseVideos = chaseVideos + videoDocRef

                    // 更新用户文档的 chaseVideos 字段
                    userDocRef.update("chaseVideos", updatedChaseVideos)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Video added to chaseVideos")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding chase video", e)
                        }
                }
            }
        }.addOnFailureListener { e ->
            Log.w("Firebase", "Error fetching user data", e)
        }
        }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    AsyncImage(
                        model = videoItem.pic,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = videoItem.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 30.sp
                    )
                    Row() {
                        Text(text = "地区：${videoItem.area}  ",fontSize = 10.sp)
                        Text(text = "类型：${videoItem.type}  ",fontSize = 10.sp)
                        Text(text = "年份：${videoItem.year}  ",fontSize = 10.sp)
                        Text(text = "语言：${videoItem.lang}  ",fontSize = 10.sp)
                    }
                    Text(text = videoItem.note)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {//bug：不确定是否点过赞，点击后更改按钮样式
                //徽章，用于展示点赞数
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    BadgedBox(badge = { Badge{
                        Text(count1.toString())
                    } }) {
                        IconButton(onClick = {
                            clickState1 = !clickState1
                            updateFavoriteVideos(videoDocRef)
                            if (clickState1){
                                count1 += 1
                            }else count1 -= 1
                            updateDianzanCount(count1)
                        }) {
                            if(clickState1)
                            Icon(Icons.Filled.ThumbUp,contentDescription = null)
                            else
                            Icon(Icons.Outlined.ThumbUp,contentDescription = null)
                        }
                    }
                    Text("点赞", fontSize = 15.sp)
                }
                //徽章，用于展示收藏数
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    BadgedBox(badge = {
                        Badge {
                            Text(count2.toString())
                        }
                    }) {
                        IconButton(onClick = {
                            clickState2 = !clickState2
                            updateCollectVideos(videoDocRef)
                            if (clickState2) {
                                count2 += 1
                            } else count2 -= 1
                            updateCollectCount(count2)
                        }) {
                            if(clickState2)
                                Icon(Icons.Filled.Star,contentDescription = null)
                            else
                                Icon(Icons.TwoTone.Star,contentDescription = null)
                        }
                    }
                    Text("收藏", fontSize = 15.sp)
                }
                //添加到播放列表（如追番列表等）
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        updateChaseVideos(videoDocRef)
                        clickState3 = !clickState3
                    }) {
                        if(clickState3)
                            Icon(Icons.Filled.Favorite,contentDescription = null)
                        else
                            Icon(Icons.Outlined.FavoriteBorder,contentDescription = null)
                    }
                    Text("追剧", fontSize = 15.sp)
                }
                //分享按钮，点击后自动生成链接和QQ或者微信
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Share, contentDescription = null)
                    }
                    Text("分享", fontSize = 15.sp)
                }

            }
            Spacer(modifier = Modifier.padding(10.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(videoItem.videoUrl.size) { item ->
                    Button(
                        onClick = {
                            onPlayVideo(videoItem.videoUrl[item], item)
                            index = item
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ) {
                        Text(text = "第${item + 1}集")
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        if (showDialog){
            Dialog(onDismissRequest = { showDialog = false}) {
                       Surface(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(vertical = 8.dp),
                           shape = RoundedCornerShape(8.dp),
                           color = MaterialTheme.colorScheme.surface,
                           shadowElevation = 4.dp
                       ) {
                           Column(){
                               AsyncImage(model = videoItem.pic, contentDescription = null,
                                   modifier = Modifier.fillMaxWidth())
                               Row(modifier = Modifier.fillMaxWidth()){
                                   TextButton(onClick = {
                                       showDialog = false
                                       clipboardManager.setText(AnnotatedString("https://www.50233.top/player/ec.php?code=qw&from=qq&if=1&url=${videoItem.videoUrl[index]}"))
                                       Toast.makeText(context, "已复制到剪贴板", Toast.LENGTH_SHORT).show()
                                   }) {
                                       Text("复制链接")
                                   }
                                   TextButton(onClick = {
                                       showDialog = false
                                       clipboardManager.setText(AnnotatedString(videoItem.videoUrl[index]))
                                       Toast.makeText(context, "已复制到剪贴板", Toast.LENGTH_SHORT).show()//逻辑打开QQ微信
                                   }) {
                                       Text("分享朋友")
                                   }
                               }
                           }
                       }
            }
        }
    }
}

//评论区
    @Composable
    fun CommentSection() {
    // 评论列表
    val comments = remember { mutableStateListOf<Comment>() }
    var commentText by remember { mutableStateOf(TextFieldValue()) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "评论区",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            if (comments.isEmpty()) {
                item {
                    Text(
                        text = "还没有评论，快来发表你的评论吧！",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

        // 输入框和发送按钮
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = commentText,
                onValueChange = { commentText = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (commentText.text.isEmpty()) {
                        Text(text = "输入评论...", color = Color.Gray, fontSize = 14.sp)
                    }
                    innerTextField()
                }
            )

            IconButton(onClick = {
                // 发送评论
                if (commentText.text.isNotBlank()) {
                    coroutineScope.launch {
                        comments.add(Comment(username = "用户", content = commentText.text, avatar =""))
                        commentText = TextFieldValue() // 清空输入框
                    }
                }
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "发送评论", tint = Color.Blue)
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        AsyncImage(
            model = comment.avatar,
            contentDescription = "用户头像",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = comment.username,
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = comment.content,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
    //播放视频
    @Composable
    fun VideoPlay(viewModel: VideoPlayViewModel,
              isPlaying : Boolean,
              onPlayClosed: (isVideoPlaying:Boolean) -> Unit){
         val context = LocalContext.current
        Box (modifier =Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (isPlaying) {
                    // 初始化播放器
                    DisposableEffect(Unit) {
                        viewModel.initializePlayer(context = context)
                        onDispose {
                            viewModel.releasePlayer()
                        }
                    }
                AndroidView(modifier = Modifier.fillMaxWidth(),
                    factory = { cont ->
                        viewModel.playerViewBuilder(cont)
                    })
                IconButton(onClick = {
                    onPlayClosed(false)

                    viewModel.releasePlayer()
                }, modifier = Modifier.align(Alignment.TopEnd)) {
                    Icon(imageVector = Icons.Default.Close,contentDescription = null
                        , tint = Color.White)
                }
            }
        }
    }

    //视频播放页
    @Composable
    fun VideoPlayScreen(videoItem: VideoDescription) {
        val tabItems = listOf(
            Tab1Item("详情"),
            Tab1Item("评论")
        )
        val db = FirebaseFirestore.getInstance()
        var videoIndex by remember { mutableIntStateOf(0) }
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        var showflag by remember { mutableStateOf(false) }
        val pagerState = rememberPagerState {
            tabItems.size
        }
        val viewModel : VideoPlayViewModel = viewModel()
        val currentTime = viewModel.getCurrentPlaybackTime()
        var danMuText by remember { mutableStateOf("") }
        val danmuList = remember { mutableStateListOf<Danmu>() }
        var isPlaying by remember { mutableStateOf(false) }
        LaunchedEffect(videoIndex) {
            db.collection("弹幕")
                .whereEqualTo("videoName", videoItem.name)
                .whereEqualTo("videoIndex", videoIndex)
                .get()
                .addOnSuccessListener { documents ->
                    danmuList.clear()
                    for (document in documents) {
                        val danmu = document.toObject(Danmu::class.java)
                        danmuList.add(danmu)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error getting danmu: ", e)
                }
        }
        LaunchedEffect(pagerState.currentPage) {
            selectedTabIndex = pagerState.currentPage
        }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }

        val context = LocalContext.current
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {
                    VideoPlay(viewModel,isPlaying, onPlayClosed ={isVideoPlaying->
                        isPlaying = isVideoPlaying
                    })
                }
                DisplayDanmu(danmuList = danmuList, currentTime = currentTime)
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabItems.forEachIndexed { index, item ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = { selectedTabIndex = index },
                            text = { Text(text = item.title) },
                        )
                    }
                    TextButton(onClick = {
                        showflag = true
                    }) {
                        Text(text = "点我发弹幕")//考虑用Box，然后给文字设定一个从右向左的移动动画
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                    }
                }
                HorizontalPager(state = pagerState) { index ->
                    when (index) {
                        0 -> VideoPlayDescription(videoItem = videoItem, onPlayVideo = { url,itemIndex ->
                            // 更新videoUrl
                            isPlaying = true
                            viewModel.url = url
                            videoIndex = itemIndex
                            //bug:点击关闭后视频还在后台播放
                            viewModel.apply {
                                releasePlayer()
                                initializePlayer(context)
                                playVideo()
                            }
                        }
                        )
                        1 -> CommentSection()
                    }
                }
            }
            if (showflag){
                Dialog(onDismissRequest = {showflag = false}) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxWidth()){
                            IconButton(onClick = { showflag = false}, modifier = Modifier.align(Alignment.End)) {
                                Icon(Icons.Default.Close,contentDescription = null)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            BasicTextField(
                                value = danMuText,
                                onValueChange = {danMuText = it},
                                modifier = Modifier
                                    .background(Color.White, CircleShape)
                                    .height(35.dp)
                                    .fillMaxWidth(),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    ) {
                                        IconButton(
                                            onClick = { }
                                        ) {
                                            Icon(Icons.Default.Edit, null)
                                        }
                                        Box(
                                            modifier = Modifier.weight(1f),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            innerTextField()
                                        }
                                        IconButton(
                                            onClick = {
                                                // 获取当前视频的播放时间
                                                Log.d("Danmu", "Current playback time: $currentTime,Index: $videoIndex")
                                                // 构建 Danmu 数据对象
                                                val danmu = Danmu(
                                                    time = currentTime,
                                                    content = danMuText,
                                                    videoName = videoItem.name,
                                                    userId = "",
                                                    videoIndex = videoIndex
                                                )

                                                // 将弹幕数据保存到 Firestore
                                                db.collection("弹幕")
                                                    .add(danmu)
                                                    .addOnSuccessListener {
                                                        Log.d("Firestore", "Danmu added successfully")
                                                    }
                                                    .addOnFailureListener { e ->
                                                        Log.w("Firestore", "Error adding danmu", e)
                                                    }
                                                // 重置弹幕输入框
                                                showflag = false
                                                danMuText = ""
                                            },
                                        ) {
                                            Icon(Icons.AutoMirrored.Filled.Send, null)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    //弹幕显示(bug:所有弹幕在同一时间出现，修改布局之后发现弹幕又显示不出来了)
    @Composable
    fun DisplayDanmu(
        danmuList: List<Danmu>,
        currentTime: Long, // 视频当前播放时间
        modifier: Modifier = Modifier
    ) {
        // 过滤在当前播放时间之前的弹幕
        val visibleDanmu = danmuList.filter { it.time <= currentTime }
        Box(modifier = modifier) {
            visibleDanmu.forEach { danmu ->
                // 弹幕文字的显示效果
                AnimatedDanmu(danmu.content)
            }
        }
    }
    //弹幕的动画效果
    @Composable
    fun AnimatedDanmu(content: String) {
        // 定义弹幕的动画效果，比如从右向左移动
        val offsetX = remember { Animatable(1000f) }
        LaunchedEffect(Unit) {
            offsetX.animateTo(
                targetValue = -300f,
                animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
            )
        }
        Text(
            text = content,
            modifier = Modifier
                .offset(x = offsetX.value.dp)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp),
            color = Color.White
        )
    }
    //视频播放首页
    @Composable
    fun MainVideoScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var videoList by remember { mutableStateOf<List<VideoDescription>>(emptyList()) }

    // 异步加载视频数据
    LaunchedEffect(Unit) {
        try {
            val documents = db.collection("港剧")
                .limit(5)
                .get()
                .await()
            val videos = documents.map { document ->
                VideoDescription(
                    name = document.getString("name") ?: "",
                    type = document.getString("type") ?: "",
                    pic = document.getString("pic") ?: "",
                    lang = document.getString("lang") ?: "",
                    area = document.getString("area") ?: "",
                    year = document.getString("year") ?: "",
                    note = document.getString("note") ?: "",
                    actor = document.getString("actor") ?: "",
                    director = document.getString("director") ?: "",
                    videoUrl = document.get("videoUrl") as? List<String> ?: emptyList(),
                    dianzanCounts = 0,
                    collectCounts = 0
                )
            }
            videoList = videos // 更新视频列表
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 显示视频列表
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
        ) {
            items(5) { index ->
                Text("Section ${index + 1}")
                VideoScreen(navController, videoList)
            }
        }
    }
}
    @Composable
    fun SearchScreen(navController: NavHostController) {
    var searchResults by remember { mutableStateOf<List<VideoDescription>>(emptyList()) }
    val db = FirebaseFirestore.getInstance()

    // 搜索框组件
    Column(modifier = Modifier.fillMaxSize()) {
        CheckName(onSearch = { queryText ->
            if (queryText.isNotEmpty()) {
                db.collection("港剧")
                    .orderBy("name")
                    .startAt(queryText)
                    .endAt(queryText + "\uf8ff") // 使用模糊搜索
                    .get()
                    .addOnSuccessListener { documents ->
                        val videos = documents.map { document ->
                            VideoDescription(
                                name = document.getString("name") ?: "",
                                type = document.getString("type") ?: "",
                                pic = document.getString("pic") ?: "",
                                lang = document.getString("lang") ?: "",
                                area = document.getString("area") ?: "",
                                year = document.getString("year") ?: "",
                                note = document.getString("note") ?: "",
                                actor = document.getString("actor") ?: "",
                                director = document.getString("director") ?: "",
                                videoUrl = document.get("videoUrl") as? List<String> ?: emptyList(),
                                dianzanCounts = 0,
                                collectCounts = 0
                            )
                        }
                        searchResults = videos // 更新搜索结果
                        Log.d("Firestore", "Found ${videos.size} videos")
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Firestore", "Error getting documents: ", exception)
                    }
            }
        })

        // 搜索结果展示
        LazyColumn {
            items(searchResults) { video ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    VideoDescription(video, navController)
                }
            }
        }
    }
}

    //示例
    fun fetchWebPageContent(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
    //获取小说详情
    fun extractArticles(html: String): List<XiaoShuo> {
    val document: Document = Jsoup.parse(html)
    val novels = mutableListOf<XiaoShuo>()

    // 查找所有包含小说信息的div（根据实际结构）
    val novelElements = document.select("div.col-12.col-md-6")

    // 遍历所有小说信息
    for (novelElement in novelElements) {
        // 解析封面图片
        val imgElement: Element? = novelElement.selectFirst("dt img[src]")
        val coverImageUrl: String = imgElement?.attr("src") ?: ""

        // 解析小说名称和分类
        val titleElement: Element? = novelElement.selectFirst("dd h3 a")
        val name: String = titleElement?.text() ?: ""

        // 解析作者
        val authorElement: Element? = novelElement.selectFirst("dd.book_other span")
        val author: String = authorElement?.text() ?: ""

        // 解析状态（如连载、完本）
        val statusElement: Element? = novelElement.select("dd.book_other").getOrNull(1)
        val status: String = statusElement?.text()?.replace("状态：", "") ?: ""

        // 解析最新章节链接和标题
        val latestChapterElement: Element? = novelElement.select("dd.book_other a").last()
        val latestChapterTitle: String = latestChapterElement?.text() ?: ""
        val latestChapterUrl: String = latestChapterElement?.attr("href") ?: ""
        // 解析小说详情链接
        val link: String = titleElement?.attr("href") ?: ""

        // 如果解析到了小说的名字和作者，添加到小说列表
        if (name.isNotEmpty() && author.isNotEmpty()) {
            novels.add(
                XiaoShuo(
                    name = name,
                    author = author,
                    status = status,
                    description = latestChapterTitle, // 将最新章节作为描述
                    coverImageUrl = "https://www.biquges.cc$coverImageUrl",
                    link = "https://www.biquges.cc$link"  // 解析到的小说详情链接
                )
            )
        }
    }

    return novels
}
    //获取小说详情分页情况
    fun extractPaginationLinks(html: String): List<String> {
        val document: Document = Jsoup.parse(html)
        val paginationLinks = mutableListOf<String>()

        // 查找分页的链接（具体结构根据页面）
        val pageElements = document.select("li.page-item a.page-link")

        for (pageElement in pageElements) {
            val href = pageElement.attr("href")
            if (href.isNotEmpty()) {
                paginationLinks.add("https://www.biquges.cc$href")
            }
        }

        return paginationLinks
    }

    //获取小说章节列表
    fun extractChapterList(html: String): List<XiaoShuoChapter> {
        val document: Document = Jsoup.parse(html)
        val chapters = mutableListOf<XiaoShuoChapter>()
        val chapterElements = document.select("ul.row li a")

        for (chapterElement in chapterElements) {
            val chapterName = chapterElement.text()
            val chapterLink = chapterElement.attr("href") // 提取 href 属性
            chapters.add(XiaoShuoChapter(chapterName, "https://www.biquges.cc$chapterLink"))
        }

        return chapters
    }
    //获取小说内容
    fun extractArticleContent(html: String): String? {
        val document: Document = Jsoup.parse(html)
        // 查找 <article class="font_max"> 标签内的内容
        val articleElement = document.selectFirst("article.font_max")
        return articleElement?.text()
    }

    //小说的搜索页面
    @Composable
    fun WebContentScreen(navController: NavHostController) {
        var novels by remember { mutableStateOf<List<XiaoShuo>>(emptyList()) }
        var url by remember { mutableStateOf("") }
        var currentPage by remember { mutableIntStateOf(1) }  // 当前页码
        var hasNextPage by remember { mutableStateOf(true) }  // 是否有下一页
        var isLoading by remember { mutableStateOf(false) }  // 是否正在加载
        val focusManager = LocalFocusManager.current

        Column(modifier = Modifier.fillMaxSize()) {
            // 搜索输入框和按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("搜索小说") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 1,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            currentPage = 1  // 重置页码为1
                            novels = emptyList()  // 清空现有的小说数据
                            isLoading = true  // 设置加载状态
                        }
                    )
                )
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        currentPage = 1  // 重置页码为1
                        novels = emptyList()  // 清空现有的小说数据
                        isLoading = true  // 设置加载状态
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("搜索")
                }
            }

            // 显示小说列表
            if (novels.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(novels) { novel ->
                        NovelCard(xiaoShuo = novel, navController = navController)
                    }

                    // 在底部显示 "加载下一页" 按钮
                    item {
                        if (hasNextPage && !isLoading) {
                            Button(
                                onClick = { isLoading = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("加载下一页")
                            }
                        } else if (isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = if (isLoading) "加载中..." else "暂无数据")
                }
            }
        }

        // 使用 LaunchedEffect 进行异步网络请求
        LaunchedEffect(isLoading) {
            if (isLoading && url.isNotEmpty()) {
                val baseUrl = "https://www.biquges.cc/search.php?q=$url&p=$currentPage"
                val html = fetchWebPageContentSuspend(baseUrl)

                if (html != null) {
                    // 提取当前页的小说内容
                    val newNovels = extractArticles(html)

                    // 判断是否有下一页
                    val paginationLinks = extractPaginationLinks(html)
                    hasNextPage = paginationLinks.isNotEmpty() && paginationLinks.any { it.contains("p=${currentPage + 1}") }

                    // 追加新的小说内容到现有列表
                    novels = novels + newNovels
                }

                // 加载完成后重置加载状态
                isLoading = false

                // 如果有下一页，增加当前页码
                if (hasNextPage) {
                    currentPage += 1
                }
            }
        }
    }


    //小说卡面
    @Composable
    fun NovelCard(xiaoShuo: XiaoShuo,navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                val encodedLink = URLEncoder.encode(xiaoShuo.link, "UTF-8")
                navController.navigate("readChapter/$encodedLink")
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = xiaoShuo.coverImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.nocover)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                // 小说名称
                Text(text = xiaoShuo.name, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(8.dp))

                // 作者
                Text(text = "作者: ${xiaoShuo.author}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(4.dp))

                // 状态
                Text(text = "状态: ${xiaoShuo.status}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                // 简介
                Text( text = xiaoShuo.description.ifEmpty { "暂无简介" }, style = MaterialTheme.typography.bodySmall, maxLines = 3, overflow = TextOverflow.Ellipsis)

            }
        }
    }
}


    // 提取当前的页码（从 URL 中提取出数字，如 "index_3.html" 提取出 3）
    fun extractCurrentPageNumber(link: String): Int {
        val regex = Regex("index_(\\d+)\\.html")
        val matchResult = regex.find(link)
        return matchResult?.groupValues?.get(1)?.toInt() ?: 1 // 默认第一页
    }

    // 构造新的链接（通过加减页码生成新的 URL）
    fun generatePageLink(baseLink: String, pageNumber: Int): String {
    return if (pageNumber > 1) {
        baseLink.replace(Regex("index_\\d+\\.html"), "index_$pageNumber.html")
    } else {
        baseLink.replace(Regex("index_\\d+\\.html"), "index.html")  // 第一页不带页码
    }
}
    //小说的章节列表页面（bug：点击按钮，不能更新页面内容）
    @Composable
    fun ReadXiaoShuoChapter(link: String, navController: NavHostController) {
        var chapters by remember { mutableStateOf<List<XiaoShuoChapter>>(emptyList()) }
        var currentLink by remember { mutableStateOf(link) }  // 当前章节页的链接
        var currentPage by remember { mutableIntStateOf(extractCurrentPageNumber(link)) }  // 当前的页码
        val listState = rememberLazyListState()  // 创建 LazyListState
        var isLoading by remember { mutableStateOf(true) }  // 加载状态，初始为 true

        // 使用 LaunchedEffect 重新获取内容，每次 currentLink 更新时触发
        LaunchedEffect(currentLink) {
            isLoading = true  // 开始加载
            // 使用 OkHttp 获取网页 HTML 内容
            val html = fetchWebPageContentSuspend(currentLink)
            // 如果 HTML 内容不为空，则使用 Jsoup 解析章节列表
            if (html != null) {
                chapters = extractChapterList(html)
            }
            isLoading = false  // 加载完成
            // 滚动到顶部
            listState.animateScrollToItem(0)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // 显示加载指示器
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()  // 使用进度指示器
                }
            } else {
                if (chapters.isNotEmpty()) {
                    LazyColumn(
                        state = listState,  // 将 LazyListState 应用到 LazyColumn
                        modifier = Modifier
                            .weight(1f)  // 使用 weight 确保 LazyColumn 占据剩余空间
                            .fillMaxSize()
                            .padding(16.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(chapters) { chapter ->
                            ChapterItem(chapter = chapter, navController = navController)
                        }
                    }
                } else {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "加载中或没有章节可显示")
                    }
                }
            }

            // 底部翻页按钮栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 上一页按钮
                Button(
                    onClick = {
                        if (currentPage > 1) {
                            // 减少页码，生成上一页的链接
                            currentLink = generatePageLink(currentLink, currentPage - 1)
                            currentPage--  // 更新当前页码
                        }
                    },
                    enabled = currentPage > 1  // 如果是第一页，禁用上一页按钮
                ) {
                    Text("上一页")
                }

                // 下一页按钮
                Button(
                    onClick = {
                        // 增加页码，生成下一页的链接
                        currentLink = generatePageLink(currentLink, currentPage + 1)
                        currentPage++  // 更新当前页码
                    },
                    enabled = true  // 一直启用下一页按钮
                ) {
                    Text("下一页")
                }
            }
        }
    }

    //小说阅读页
    @Composable
    fun ReadXiaoShuo(link: String, navController: NavHostController) {
        var contents by remember { mutableStateOf("") }  // 保存拼接的内容
        var currentLink by remember { mutableStateOf(link) }  // 当前章节链接
        val listState = rememberLazyListState()  // 用于控制 LazyColumn 的滚动状态

        LaunchedEffect(currentLink) {
            val allContents = StringBuilder()  // 使用 StringBuilder 拼接所有页面的内容
            // 如果链接不包含 "_1.html" 的形式，自动添加页码
            val baseLink = if (currentLink.endsWith(".html")) {
                currentLink.removeSuffix(".html")  // 移除 .html，方便后续拼接页码
            } else {
                currentLink  // 如果已经是带页码的形式，直接使用
            }
            val extension = ".html"  // HTML 文件扩展名

            // 循环请求 1 到 3 页的内容
            for (index in 1..3) {
                // 构造新的链接，添加页码
                val pageLink = baseLink + "_$index" + extension

                // 使用 OkHttp 请求每个页面的 HTML 内容
                val html = fetchWebPageContentSuspend(pageLink)

                // 如果成功获取 HTML 内容，解析并拼接到 allContents 中
                if (html != null) {
                    val articleContent = extractArticleContent(html)
                    if (articleContent != null) {
                        allContents.append(articleContent).append("\n\n")  // 拼接内容并换行
                    }
                }
            }

            // 将拼接后的所有内容更新到 UI
            contents = allContents.toString()

            // 在加载新章节后，滚动回到顶部
            listState.scrollToItem(0)
        }

        // 提取章节编号 (假设编号在 URL 的最后一个斜杠后面)
        val chapterNumber = extractChapterNumber(currentLink)

        // 显示拼接后的内容
        Column(modifier = Modifier.fillMaxSize()) {
            if (contents.isNotEmpty()) {
                LazyColumn(
                    state = listState,  // 绑定滚动状态
                    modifier = Modifier
                        .weight(1f)  // 使用 weight 确保 LazyColumn 占据剩余空间
                        .fillMaxSize()
                        .padding(16.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text(text = contents)
                    }
                }
            } else {
                // 如果内容为空，显示加载中的状态
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(text = "加载中...")
                }
            }

            // 底部按钮栏：上一章、目录、下一章
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),  // 添加一些外边距
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 上一章按钮
                Button(
                    onClick = {
                        val previousChapterLink = generatePreviousChapterLink(currentLink, chapterNumber)
                        if (previousChapterLink != null) {
                            currentLink = previousChapterLink  // 更新当前章节链接，触发重新加载
                        }
                    },
                    enabled = chapterNumber > 1  // 第一章禁用上一章按钮
                ) {
                    Text("上一章")
                }

                // 目录按钮 (可以跳转到目录页)
                Button(onClick = {
                    navController.navigateUp()  // 回到目录页
                }) {
                    Text("目录")
                }

                // 下一章按钮
                Button(
                    onClick = {
                        val nextChapterLink = generateNextChapterLink(currentLink, chapterNumber)
                        currentLink = nextChapterLink  // 更新当前章节链接，触发重新加载
                    }
                ) {
                    Text("下一章")
                }
            }
        }
    }

    // 提取当前章节编号
    fun extractChapterNumber(link: String): Int {
    val regex = """(\d+)(?:_\d+)?\.html$""".toRegex()  // 匹配章节编号部分
    val matchResult = regex.find(link)
    return matchResult?.groups?.get(1)?.value?.toInt() ?: 1  // 提取编号，默认第1章
}

    // 生成上一章的链接
    fun generatePreviousChapterLink(currentLink: String, chapterNumber: Int): String? {
    if (chapterNumber <= 1) return null  // 如果已经是第一章，返回null

    val previousChapterNumber = chapterNumber - 1
    return currentLink.replace("""\d+\.html$""".toRegex(), "$previousChapterNumber.html")
}

    // 生成下一章的链接
    fun generateNextChapterLink(currentLink: String, chapterNumber: Int): String {
    val nextChapterNumber = chapterNumber + 1
    return currentLink.replace("""\d+\.html$""".toRegex(), "$nextChapterNumber.html")
}

    //章节名
    @Composable
    fun ChapterItem(chapter: XiaoShuoChapter, navController: NavHostController) {
        // 章节项是可点击的，点击后跳转到相应的阅读页面
        Text(
            text = chapter.ChapterName,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val encodedLink = URLEncoder.encode(chapter.link, "UTF-8")
                    navController.navigate("read/$encodedLink") // 跳转到阅读页面
                }
                .padding(8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
    //放在IO协程进行，防止堵塞主线程
    suspend fun fetchWebPageContentSuspend(url: String): String? {
    return withContext(Dispatchers.IO) {
        fetchWebPageContent(url)
    }
}

    @Composable
    fun ChatMainScreen(){
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            val intent = Intent(context, ChatActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("开始聊天")
        }
    }
}
    @Composable
    fun demo1() {
    var responseData by remember { mutableStateOf("Loading...") }
    val scrollState = rememberScrollState() // 滚动状态
    val client = OkHttpClient()

    LaunchedEffect(Unit) {
        val request = Request.Builder()
            .url("https://kuaichezy.com/index.php/vod/detail/id/94056.html")
            .build()

        try {
            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
            responseData = response.body?.string() ?: "No Response"
        } catch (e: Exception) {
            responseData = "Error: ${e.message}"
        }
    }

    // 使用 Column 和 verticalScroll 使文本可滚动
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState) // 使文本可滚动
    ) {
        Text(text = responseData)
    }
}
    @Composable
    fun VideoUrlListScreen() {
    var videoDescriptions by remember { mutableStateOf(listOf<VideoDescription>()) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            videoDescriptions = fetchAndParseVideoDescriptions()
        }
    }

    LazyColumn {
        items(videoDescriptions) { videoDescription ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = videoDescription.name)
                videoDescription.videoUrl.forEach { url ->
                    Text(text = "Video URL: $url")
                }
                // Add more details as needed
                // Display the image if needed
                // Image(painter = rememberImagePainter(videoDescription.pic), contentDescription = null)
            }
        }
    }
}

    fun fetchAndParseVideoDescriptions(): List<VideoDescription> {
    val client = OkHttpClient()
    val descriptions = mutableListOf<VideoDescription>()

    for (num in 12001..14000) {//https://suoniapi.com/api.php/provide/vod/from/snm3u8/at/xml/?ac=videolist&ids=
        val url = "https://suoniapi.com/api.php/provide/vod/from/snm3u8/at/xml/?ac=videolist&ids=$num"//
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }

            val xml = response.body?.string()
            if (xml != null) {
                val videoDescription = parseVideoDescription(xml)
                if (videoDescription != null) {
                    descriptions.add(videoDescription)
                }
            }
        }
    }
    return descriptions
}

    fun parseVideoDescription(xml: String): VideoDescription? {
    var name = ""
    var type = ""
    var pic = ""
    var lang = ""
    var area = ""
    var year = ""
    var note = ""
    var actor = ""
    var director = ""
    val videoUrls = mutableListOf<String>()
    var dianzanCounts=0
    var collectCounts =0
    val db = Firebase.firestore

    try {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(StringReader(xml))

        var eventType = parser.eventType
        var isVideoNode = false
        var isDlNode = false

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val nodeName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (nodeName == "video") {
                        isVideoNode = true
                    } else if (isVideoNode) {
                        when (nodeName) {
                            "name" -> name = parser.nextText()
                            "type" -> type = parser.nextText()
                            "pic" -> pic = parser.nextText()
                            "lang" -> lang = parser.nextText()
                            "area" -> area = parser.nextText()
                            "year" -> year = parser.nextText()
                            "note" -> note = parser.nextText()
                            "actor" -> actor = parser.nextText()
                            "director" -> director = parser.nextText()
                        }
                    }
                    if (isVideoNode && nodeName == "dl") {
                        isDlNode = true
                    }
                }
                XmlPullParser.TEXT -> {
                    if (isDlNode) {
                        // 获取视频地址并分割
                        val content = parser.text
                        val parts = content.split("#")
                        for (part in parts) {
                            val urlPart = part.split("$")
                            if (urlPart.size > 1) {
                                videoUrls.add(urlPart[1]) // 提取视频 URL
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (nodeName == "dl") {
                        isDlNode = false
                    }
                    if (nodeName == "video") {
                        isVideoNode = false
                        val video = hashMapOf(
                            "name" to name,
                            "type" to type,
                            "pic" to pic,
                            "lang" to lang,
                            "area" to area,
                            "year" to year,
                            "note" to note,
                            "actor" to actor,
                            "director" to director,
                            "videoUrl" to videoUrls,
                            "dianzanCounts" to 0,
                            "collectCounts" to 0
                        )

                        val safeVideoName = "${name.replace("[#\\[\\]]".toRegex(), "")}_$year" // 例如："电影名_2024"
                        db.collection(type)
                            .document(safeVideoName) // 使用 safeVideoName 作为文档 ID
                            .set(video)
                            .addOnSuccessListener { documentReference ->
                                Log.d("DocSnippets", "DocumentSnapshot added with ID: ${documentReference}")
                            }
                            .addOnFailureListener { e ->
                                Log.w("DocSnippets", "Error adding document", e)
                            }
                        return VideoDescription(name, type, pic, lang, area, year, note, actor, director, videoUrls,dianzanCounts, collectCounts )
                    }
                }
            }
            eventType = parser.next()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
    @Composable
    fun OpenAppButton() {
        val context = LocalContext.current
        Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center )  {
            Button(onClick = {
                val searchIntent = Intent(Intent.ACTION_WEB_SEARCH)
                context.startActivity(searchIntent)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Open Google Search")
            }
            Button(onClick = {
                try {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.component = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                } catch (e: Exception) {

                    val appStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tencent.mm"))
                    context.startActivity(appStoreIntent)
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Open WeChat App")
            }
            Button(onClick = {
                try {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.component = ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.SplashActivity")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                } catch (e: Exception) {
                    val appStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tencent.mobileqq"))
                    context.startActivity(appStoreIntent)
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Open QQ App")
            }

        }
    }

    @Composable
    fun alarm(){
        var secondsText by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        val scheduler = AndroidAlarmScheduler(LocalContext.current)
        var alarmItem : AlarmItem?=null
        checkAndRequestExactAlarmPermission(LocalContext.current)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
                verticalArrangement = Arrangement.Center ) {
            OutlinedTextField(
                value = secondsText,
                onValueChange = {secondsText=it},
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "设置时间")
                }
            )
            OutlinedTextField(
                value = message,
                onValueChange = {message=it},
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "设置message")
                }
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                        alarmItem= AlarmItem(
                            time = LocalDateTime.now().plusSeconds(secondsText.toLong()),
                            message = message
                        )
                    alarmItem?.let(scheduler::schedule)
                    secondsText =""
                    message = ""
            }){ Text(text = "设置闹钟")
                }
                Button(onClick = {
                    alarmItem?.let(scheduler::cancel)
                }){ Text(text = "取消")
                }
            }

            }
        }

    fun checkAndRequestExactAlarmPermission(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
             if (!alarmManager.canScheduleExactAlarms()) {
                 val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                  context.startActivity(intent)
                 Toast.makeText(context, "Please allow exact alarms in settings.", Toast.LENGTH_LONG).show()
        }
    }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = android.Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(context, notificationPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(notificationPermission), 100)
            }
        }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MessageCard1(msg:Lazycolumn_1){
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
        var showBottomSheet by remember { mutableStateOf(false) }
        var openDialog by remember { mutableStateOf(false) }
        var isExpanded by remember { mutableStateOf(false) }
        var textdemo by remember { mutableStateOf("") }
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }



        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                var scale by remember { mutableFloatStateOf(1f)}
                var offset by remember { mutableStateOf(Offset.Zero) }
                var rotation by remember { mutableFloatStateOf(1f) }
                val clipboardManager: ClipboardManager = LocalClipboardManager.current
                var showMenu by remember { mutableStateOf(false) }
                var showMenu1 by remember { mutableStateOf(false) }
                var menuPosition by remember { mutableStateOf(Offset.Zero) }
                var selectedText by remember { mutableStateOf("") }
                val density = LocalDensity.current
                val dpMenuPosition = with(density) { DpOffset(menuPosition.x.toDp(), menuPosition.y.toDp()) }
                val context = LocalContext.current

                Card(shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    elevation = CardDefaults.cardElevation(10.dp)){
                    Box {
                        SelectionContainer{
                            Text(text = "You clicked the image of ${msg.body}: ", style = TextStyle(fontSize = 20.sp),
                                modifier = Modifier.pointerInput(Unit){
                                    detectTapGestures(
                                        onLongPress = {
                                            showMenu = true
                                            menuPosition=it
                                            selectedText = msg.body
                                        }
                                    )
                                })
                        }
                            DropdownMenu(showMenu, onDismissRequest = {showMenu = false}, offset = dpMenuPosition) {
                                    DropdownMenuItem(text = { Text("复制") }, onClick = {clipboardManager.setText(
                                        AnnotatedString(selectedText)
                                    )
                                        showMenu = false})
                                    DropdownMenuItem(text = { Text("分享") }, onClick = {
                                        showMenu = false
                                        try {
                                            val intent = Intent(Intent.ACTION_MAIN)
                                            intent.addCategory(Intent.CATEGORY_LAUNCHER)
                                            intent.component = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                            context.startActivity(intent)
                                        } catch (e: Exception) {

                                            val appStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tencent.mm"))
                                            context.startActivity(appStoreIntent)
                                        }
                                })
                            }

                    }
                    BoxWithConstraints(modifier = Modifier
                        .fillMaxWidth()
                        , contentAlignment = Alignment.Center) {
                        val state =
                            rememberTransformableState { zoomChange, panChange, rotationChange ->
                                scale = (scale * zoomChange).coerceIn(1f, 5f)
                                //rotation += rotationChange
                                val extraWidth = (scale - 1) * constraints.maxWidth
                                val extraHeight = (scale - 1) * constraints.maxHeight
                                val maxx = extraWidth / 2
                                val maxy = extraHeight / 2
                                offset = Offset(
                                    x = (offset.x + scale * panChange.x).coerceIn(-maxx, maxx),
                                    y = (offset.y + scale * panChange.y).coerceIn(-maxy, maxy)
                                )

                            }

                        AsyncImage(model = msg.imageURL,contentDescription = null
                            , modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .border(
                                    1.5.dp,
                                    MaterialTheme.colorScheme.secondary,
                                    shape = CircleShape
                                )
                                .fillMaxWidth()
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    translationX = offset.x
                                    translationY = offset.y
                                }
                                .transformable(state)
                                )
                    }
                    Box {
                        SelectionContainer{
                            Text(text = msg.descriptionfuite, style = TextStyle(fontSize = 20.sp),
                                modifier = Modifier.pointerInput(Unit){
                                    detectTapGestures(
                                        onLongPress = {
                                            showMenu1 = true
                                            menuPosition=it
                                            selectedText = msg.descriptionfuite
                                        }
                                    )
                                })
                        }
                        DropdownMenu(showMenu1, onDismissRequest = {showMenu1 = false}, offset = dpMenuPosition) {
                            DropdownMenuItem(text = { Text("复制") }, onClick = {clipboardManager.setText(
                                AnnotatedString(selectedText)
                            )
                                showMenu1 = false})
                            DropdownMenuItem(text = { Text("分享") }, onClick = {
                                showMenu1 = false
                                try {
                                    val intent = Intent(Intent.ACTION_MAIN)
                                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                                    intent.component = ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.SplashActivity")
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    val appStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tencent.mobileqq"))
                                    context.startActivity(appStoreIntent)
                                }
                            })
                        }

                    }

                }

            }
        }
        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },

                text = {
                    Text(text = textdemo)
                },

                confirmButton = {
                    Button(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text("取消")
                    }
                }
            )
        }
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxSize()
                .height(150.dp)
                .clickable {
                    isExpanded = !isExpanded
                    openDialog = true
                    textdemo = msg.body
                }
                .padding(horizontal = 10.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxSize()
            ) {
                AsyncImage(model =msg.imageURL,contentDescription = null
                    , modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.secondary, shape = CircleShape)
                        .clickable {
                            showBottomSheet = true
                            textdemo = "You clicked the image of ${msg.author}"
                        })
                Row {
                    Text(
                        text =msg.author,
                        modifier = Modifier
                            .clickable(onClick = {
                                isExpanded = !isExpanded
                                openDialog = true
                                textdemo = msg.author
                            })
                            .size(150.dp)
                            .padding(45.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = msg.body,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        modifier = Modifier
                            .animateContentSize()
                            .clickable(onClick = {
                                openDialog = true
                                textdemo = msg.body
                            })
                            .padding(vertical = 45.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Composable
    fun LazyMessCard1(viewModel: Lazycolumn_1ViewModel,searchText: String){
    var author by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var imageURL by remember { mutableStateOf("") }
    var fuiteName by remember { mutableStateOf("") }
    var descriptionfuite by remember { mutableStateOf("") }
    val lazyMessages =Lazycolumn_1(author,body,imageURL, fuiteName, descriptionfuite)
    var lazyMessageList by remember {
        mutableStateOf(listOf<Lazycolumn_1>())
    }
    viewModel.getMessages().observe(LocalLifecycleOwner.current){
        lazyMessageList=it
    }
    var showDialog by remember { mutableStateOf(false) }
    val filteredMessages = lazyMessageList.filter {messages ->
        messages.author.contains(searchText, ignoreCase = true) || messages.body.contains(
            searchText,
            ignoreCase = true
        )
    }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope= rememberCoroutineScope()

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
        val context = LocalContext.current
        val singleImagePckerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
                uri ->
            if (uri != null) {
                selectedImageUri = uri
                val savedImagePath = saveImageToInternalStorage(context, uri)
                if (savedImagePath != null) {
                    imageURL = savedImagePath
                }
            }

        },
    )

        Box(modifier = Modifier.fillMaxSize()) {
        PullToRefreshLazyColumn(items = filteredMessages,
            content = {
                    lazyMessages ->
                SwipeToDeleteContainer(item = lazyMessages,
                    onDelete ={viewModel.deleteMessages(lazyMessages)}) {
                    MessageCard1(lazyMessages)
                }
            },
            isRefreshing=isRefreshing,
            onRefreshing = {
                scope.launch {
                    isRefreshing=true
                    delay(2000)
                    isRefreshing=false
                }
            }

        )
        SmallFloatingActionButton(
            onClick = {
                showDialog = true
            },
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
        }
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false }
            ) {
                Box(
                    modifier = Modifier
                        .size(450.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center) {

                        Text(text="请输入要输入的内容")
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        val focusManager = LocalFocusManager.current
                        TextField(
                            value = author,
                            maxLines = 1,
                            onValueChange = { author = it },
                            placeholder = {
                                Text("姓名")
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            ,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            )
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        TextField(
                            value = body,
                            maxLines = 1,
                            onValueChange = { body = it },
                            placeholder = {
                                Text("信息")
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            ,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                })
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        TextField(
                            value = imageURL,
                            maxLines = 1,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        singleImagePckerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                ) {
                                    Icon(imageVector = Icons.Outlined.Add, null)
                                }
                            },
                            onValueChange = { imageURL = it },

                            placeholder = {
                                Text("上传图片")
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            ,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                })
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        TextField(
                            value = fuiteName,
                            maxLines = 1,
                            onValueChange = { fuiteName = it },
                            placeholder = {
                                Text("外号")
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            ,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                })
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        TextField(
                            value = descriptionfuite,
                            maxLines = 1,
                            onValueChange = { descriptionfuite = it },
                            placeholder = {
                                Text("爱好")
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            ,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                })
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        Button(
                            onClick = {
                                viewModel.upsertMessages(lazyMessages)
                                showDialog = false
                                author=""
                                body = ""
                                imageURL =""
                                fuiteName = ""
                                descriptionfuite = ""
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun page(navController: NavHostController,viewModel: Lazycolumn_1ViewModel,todoViewModel: TodoViewModel,authViewModel: AuthViewModel){

        val tabItems = listOf(
            TabItem(
                title = "首页",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home
            ),
            TabItem(
                title = "推荐",
                selectedIcon = Icons.Filled.Favorite,
                unSelectedIcon = Icons.Outlined.FavoriteBorder
            ),
            TabItem(
                title = "点赞过",
                selectedIcon = Icons.Filled.ThumbUp,
                unSelectedIcon = Icons.Outlined.ThumbUp
            ) ,
            TabItem(
                title = "影视",
                selectedIcon = Icons.Filled.PlayArrow,
                unSelectedIcon = Icons.Outlined.PlayArrow
            ),
            TabItem(
                title = "小说",
                selectedIcon = Icons.Filled.Email,
                unSelectedIcon = Icons.Outlined.Email
            ),
            TabItem(
                title = "好看的",
                selectedIcon = Icons.Filled.Person,
                unSelectedIcon = Icons.Outlined.Person
            )

        )
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        val pagerState = rememberPagerState{
            tabItems.size
        }
//        val authState = authViewModel.authState.observeAsState()
//        LaunchedEffect(authState.value) {
//            when(authState.value){
//                is AuthState.Unauthenticated -> navController.navigate("login")
//                else ->Unit
//            }
//        }
        LaunchedEffect(pagerState.currentPage) {
            selectedTabIndex = pagerState.currentPage
        }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress){
                selectedTabIndex = pagerState.currentPage
            }
        }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var searchText by remember { mutableStateOf("") }
        val context = LocalContext.current
        val isGuest = isGuestUser(context)
        val guestName = getUserId(context)
        var imageURL by remember { mutableStateOf(if(!isGuest){
            getImagePath(context) ?: "\"https://truth.bahamut.com.tw/s01/201804/b34f037ab8301d4cd1331f686405b97a.JPG\""
        }else{
            "https://c-ssl.duitang.com/uploads/item/201803/19/20180319200326_3HvLA.jpeg"
        }) }
        var textmode by remember { mutableStateOf(if(!isGuest){
            loadUserName(context) ?: "默认用户"
        }else{
            "访客$guestName"
        }) }
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val singleImagePckerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                    uri ->
                if (uri != null) {
                    selectedImageUri = uri
                    val savedImagePath = saveImageToInternalStorage(context, uri)
                    if (savedImagePath != null) {
                        imageURL = savedImagePath
                        saveImagePath(context, savedImagePath)
                        saveUserName(context, textmode)
                    }
                }

            },
        )
        var showdialog1 by remember { mutableStateOf(false) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        var showdialog by remember { mutableStateOf(false) }
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.Cyan)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Row(modifier = Modifier.fillMaxSize()) {
                                        Column {
                                            AsyncImage(
                                                model = imageURL,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .clip(CircleShape)
                                                    .border(
                                                        1.5.dp,
                                                        MaterialTheme.colorScheme.secondary,
                                                        shape = CircleShape
                                                    )
                                            )
                                        }
                                        Column(modifier = Modifier.fillMaxWidth(1f)) {
                                            IconButton(
                                                onClick =
                                                {
                                                    singleImagePckerLauncher.launch(
                                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                    )
                                                }, modifier = Modifier
                                                    .size(30.dp)
                                                    .align(Alignment.End)
                                            ) {
                                                Icon(Icons.Default.Edit, contentDescription = null)
                                            }
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.Cyan)
                                    .padding(vertical = 5.dp)
                            ) {
                                Row {
                                    Text(text = textmode, modifier = Modifier
                                        .clickable {
                                            showdialog = true
                                        }
                                        .padding(horizontal = 20.dp), fontSize = 20.sp)
                                    IconButton(
                                        onClick = {
                                            showdialog = true
                                        }, modifier = Modifier
                                            .size(20.dp)
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = null)
                                    }
                                }
                                Spacer(modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .background(Color.Cyan))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column {
                                        Text(text = "永远相信美好的事情即将发生", modifier = Modifier
                                            .clickable {
                                            }, fontSize = 10.sp
                                        )
                                    }
                                    Column(modifier = Modifier.fillMaxWidth(1f)) {
                                        TextButton(onClick = {
                                            showdialog1 = true
                                            clearLoginInfo(context)
                                            navController.navigate("login")
                                        }, modifier = Modifier.align(Alignment.End)) {
                                            Text(text = "退出登录")
                                        }
                                    }

                                }


                            }
                            Column(modifier = Modifier.fillMaxWidth()) {
                                TodoListPage(todoViewModel)
                            }
                        }
                        if(showdialog){
                            Dialog(onDismissRequest = {
                                showdialog = false
                            }) {
                                Column(verticalArrangement = Arrangement.Center) {
                                    TextField(
                                        value = textmode,
                                        maxLines = 1,
                                        onValueChange = { textmode = it },
                                        placeholder = {
                                            Text("输入您的用户名")
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                        ,
                                    )
                                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                                    Button(
                                        onClick = {
                                            saveUserName(context, textmode)
                                            showdialog = false
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    ) {
                                        Text("确认")
                                    }
                                }
                            }
                        }
                        if (showdialog1){
                            BasicAlertDialog(onDismissRequest = {showdialog1 = false}) {
                                Column(modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally ) {
                                    Text("你已经被强制下线")
                                    Button(onClick = {
                                        showdialog1 = false
                                    }) {
                                        Text("确认")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                                Check(navController)
                                },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                AsyncImage(
                                    model = imageURL,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .border(
                                            1.5.dp,
                                            MaterialTheme.colorScheme.secondary,
                                            shape = CircleShape
                                        )

                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)) {
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        tabItems.forEachIndexed { index, item ->
                            Tab(
                                selected = index == selectedTabIndex,
                                onClick = { selectedTabIndex = index },
                                text = { Text(text = item.title) },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedTabIndex) item.selectedIcon else item.unSelectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            )
                        }
                    }

                    HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
                        when (index) {
                            0 -> MainVideoScreen(navController)
                            1 -> LoveVideo(navController)
                            2 -> Text("")
                            3 -> Text("")
                            4 -> WebContentScreen(navController)
                            5 -> Text("")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BiometricAuthScreen(navController: NavHostController, activity: AppCompatActivity) {
        val promptManager = remember { BiometricPromptManager(activity) }

        val biometricResult by promptManager.promptResult.collectAsState(initial = null)
        var hasShownToast by remember { mutableStateOf(false) }
        val enrollLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                println("结果: $it")
            }
        )

        LaunchedEffect(biometricResult) {
            if (biometricResult is BiometricResult.AuthenticationNotSet) {
                if (Build.VERSION.SDK_INT >= 30) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    enrollLauncher.launch(enrollIntent)
                }
            } else if (biometricResult is BiometricResult.AuthenticationSuccess) {
                navController.navigate("home")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                promptManager.showBiomertricPrompy(
                    title = "登录",
                    description = "登录"
                )
            }) {
                Text(text = "点击登录")
            }

            biometricResult?.let { result ->
                if ( !hasShownToast) {
                    when (result) {
                        is BiometricResult.AuthenticationError -> result.error
                        BiometricResult.AuthenticationFailed -> Toast.makeText(
                            LocalContext.current,
                            "登陆失败",
                            Toast.LENGTH_SHORT
                        ).show()

                        BiometricResult.AuthenticationNotSet -> Toast.makeText(
                            LocalContext.current,
                            "还没设置生物密钥",
                            Toast.LENGTH_SHORT
                        ).show()

                        BiometricResult.AuthenticationSuccess -> Toast.makeText(
                            LocalContext.current,
                            "登录成功",
                            Toast.LENGTH_SHORT
                        ).show()

                        BiometricResult.FeatureUnavailable -> Toast.makeText(
                            LocalContext.current,
                            "显示失败",
                            Toast.LENGTH_SHORT
                        ).show()

                        BiometricResult.HardwareUnavailable -> Toast.makeText(
                            LocalContext.current,
                            "改设备不支持生物密钥登录",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    hasShownToast = true
                }
            }
        }
    }

    //个人简介页
    @Composable
    fun PersonalProfilePage(navController: NavHostController) {
        val context = LocalContext.current
        val isGuest = isGuestUser(context)
        val guestName = getUserId(context)
        var imageURL by remember { mutableStateOf(if(!isGuest){
            getImagePath(context) ?: "\"https://truth.bahamut.com.tw/s01/201804/b34f037ab8301d4cd1331f686405b97a.JPG\""
        }else{
            "https://c-ssl.duitang.com/uploads/item/201803/19/20180319200326_3HvLA.jpeg"
        }) }
        var textmode by remember { mutableStateOf(if(!isGuest){
            loadUserName(context) ?: "默认用户"
        }else{
            "访客$guestName"
        }) }
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val singleImagePckerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                    uri ->
                if (uri != null) {
                    selectedImageUri = uri
                    val savedImagePath = saveImageToInternalStorage(context, uri)
                    if (savedImagePath != null) {
                        imageURL = savedImagePath
                        saveImagePath(context, savedImagePath)
                        saveUserName(context, textmode)
                    }
                }

            },
        )
    // 背景渐变色
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )
        Column(modifier = Modifier.fillMaxSize().background(gradientBackground), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            var showdialog by remember { mutableStateOf(false) }
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Cyan)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            Column {
                                AsyncImage(
                                    model = imageURL,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                        .border(
                                            1.5.dp,
                                            MaterialTheme.colorScheme.secondary,
                                            shape = CircleShape
                                        )
                                )
                            }
                            Column(modifier = Modifier.fillMaxWidth(1f)) {
                                IconButton(
                                    onClick =
                                    {
                                        singleImagePckerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }, modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.End)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Cyan)
                        .padding(vertical = 5.dp)
                ) {
                    Row {
                        Text(text = textmode, modifier = Modifier
                            .clickable {
                                showdialog = true
                            }
                            .padding(horizontal = 20.dp), fontSize = 20.sp)
                        IconButton(
                            onClick = {
                                showdialog = true
                            }, modifier = Modifier
                                .size(20.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                    }
                    Spacer(modifier = Modifier
                        .padding(vertical = 5.dp)
                        .background(Color.Cyan))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text(text = "永远相信美好的事情即将发生", modifier = Modifier
                                .clickable {
                                }, fontSize = 10.sp
                            )
                        }
                        Column(modifier = Modifier.fillMaxWidth(1f)) {
                            TextButton(onClick = {
                                clearLoginInfo(context)
                                navController.navigate("login")
                            }, modifier = Modifier.align(Alignment.End)) {
                                Text(text = "退出登录")
                            }
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
                        Column(modifier = Modifier.fillMaxHeight().weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                navController.navigate("thumbUpList")
                            }) {
                                Icon(Icons.Default.ThumbUp, contentDescription = null)
                            }
                            Text("我的点赞")
                        }
                        Column(modifier = Modifier.fillMaxHeight().weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                navController.navigate("collectList")
                            }) {
                                Icon(Icons.Default.Star, contentDescription = null)
                            }

                            Text("我的收藏")
                        }
                        Column(modifier = Modifier.fillMaxHeight().weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                navController.navigate("chaseList")
                            }) {
                                Icon(Icons.Default.Favorite, contentDescription = null)
                            }
                            Text("我的追番")
                        }
                        Column(modifier = Modifier.fillMaxHeight().weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                navController.navigate("")
                            }) {
                                Icon(Icons.Default.Share, contentDescription = null)
                            }
                            Text("我的分享")
                        }
                    }
                }
            }
            if(showdialog){
                Dialog(onDismissRequest = {
                    showdialog = false
                }) {
                    Column(verticalArrangement = Arrangement.Center) {
                        TextField(
                            value = textmode,
                            maxLines = 1,
                            onValueChange = { textmode = it },
                            placeholder = {
                                Text("输入您的用户名")
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            ,
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        Button(
                            onClick = {
                                saveUserName(context, textmode)
                                showdialog = false
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("确认")
                        }
                    }
                }
            }
        }
    }
    //点赞列表逻辑
    @Composable
    fun ThumbUpList(navController: NavHostController) {
        val context = LocalContext.current
        var favoriteVideos by remember { mutableStateOf<List<VideoDescription>>(emptyList()) }
        // 使用 LaunchedEffect 来处理异步调用
        LaunchedEffect(Unit) {
            getFavoriteVideos(context) { videos ->
                favoriteVideos = videos // 更新状态，触发 UI 重组
            }
        }

        // 当 favoriteVideos 更新时，会重新执行以下代码
        Column {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteVideos){
                    item ->  VideoDescription(item,navController)
                }
            }
        }
    }
    @Composable
    fun CollectList(navController: NavHostController){
        val context = LocalContext.current
        var collectVideos by remember { mutableStateOf<List<VideoDescription>>(emptyList()) }
        // 使用 LaunchedEffect 来处理异步调用
        LaunchedEffect(Unit) {
            getCollectVideos(context) { videos ->
                collectVideos = videos // 更新状态，触发 UI 重组
            }
        }

        // 当 favoriteVideos 更新时，会重新执行以下代码
        Column {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(collectVideos){
                        item ->  VideoDescription(item,navController)
                }
            }
        }
    }
    @Composable
    fun ChaseList(navController: NavHostController){
        val context = LocalContext.current
        var chaseVideos by remember { mutableStateOf<List<VideoDescription>>(emptyList()) }
        // 使用 LaunchedEffect 来处理异步调用
        LaunchedEffect(Unit) {
            getChaseVideos(context) { videos ->
                chaseVideos = videos // 更新状态，触发 UI 重组
            }
        }

        // 当 favoriteVideos 更新时，会重新执行以下代码
        Column {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(chaseVideos){
                        item ->  VideoDescription(item,navController)
                }
            }
        }
    }
//
    fun getFavoriteVideos(context: Context, onResult: (List<VideoDescription>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        // 获取用户文档引用
        val userDocRef = db.collection("users").document("Sakura")

        // 获取用户文档
        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // 获取 favoriteVideos 列表 (DocumentReference 类型)
                val favoriteVideoRefs = document.get("favoriteVideos") as? List<DocumentReference>

                // 如果没有点赞视频，返回空列表
                if (favoriteVideoRefs.isNullOrEmpty()) {
                    onResult(emptyList())
                    return@addOnSuccessListener
                }

                // 创建一个空列表用于存储 VideoDescription 对象
                val videoDescriptions = mutableListOf<VideoDescription>()

                // 遍历每个 DocumentReference，获取对应的视频详情
                favoriteVideoRefs.forEach { videoDocRef ->
                    videoDocRef.get().addOnSuccessListener { videoDocument ->
                        if (videoDocument.exists()) {
                            // 将 Firestore 文档转换为 VideoDescription 对象
                            val videoDescription = videoDocument.toObject(VideoDescription::class.java)
                            if (videoDescription != null) {
                                videoDescriptions.add(videoDescription)
                            }

                            // 当所有文档都获取完后，返回结果
                            if (videoDescriptions.size == favoriteVideoRefs.size) {
                                onResult(videoDescriptions)
                            }
                        }
                    }.addOnFailureListener { e ->
                        Log.w("Firebase", "Error fetching video document", e)
                    }
                }
            } else {
                Log.w("Firebase", "User document does not exist")
                onResult(emptyList()) // 如果用户文档不存在，返回空列表
            }
        }.addOnFailureListener { e ->
            Log.w("Firebase", "Error fetching user document", e)
        }
    }
    fun getCollectVideos(context: Context, onResult: (List<VideoDescription>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    // 获取用户文档引用
    val userDocRef = db.collection("users").document("Sakura")

    // 获取用户文档
    userDocRef.get().addOnSuccessListener { document ->
        if (document.exists()) {
            // 获取 favoriteVideos 列表 (DocumentReference 类型)
            val favoriteVideoRefs = document.get("collectVideos") as? List<DocumentReference>
            // 如果没有点赞视频，返回空列表
            if (favoriteVideoRefs.isNullOrEmpty()) {
                onResult(emptyList())
                return@addOnSuccessListener
            }

            // 创建一个空列表用于存储 VideoDescription 对象
            val videoDescriptions = mutableListOf<VideoDescription>()

            // 遍历每个 DocumentReference，获取对应的视频详情
            favoriteVideoRefs.forEach { videoDocRef ->
                videoDocRef.get().addOnSuccessListener { videoDocument ->
                    if (videoDocument.exists()) {
                        // 将 Firestore 文档转换为 VideoDescription 对象
                        val videoDescription = videoDocument.toObject(VideoDescription::class.java)
                        if (videoDescription != null) {
                            videoDescriptions.add(videoDescription)
                        }

                        // 当所有文档都获取完后，返回结果
                        if (videoDescriptions.size == favoriteVideoRefs.size) {
                            onResult(videoDescriptions)
                        }
                    }
                }.addOnFailureListener { e ->
                    Log.w("Firebase", "Error fetching video document", e)
                }
            }
        } else {
            Log.w("Firebase", "User document does not exist")
            onResult(emptyList()) // 如果用户文档不存在，返回空列表
        }
    }.addOnFailureListener { e ->
        Log.w("Firebase", "Error fetching user document", e)
    }
}
    fun getChaseVideos(context: Context, onResult: (List<VideoDescription>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    // 获取用户文档引用
    val userDocRef = db.collection("users").document("Sakura")

    // 获取用户文档
    userDocRef.get().addOnSuccessListener { document ->
        if (document.exists()) {
            // 获取 favoriteVideos 列表 (DocumentReference 类型)
            val favoriteVideoRefs = document.get("chaseVideos") as? List<DocumentReference>

            // 如果没有点赞视频，返回空列表
            if (favoriteVideoRefs.isNullOrEmpty()) {
                onResult(emptyList())
                return@addOnSuccessListener
            }

            // 创建一个空列表用于存储 VideoDescription 对象
            val videoDescriptions = mutableListOf<VideoDescription>()

            // 遍历每个 DocumentReference，获取对应的视频详情
            favoriteVideoRefs.forEach { videoDocRef ->
                videoDocRef.get().addOnSuccessListener { videoDocument ->
                    if (videoDocument.exists()) {
                        // 将 Firestore 文档转换为 VideoDescription 对象
                        val videoDescription = videoDocument.toObject(VideoDescription::class.java)
                        if (videoDescription != null) {
                            videoDescriptions.add(videoDescription)
                        }

                        // 当所有文档都获取完后，返回结果
                        if (videoDescriptions.size == favoriteVideoRefs.size) {
                            onResult(videoDescriptions)
                        }
                    }
                }.addOnFailureListener { e ->
                    Log.w("Firebase", "Error fetching video document", e)
                }
            }
        } else {
            Log.w("Firebase", "User document does not exist")
            onResult(emptyList()) // 如果用户文档不存在，返回空列表
        }
    }.addOnFailureListener { e ->
        Log.w("Firebase", "Error fetching user document", e)
    }
}

    @Composable
    fun MapScreen(){
        var mapView by remember { mutableStateOf<MapView?>(null) }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context).apply {
                    onCreate(null)
                    map.uiSettings.isZoomControlsEnabled = true
                    map.uiSettings.isCompassEnabled = true
                    map.uiSettings.isMyLocationButtonEnabled = true
                    map.uiSettings.isScaleControlsEnabled= true
                    val beijing = LatLng(39.9042, 116.4074)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(beijing, 10f))
                    mapView = this
                }
            },
            update = { mapView?.onResume() }
        )
    }

    @Composable
    fun selectLogin(navController: NavHostController){

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                navController.navigate("login")
            }) {
                Text(text = "账号密码登录")
            }
            Button(onClick = {
                navController.navigate("biometricAuth")
            }) {
                Text(text = "生物密钥登录")
            }
        }
    }

    @Composable
    fun Check(navController: NavHostController){
        TextField(
            value = "",
            onValueChange = {
            },
            label = {
                Text(text = "搜索")
            },
            leadingIcon = {
                IconButton(onClick = {
                    navController.navigate("videoSearch")
                }) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        )
    }
    @Composable
    fun CheckName(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text(text = "搜索")
            },
            leadingIcon = {
                IconButton(onClick = {
                    onSearch(text)
                }) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        )
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MessageCard(msg: Message,navController: NavHostController) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
        var showBottomSheet by remember { mutableStateOf(false) }
        val openDialog = remember { mutableStateOf(false) }
        var isExpanded by remember { mutableStateOf(false) }
        var textdemo by remember { mutableStateOf("") }
        val imageRes = when (msg.imageURL) {
            "one" -> R.drawable.one
            "two" -> R.drawable.two
            "three" -> R.drawable.three
            "four" -> R.drawable.four
            "five" -> R.drawable.five
            "six" -> R.drawable.six
            "seven" -> R.drawable.seven
            "eight" -> R.drawable.eight
            "nine"  -> R.drawable.first
            else -> R.drawable.first
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },

                text = {
                    Text(text = textdemo)
                },

                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("确认")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("取消")
                    }
                }
            )
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                var scale by remember { mutableFloatStateOf(1f)}
                var offset by remember { mutableStateOf(Offset.Zero) }
                var rotation by remember { mutableFloatStateOf(1f) }

                Card(shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .fillMaxSize()
                        ,
                    elevation = CardDefaults.cardElevation(10.dp)){
                    Text(
                        "You clicked the image of " + msg.author+": ",
                        style = TextStyle(fontSize = 20.sp)

                    )
                    BoxWithConstraints(modifier = Modifier
                        .fillMaxWidth()
                        , contentAlignment = Alignment.Center) {
                        val state =
                            rememberTransformableState { zoomChange, panChange, rotationChange ->
                                scale = (scale * zoomChange).coerceIn(1f, 5f)
                                //rotation += rotationChange
                                val extraWidth = (scale - 1) * constraints.maxWidth
                                val extraHeight = (scale - 1) * constraints.maxHeight
                                val maxx = extraWidth / 2
                                val maxy = extraHeight / 2
                                offset = Offset(
                                    x = (offset.x + scale * panChange.x).coerceIn(-maxx, maxx),
                                    y = (offset.y + scale * panChange.y).coerceIn(-maxy, maxy)
                                )

                            }
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = msg.fuiteName,
                            modifier = Modifier
                                .size(150.dp)
                                .fillMaxWidth()
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    translationX = offset.x
                                    translationY = offset.y
                                }
                                .transformable(state))

                    }
                    Text(text = msg.descriptionfuite)
                }

            }
        }
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxSize()
                .height(150.dp)
                .clickable {
                    isExpanded = !isExpanded
                    openDialog.value = true
                    textdemo = msg.author
                }
                .padding(horizontal = 10.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = msg.fuiteName,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.secondary, shape = CircleShape)
                        .clickable {
                            openDialog.value = false
                            showBottomSheet = true
                            textdemo = "You clicked the image of " + msg.author
                        }
                )

                Row {
                    Text(
                        text = msg.author,

                        modifier = Modifier
                            .clickable(onClick = {
                                isExpanded = !isExpanded
                                openDialog.value = true
                                textdemo = msg.author
                            })
                            .size(150.dp)
                            .padding(45.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = msg.body,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        modifier = Modifier
                            .animateContentSize()
                            .clickable(onClick = {
                                openDialog.value = true
                                textdemo = msg.body
                            })
                            .padding(vertical = 45.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LazyMessageCara(messages: List<Message>, searchText: String,navController: NavHostController) {
        var showDialog by remember { mutableStateOf(false) }
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )

        var filteredMessages = messages.filter {
            it.author.contains(searchText, ignoreCase = true) || it.body.contains(
                searchText,
                ignoreCase = true
            )
        }
        var isRefreshing by remember { mutableStateOf(false) }
        val scope= rememberCoroutineScope()
        val itemCount= filteredMessages.size
        Box(modifier = Modifier.fillMaxSize()) {
            PullToRefreshLazyColumn(items = filteredMessages,
                content = {
                    message ->
                        SwipeToDeleteContainer(item = message,
                                                onDelete ={filteredMessages -= message}) {
                            MessageCard(msg = message,navController)

                        }
                    },
                isRefreshing=isRefreshing,
                onRefreshing = {
                    scope.launch {
                        isRefreshing=true
                        delay(2000)
                        isRefreshing=false
                    }
                }

            )
            SmallFloatingActionButton(
                onClick = {

                },
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            ) {
                Icon(Icons.Outlined.Place, contentDescription = null)
            }
    }
            Box(modifier = Modifier.fillMaxSize()) {
                BadgedBox(
                    badge = {
                        if (itemCount > 0) {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text("$itemCount")
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable {
                            showDialog = false
                            showBottomSheet = false
                            navController.navigate("exam")
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "cart",
                    )
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        modifier = Modifier.fillMaxHeight(),
                        sheetState = sheetState,
                        onDismissRequest = { showBottomSheet = false }
                    ) {

                        LazyMessageCara(
                            messages = MsgData.messages,
                            searchText = searchText,
                            navController = navController
                        )
                    }
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },

                        text = {
                            Text(text = "查看")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                }
                            ) {
                                Text("确认")
                            }
                        },
                        dismissButton = {

                            Button(
                                onClick = {
                                    showDialog = false
                                }
                            ) {
                                Text("取消")
                            }
                        }
                    )
                }
            }
        }
    
    
    @Composable
    fun ButtonNavigtion(navController: NavHostController, modifier: Modifier = Modifier) {

        NavigationBar(modifier = modifier) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.name1)
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == "home",
                onClick = {
                    navController.navigate("home")

                })
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.name7)
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == "main",
                onClick = {
                    navController.navigate("main")

                })
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.name4)
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == "web",
                onClick = {
                    navController.navigate("web")

                })
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ai),
                        contentDescription = null,
                        modifier = Modifier.clip(CircleShape)
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.name6)
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == "AiBot",
                onClick = {
                    navController.navigate("AiBot")

                })
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.name5)
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == "person",
                onClick = {
                    navController.navigate("person")
                })

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavDrawer(navController: NavHostController,viewModel: Lazycolumn_1ViewModel){
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var searchText by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
        val context = LocalContext.current
        var imageURL by remember { mutableStateOf("") }
        var textmode by remember { mutableStateOf("默认用户") }
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val singleImagePckerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                    uri ->
                if (uri != null) {
                    selectedImageUri = uri
                    val savedImagePath = saveImageToInternalStorage(context, uri)
                    if (savedImagePath != null) {
                        imageURL = savedImagePath
                        saveImagePath(context, savedImagePath)
                    }
                }

            },
        )


        ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    var todoString by remember { mutableStateOf("") }
                    var showdialog by remember { mutableStateOf(false) }
                    var showdialog2 by remember { mutableStateOf(false) }
                    var selected by remember { mutableStateOf(false) }

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.Cyan)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Row(modifier = Modifier.fillMaxSize()) {
                                    Column {
                                        AsyncImage(
                                            model = imageURL,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .clip(CircleShape)
                                                .border(
                                                    1.5.dp,
                                                    MaterialTheme.colorScheme.secondary,
                                                    shape = CircleShape
                                                )
                                        )
                                    }
                                    Column(modifier = Modifier.fillMaxWidth(1f)) {
                                        IconButton(
                                            onClick =
                                            {
                                                singleImagePckerLauncher.launch(
                                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                )
                                            }, modifier = Modifier
                                                .size(30.dp)
                                                .align(Alignment.End)
                                        ) {
                                            Icon(Icons.Default.Edit, contentDescription = null)
                                        }
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.Cyan)
                                .padding(vertical = 5.dp)
                        ) {
                            Row {
                                Text(text = textmode, modifier = Modifier
                                    .clickable {
                                        showdialog = true
                                    }
                                    .padding(horizontal = 20.dp), fontSize = 20.sp)
                                IconButton(
                                    onClick = {
                                        showdialog = true
                                    }, modifier = Modifier
                                        .size(20.dp)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                }
                            }
                            Spacer(modifier = Modifier
                                .padding(vertical = 5.dp)
                                .background(Color.Cyan))
                            Text(text = "永远相信美好的事情即将发生", modifier = Modifier
                                .clickable {
                                }, fontSize = 10.sp
                            )

                        }
                    }
                   Column(modifier = Modifier
                       .fillMaxSize(1f)
                       .background(Color.Cyan)) {
                       Box(modifier = Modifier.fillMaxSize()){
                           Column {
                               Button(
                                   onClick = {
                                       showdialog2 = true
                                   },
                                   modifier = Modifier
                                       .padding(end = 10.dp)
                                       .align(Alignment.CenterHorizontally)
                               ) {
                                   Text(text = "添加代办")
                               }
                               LazyColumn(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(8.dp)
                               ) {

                               }

                           }

                       }

                   }
                    if(showdialog){
                        Dialog(onDismissRequest = {
                            showdialog = false
                        }) {
                            Column(verticalArrangement = Arrangement.Center) {
                                TextField(
                                    value = textmode,
                                    maxLines = 1,
                                    onValueChange = { textmode = it },
                                    placeholder = {
                                        Text("输入您的用户名")
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                    ,
                                )
                                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                                Button(
                                    onClick = {
                                        showdialog = false
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("确认")
                                }
                            }
                        }
                    }
                    if(showdialog2){
                        Dialog(onDismissRequest = {
                            showdialog2 = false
                        }) {
                            val NewLazyItem = TODoItem(todoString)
                            Column(verticalArrangement = Arrangement.Center) {
                                TextField(
                                    value = todoString,
                                    maxLines = 1,
                                    onValueChange = { todoString = it },
                                    placeholder = {
                                        Text("添加代办")
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                    ,
                                )
                                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                                Button(
                                    onClick = {
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("确认")
                                }
                            }
                        }
                    }
                }
            }
        }, modifier = Modifier, gesturesEnabled = false, drawerState = drawerState) {
            Scaffold() {
                paddingValues ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)){
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                        }
                                    }) {
                                        Icon(Icons.Filled.Menu, null)
                                    }
                                CheckName(onSearch = { searchText = it })
                                }
                            Box() {
                                if (showBottomSheet) {
                                    ModalBottomSheet(
                                        modifier = Modifier.fillMaxHeight(),
                                        sheetState = sheetState,
                                        onDismissRequest = { showBottomSheet = false }
                                    ) {

                                        LazyMessageCara(
                                            messages = MsgData.messages,
                                            searchText = searchText,
                                            navController = navController
                                        )
                                    }
                                }
                                if (showDialog) {
                                    AlertDialog(
                                        onDismissRequest = {
                                            showDialog = false
                                        },

                                        text = {
                                            Text(text = "查看")
                                        },
                                        confirmButton = {
                                            Button(
                                                onClick = {
                                                    showDialog = false
                                                }
                                            ) {
                                                Text("确认")
                                            }
                                        },
                                        dismissButton = {

                                            Button(
                                                onClick = {
                                                    showDialog = false
                                                }
                                            ) {
                                                Text("取消")
                                            }
                                        }
                                    )
                                }

                                LazyMessCard1(viewModel, searchText)
                            }
                        }
                    }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(viewModel: Lazycolumn_1ViewModel,navController: NavHostController) {
        var searchText by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
        Box {
            Column {
                CheckName(onSearch = { searchText = it })
                Box() {
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            modifier = Modifier.fillMaxHeight(),
                            sheetState = sheetState,
                            onDismissRequest = { showBottomSheet = false }
                        ) {

                            LazyMessageCara(
                                messages = MsgData.messages,
                                searchText = searchText,
                                navController = navController
                            )
                        }
                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showDialog = false
                            },

                            text = {
                                Text(text = "查看")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showDialog = false
                                    }
                                ) {
                                    Text("确认")
                                }
                            },
                            dismissButton = {

                                Button(
                                    onClick = {
                                        showDialog = false
                                    }
                                ) {
                                    Text("取消")
                                }
                            }
                        )
                    }

                LazyMessCard1(viewModel, searchText)
            }
            }
        }

    }

    @Composable
    fun MenuScreen(navController: NavHostController) {
        var searchText by remember { mutableStateOf("") }
        Column() {
            LazyMessageCara(messages = MsgData.messages, searchText = searchText, navController = navController)
        }
    }

    @Composable
    fun GifSplashScreen(navController: NavHostController) {

    LaunchedEffect(true) {
        delay(2000)
        navController.popBackStack()
        navController.navigate("selectLogin")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.__00_00_00_00_00_30)
                .decoderFactory(GifDecoder.Factory())
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
    //保存登录状态
    fun saveLoginInfo(context: Context, username: String, password: String) {
    val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("username", username)
    editor.putString("password", password)
    editor.putBoolean("isLoggedIn", true) // 标记用户已登录
    editor.apply()
}
    //清除登录状态
    fun clearLoginInfo(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
 }
    //检查是否已经登录
    fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isLoggedIn", false)
    }
    //获取当前登录的用户名
    fun getLoggedInUsername(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", "") ?: "" // 如果没有用户名，返回空字符串
    }


    //生成随机访客id
    fun generateRandomId(length: Int): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
    //访客模式
    fun saveGuestLoginInfo(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val guestId = generateRandomId(6)

    // 保存访客的登录状态和ID
    editor.putString("userId", guestId)
    editor.putBoolean("isLoggedIn", true)
    editor.putBoolean("isGuest", true) // 标记为访客
    editor.apply()
}
    //访客模式
    fun isGuestUser(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isGuest", false)
}
    //获取访客模式随机生成的id
    fun getUserId(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("UserLoginInfo", Context.MODE_PRIVATE)
        // 如果 "userId" 为 null，返回默认值 "guest"
        return sharedPreferences.getString("userId", "guest") ?: "guest"
    }


    @Composable
    fun StartScreen(navController: NavHostController) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (isUserLoggedIn(context)) {
            navController.navigate("home") // 用户已经登录，跳转到主页面
        } else {
            navController.navigate("login") // 未登录，跳转到登录页面
        }
    }
}
    @Composable
    fun LoginDemo(navController: NavHostController,viewModel: UserViewModel){
        var passwordHidden by remember{ mutableStateOf(false)}
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val loginResult by viewModel.loginResult.observeAsState()
        var hasShownToast by remember { mutableStateOf(false) }
        val context = LocalContext.current
        Column(modifier = Modifier
            .padding(vertical = 200.dp)
            .fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {

                val focusManager = LocalFocusManager.current

                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    singleLine = true,
                    label = { Text("登录账号 :") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        ,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                val focusManager = LocalFocusManager.current
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    singleLine = true,
                    label = { Text("登录密码") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            Icon(imageVector = Icons.Default.AccountCircle, null)
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(modifier = Modifier.fillMaxWidth())  {
                Button(onClick = {
                    viewModel.login(username, password)
                    username = ""
                    password = ""
                    hasShownToast = false
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("登录")
                }
                loginResult?.let {
                    if ( !hasShownToast) {
                        if (it) {
                            Toast.makeText(LocalContext.current, "登录成功", Toast.LENGTH_SHORT).show()
                            saveLoginInfo(context, username, password)
                            navController.navigate("home")
                        } else {
                            Toast.makeText(LocalContext.current, "登录失败", Toast.LENGTH_SHORT).show()
                        }
                        hasShownToast = true
                    }
                }
                Text(text = "如果您还没有账号，请注册", modifier = Modifier
                    .clickable {
                        navController.navigate("register")
                    }
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp), color = Color.Blue)
                Text(text = "访客模式", modifier = Modifier
                    .clickable {
                        saveGuestLoginInfo(context)
                        navController.navigate("home")
                    }
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp), color = Color.Blue)
            }
        }

    }

    @Composable
    fun RegisterDemo(navController: NavHostController,viewModel: UserViewModel){
        var passwordHidden by remember{ mutableStateOf(false)}
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val registerResult by viewModel.registerResult.observeAsState()
        var hasShownToast by remember { mutableStateOf(false) }
        Column(modifier = Modifier
            .padding(vertical = 200.dp)
            .fillMaxSize(),

        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                val focusManager = LocalFocusManager.current
                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = { Text("注册账号 :") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                    ,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                val focusManager = LocalFocusManager.current
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = { Text("填写密码") },

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            Icon(imageVector = Icons.Default.AccountCircle, null)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None

                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                viewModel.register(username, password)
                hasShownToast = false
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("注册")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick = {
               navController.navigate("login")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("登录")
            }
            registerResult?.let {
                if (!hasShownToast) {
                    if (it) {
                        Toast.makeText(LocalContext.current, "注册成功", Toast.LENGTH_SHORT).show()
                        username = ""
                        password = ""
                        navController.navigate("login")
                    } else {
                        Toast.makeText(LocalContext.current, "注册失败，用户名已经存在", Toast.LENGTH_SHORT).show()

                    }
                    hasShownToast = true
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun <T> SwipeToDeleteContainer(
        item:T,
        onDelete:(T)->Unit,
        animationDuration:Int=500,
        content: @Composable (T) -> Unit

    ) {
        var isRemove by remember { mutableStateOf(false) }
        val state = rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                if (value == SwipeToDismissBoxValue.StartToEnd) {
                    isRemove = true
                    true
                } else {
                    false
                }
            }
        )
        LaunchedEffect(isRemove) {
            if (isRemove) {
                delay(animationDuration.toLong())
                onDelete(item)
            }
        }
        AnimatedVisibility(
            visible = !isRemove,
            exit = shrinkVertically(
                animationSpec = tween(durationMillis = animationDuration),
                shrinkTowards = Alignment.Top
            ) + fadeOut(),
        ) {
            SwipeToDismissBox(
                state = state,
                backgroundContent = {
                },
                content = { content(item) }
            )
        }
    }
    @Composable
    fun PictureDemo(navController: NavHostController,imageURL: Int){
        var scale by remember { mutableFloatStateOf(1f)}
         var offset by remember { mutableStateOf(Offset.Zero) }
        var rotation by remember { mutableFloatStateOf(1f) }
        BoxWithConstraints(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(874f / 934f)
        , contentAlignment = Alignment.Center){
            val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
                scale = (scale*zoomChange).coerceIn(1f,5f)
                //rotation += rotationChange
                val extraWidth = (scale-1)*constraints.maxWidth
                val extraHeight=(scale-1)*constraints.maxHeight
                val maxx = extraWidth/2
                val maxy = extraHeight/2
                offset=Offset(x=(offset.x+scale*panChange.x).coerceIn(-maxx,maxx),
                                y=(offset.y+scale*panChange.y).coerceIn(-maxy,maxy))

            }
            Image(painter = painterResource(imageURL),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        //rotationZ=rotation
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .transformable(state))
        }


    }

    @Composable
    fun imageupload(){
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        var selectedImageUriList by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        var singleImagePckerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                uri->
                selectedImageUri = uri
            },
        )
        var multipleImagePckerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = {uriList ->
                selectedImageUriList = uriList
            }
        )

    }

    @Composable
    fun lazyOne(messages: List<Message>,navController: NavHostController){
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(messages) { message ->
                    MessageCard(msg = message, navController=navController)
                }
            }
        }
    }
    @Composable
    fun example(navController: NavHostController){
        lazyOne(messages = MsgData.messages, navController = navController)
    }

    @Composable
    fun SteamingVideo(){
        var isPlaying by remember {
            mutableStateOf(false)
        }
        var videoItemIndex by remember {
            mutableIntStateOf(0)
        }
        val viewModel :VideoPlayerViewModel = viewModel()
        viewModel.videoList = YisiHougongVideoList
        val context = LocalContext.current
        Column {
            StreamerPlayer(viewModel = viewModel, isPlaying =isPlaying , onPlayClosed ={isVideoPlaying->
                isPlaying = isVideoPlaying
            } )

            LazyColumn(modifier = Modifier.padding(10.dp), content = {
                itemsIndexed(items= YisiHougongVideoList){
                    index,item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            if (videoItemIndex != index) isPlaying = false
                            viewModel.index = index
                            videoItemIndex = viewModel.index
                        }, horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        AsyncImage(model = item.thumbnail, contentDescription = "视频缩略图", modifier = Modifier
                            .fillMaxHeight()
                            .size(200.dp))
                        Text(text = "第${index+1}集",
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                        )
                    }
                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp))
                }
            })
            LaunchedEffect(videoItemIndex) {
                isPlaying = true
                viewModel.apply {
                    releasePlayer()
                    initializePlayer(context)
                    playVideo()
                }
            }
        }
    }

    @Composable
    fun Re0SteamingVideo(){
    var isPlaying by remember {
        mutableStateOf(false)
    }
    var videoItemIndex by remember {
        mutableIntStateOf(0)
    }
    val viewModel :VideoPlayerViewModel = viewModel()
    viewModel.videoList = Re0VideoList
    val context = LocalContext.current
    Column {
        StreamerPlayer(viewModel = viewModel, isPlaying =isPlaying , onPlayClosed ={isVideoPlaying->
            isPlaying = isVideoPlaying
        } )

        LazyColumn(modifier = Modifier.padding(10.dp), content = {
            itemsIndexed(items= Re0VideoList){
                    index,item ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        if (videoItemIndex != index) isPlaying = false
                        viewModel.index = index
                        videoItemIndex = viewModel.index
                    }, horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    AsyncImage(model = item.thumbnail, contentDescription = "视频缩略图", modifier = Modifier
                        .fillMaxHeight()
                        .size(200.dp))
                    Text(text = "第${index+1}集",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    )
                }
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp))
            }
        })
        LaunchedEffect(videoItemIndex) {
            isPlaying = true
            viewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo()
            }
        }
    }
}

    @Composable
    fun JhSteamingVideo(){
        var isPlaying by remember {
            mutableStateOf(false)
        }
        var videoItemIndex by remember {
            mutableIntStateOf(0)
        }
        val viewModel :VideoPlayerViewModel = viewModel()
        viewModel.videoList = JhVideoList
        val context = LocalContext.current
        Column {
            StreamerPlayer(viewModel = viewModel, isPlaying =isPlaying , onPlayClosed ={isVideoPlaying->
                isPlaying = isVideoPlaying
            } )

            LazyColumn(modifier = Modifier.padding(10.dp), content = {
                itemsIndexed(items= JhVideoList){
                        index,item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            if (videoItemIndex != index) isPlaying = false
                            viewModel.index = index
                            videoItemIndex = viewModel.index
                        }, horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        AsyncImage(model = item.thumbnail, contentDescription = "视频缩略图", modifier = Modifier
                            .fillMaxHeight()
                            .size(200.dp))
                        Text(text = "第${index+1}集",
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                        )
                    }
                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp))
                }
            })
            LaunchedEffect(videoItemIndex) {
                isPlaying = true
                viewModel.apply {
                    releasePlayer()
                    initializePlayer(context)
                    playVideo()
                }
            }
        }
    }
    @Composable
     fun OtherSteamingVideo(){
    var isPlaying by remember {
        mutableStateOf(false)
    }
    var videoItemIndex by remember {
        mutableIntStateOf(0)
    }
    val viewModel :VideoPlayerViewModel = viewModel()
    viewModel.videoList = mainVideoList
    val context = LocalContext.current
    Column {
        StreamerPlayer(viewModel = viewModel, isPlaying =isPlaying , onPlayClosed ={isVideoPlaying->
            isPlaying = isVideoPlaying
        } )

        LazyColumn(modifier = Modifier.padding(10.dp), content = {
            itemsIndexed(items= mainVideoList){
                    index,item ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        if (videoItemIndex != index) isPlaying = false
                        viewModel.index = index
                        videoItemIndex = viewModel.index
                    }, horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    AsyncImage(model = item.thumbnail, contentDescription = "视频缩略图", modifier = Modifier
                        .fillMaxHeight()
                        .size(200.dp))
                    Text(text = "第${index+1}集",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    )
                }
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp))
            }
        })
        LaunchedEffect(videoItemIndex) {
            isPlaying = true
            viewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo()
            }
        }
    }
}

    @Composable
    fun LoveVideo(navController: NavHostController){
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn { item {
                Text(text = "疑似后宫", fontSize = 20.sp)
                AsyncImage(model = "https://i0.hdslb.com/bfs/new_dyn/73a9b3639fe514fdd017f82cfbd1ec6e333497172.jpg",contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("疑似后宫")
                    })
               Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }
                item {
                    Text(text = "Re:0 第三季", fontSize = 20.sp)
                    AsyncImage(model = "https://q7.itc.cn/images01/20240920/8142bdceccaa43b1a782ec46a05164ae.jpeg",contentDescription = null, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("Re0")
                        })
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                }
                item {
                    Text(text = "精灵幻想记 第二季", fontSize = 20.sp)
                    AsyncImage(model = "https://pic.kts.g.mi.com/c83dece37febedaea6137e2fe4c9a6411141326680459426200.png",contentDescription = null, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("精幻")
                        })
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                }
                item {
                    Text(text = "其他番剧", fontSize = 20.sp)
                    AsyncImage(model = "https://i1.hdslb.com/bfs/archive/6f8dba75a82fb2a6624af59df32d478066ca1adc.jpg",contentDescription = null, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("其他新番")
                        })
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                }
            }
        }
    }

