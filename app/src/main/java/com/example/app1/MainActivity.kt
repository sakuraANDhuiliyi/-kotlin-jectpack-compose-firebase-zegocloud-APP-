package com.example.app1

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
import android.widget.Scroller
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.ThumbUp
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
import com.example.app1.roomDb.viewModel.UserViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.LatLng
import com.example.app1.BiometricPromptManager.*
import com.example.app1.roomDb.Lazycolumn_1
import com.example.app1.roomDb.Lazycolumn_1Database
import com.example.app1.roomDb.viewModel.Lazycolumn_1Repository
import com.example.app1.roomDb.viewModel.Lazycolumn_1ViewModel
import com.example.app1.roomDb.viewModel.TodoViewModel
import java.time.LocalDateTime

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            App1Theme {
                val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
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

                        NavHost(navController = navController, startDestination = "home") {
                            composable("alarm"){ alarm() }
                            composable("app"){ OpenAppButton() }
                            composable("splash"){ GifSplashScreen(navController = navController) }
                            composable("home") { page(navController,viewModel_1,todoViewModel) }
                            composable("menu") { MenuScreen(navController=navController) }
                            composable("exam") { example(navController=navController) }
                            composable("login"){ LoginDemo(navController = navController,userViewModel) }
                            composable("register"){ RegisterDemo(navController=navController,userViewModel) }
                            composable("image"){ PictureDemo(navController,R.drawable.first) }
                            composable("AiBot"){ ChatPage(
                                modifier = Modifier.fillMaxSize(),
                                chatViewModel
                            )}
                            composable("web"){ WebBrowser(modifier = Modifier) }
                            composable("map"){ MapScreen() }
                            composable("biometricAuth"){BiometricAuthScreen(navController, this@MainActivity)}
                            composable("selectLogin"){ selectLogin(navController) }
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
    data class Message(
        val author: String,
        val body: String,
        val imageURL: String,
        val fuiteName: String,
        val descriptionfuite:String
    )

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
        var alarmItem :AlarmItem ?=null
        checkAndRequestExactAlarmPermission(LocalContext.current)
        Column(modifier = Modifier.fillMaxSize().padding(16.dp),
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
    fun page(navController: NavHostController,viewModel: Lazycolumn_1ViewModel,todoViewModel: TodoViewModel){

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
        var imageURL by remember { mutableStateOf(getImagePath(context) ?: "\"https://truth.bahamut.com.tw/s01/201804/b34f037ab8301d4cd1331f686405b97a.JPG\"") }
        var textmode by remember { mutableStateOf(loadUserName(context) ?: "默认用户") }
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
                                Text(text = "永远相信美好的事情即将发生", modifier = Modifier
                                    .clickable {
                                    }, fontSize = 10.sp
                                )

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
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { CherkName(onSearch = { searchText = it },Modifier) },
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
                            0 -> LazyMessCard1(viewModel, searchText)
                            else -> Text("页面不存在")
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
    fun CherkName(onSearch: (String) -> Unit,modifier: Modifier) {
        var text by remember { mutableStateOf("") }
        Column {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearch(it)
                },
                label = {
                    Text(text = "搜索记录")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, disabledContainerColor = Color.Transparent)
            )
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MessageCard(msg: com.example.app1.Message,navController: NavHostController) {
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
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.name5)
                    )
                },
                selected = navController.currentBackStackEntry?.destination?.route == "map",
                onClick = {
                    navController.navigate("map")

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
                                CherkName(onSearch = { searchText = it }, modifier = Modifier.fillMaxWidth(1f))
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
                CherkName(onSearch = { searchText = it },Modifier)
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
                .data(R.drawable._5_00_00_00_00_00_30)
                .decoderFactory(GifDecoder.Factory())
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
    @Composable
    fun LoginDemo(navController: NavHostController,viewModel: UserViewModel){
        var passwordHidden by remember{ mutableStateOf(false)}
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val loginResult by viewModel.loginResult.observeAsState()
        var hasShownToast by remember { mutableStateOf(false) }
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

