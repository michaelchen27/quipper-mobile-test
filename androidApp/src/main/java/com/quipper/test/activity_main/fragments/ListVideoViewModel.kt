package com.quipper.test.activity_main.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quipper.test.VideoSDK
import com.quipper.test.cache.AndroidDatabaseDriverFactory
import com.quipper.test.model.VideoData
import com.quipper.test.model.toListDomain
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.Video

class ListVideoViewModel: ViewModel() {
    private val _videoList = MutableSharedFlow<List<VideoData>>()
    val videoList: SharedFlow<List<VideoData>> get() = _videoList

    private lateinit var videoSDK: VideoSDK

    fun setupDbDriver(context: Context) {
        videoSDK = VideoSDK(AndroidDatabaseDriverFactory(context))
        getVideos(true)
    }

    fun getVideos(forceReload: Boolean = false) = viewModelScope.launch {
        val videos = videoSDK.getVideos(forceReload)
        _videoList.emit(
            videos.toListDomain()
        )
    }


}