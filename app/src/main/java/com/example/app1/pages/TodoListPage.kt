package com.example.app1.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app1.R
import com.example.app1.alarm.AlarmItem
import com.example.app1.alarm.AndroidAlarmScheduler
import com.example.app1.checkAndRequestExactAlarmPermission
import com.example.app1.roomDb.Todo
import com.example.app1.roomDb.viewModel.TodoViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale


@Composable
fun TodoListPage(viewModel: TodoViewModel){

    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                modifier= Modifier.weight(1f),
                value = inputText,
                onValueChange = {
                    inputText = it
                })
            Button(onClick = {
                viewModel.addTodo(inputText)
                inputText = ""
            }) {
                Text(text = "Add")
            }
        }

        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it){index: Int, item: Todo ->
                        TodoItem(item = item, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })
                    }
                }
            )
        }?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No items yet",
            fontSize = 16.sp
        )


    }

}

@Composable
fun TodoItem(item : Todo, onDelete : ()-> Unit) {
    var showdialog by remember { mutableStateOf(false) }
    var secondsText by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val scheduler = AndroidAlarmScheduler(LocalContext.current)
    var alarmItem : AlarmItem?=null
    checkAndRequestExactAlarmPermission(LocalContext.current)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
        IconButton(onClick = {
            showdialog = true
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ll131yar3b),
                contentDescription = "clock",
                tint = Color.White
            )
        }

    }
    if (showdialog){
        Dialog(onDismissRequest = {showdialog = false}) {
            Column(modifier = Modifier.size(400.dp).padding(16.dp),
                verticalArrangement = Arrangement.Center ) {
                TextField(
                    value = secondsText,
                    onValueChange = {secondsText=it},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "设置时间(秒)")
                    }
                )
                TextField(
                    value = item.title,
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
                            message = item.title
                        )
                        alarmItem?.let(scheduler::schedule)
                        secondsText =""
                        message = ""
                        showdialog = false
                    }){ Text(text = "设置闹钟")
                    }
                    Button(onClick = {
                        alarmItem?.let(scheduler::cancel)
                        showdialog = false
                    }){ Text(text = "取消闹钟")
                    }
                }

            }
        }
    }
}