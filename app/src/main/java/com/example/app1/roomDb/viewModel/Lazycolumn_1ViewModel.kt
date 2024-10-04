package com.example.app1.roomDb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.app1.roomDb.Lazycolumn_1
import kotlinx.coroutines.launch

class Lazycolumn_1ViewModel(private val repository: Lazycolumn_1Repository): ViewModel() {
    fun getMessages() = repository.getAllMessages().asLiveData(viewModelScope.coroutineContext)
    fun upsertMessages(message: Lazycolumn_1) {
        viewModelScope.launch {
            repository.upsertMessage(message)
        }
    }
    fun deleteMessages(message: Lazycolumn_1) {
        viewModelScope.launch {
            repository.deleteMessage(message)
        }
    }
}