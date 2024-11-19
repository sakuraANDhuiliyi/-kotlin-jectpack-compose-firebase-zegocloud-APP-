package com.example.app1.videoPlayer

// VideoViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app1.VideoDescription

class VideoViewModel : ViewModel() {
    private val _selectedVideo = MutableLiveData<VideoDescription>()
    val selectedVideo: LiveData<VideoDescription> = _selectedVideo

    fun selectVideo(video: VideoDescription) {
        _selectedVideo.value = video
    }
}
