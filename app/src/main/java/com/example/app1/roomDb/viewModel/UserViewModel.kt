package com.example.app1.roomDb.viewModel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app1.User
import com.example.app1.saveLoginInfo
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> get() = _registerResult
    // 登录功能：根据用户名和密码查找用户
    fun login(username: String, password: String) {
        firestore.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val user = documents.first().toObject(User::class.java)
                    _loginResult.value = user.password == password
                } else {
                    _loginResult.value = false
                }
            }
            .addOnFailureListener {
                _loginResult.value = false
            }
    }

    // 注册功能：将新用户信息存储到 Firestore
    fun register(username: String, password: String) {
        // 首先检查用户名是否已经存在
        firestore.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // 如果用户名不存在，则创建新用户
                    val newUser = User(
                        id = firestore.collection("users").document().id,
                        username = username,
                        password = password,
                        email = "", // 可以在注册时增加邮箱字段
                        imageUrl = "", // 可以添加默认头像
                        favoriteVideos = null,
                        collectVideos = null,
                        chaseVideos = null
                    )
                    firestore.collection("users")
                        .document(newUser.username)
                        .set(newUser)
                        .addOnSuccessListener {
                            _registerResult.value = true
                        }
                        .addOnFailureListener {
                            _registerResult.value = false
                        }
                } else {
                    // 用户名已经存在
                    _registerResult.value = false
                }
            }
            .addOnFailureListener {
                _registerResult.value = false
            }
    }
}
