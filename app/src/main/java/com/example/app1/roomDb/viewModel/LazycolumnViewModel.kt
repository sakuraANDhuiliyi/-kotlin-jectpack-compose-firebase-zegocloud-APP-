package com.example.app1.roomDb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.app1.roomDb.Lazycolumn
import kotlinx.coroutines.launch

class LazycolumnViewModel(private val repository: Repository): ViewModel() {
    fun getMessages() = repository.getAllMessages().asLiveData(viewModelScope.coroutineContext)
    fun upsertMessages(message: Lazycolumn) {
        viewModelScope.launch {
            repository.upsertMessage(message)
        }
        fun deleteMessages(message: Lazycolumn) {
            viewModelScope.launch {
                repository.deleteMessage(message)
            }
        }
    }
}