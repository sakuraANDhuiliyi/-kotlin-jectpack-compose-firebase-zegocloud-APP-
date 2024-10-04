package com.example.app1.roomDb.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app1.roomDb.User
import com.example.app1.roomDb.UserDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val loginResult = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            val existingUser = repository.checkUserExists(username)
            if (existingUser == null) {
                repository.insertUser(User(username = username, password = password))
                registerResult.postValue(true)
            } else {
                registerResult.postValue(false)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)
            loginResult.postValue(user != null)
        }
    }
}

