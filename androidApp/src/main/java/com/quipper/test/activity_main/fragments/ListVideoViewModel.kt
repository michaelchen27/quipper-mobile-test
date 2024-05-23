package com.quipper.test.activity_main.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quipper.test.VideoSDK
import com.quipper.test.cache.AndroidDatabaseDriverFactory
import com.quipper.test.model.VideoData
import com.quipper.test.model.toListDomain
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import model.CustomException
import java.net.UnknownHostException

class ListVideoViewModel : ViewModel() {
    private lateinit var videoSDK: VideoSDK

    sealed class VideoUIState(val videos: List<VideoData>? = null, val errMsg: String?) {
        class Success(videos: List<VideoData>) : VideoUIState(videos, null)

        class Loading : VideoUIState(null, null)

        class Error(errMsg: String) : VideoUIState(null, errMsg)
    }

    private val _listVideoUIState = MutableSharedFlow<VideoUIState>()
    val listVideoUIState: SharedFlow<VideoUIState> get() = _listVideoUIState

    fun setupDbDriver(context: Context) {
        videoSDK = VideoSDK(AndroidDatabaseDriverFactory(context))
        getVideos(false)
    }

    fun getVideos(forceReload: Boolean) = viewModelScope.launch {
        _listVideoUIState.emit(VideoUIState.Loading())

        try {
            val videos = videoSDK.getVideos(forceReload)
            _listVideoUIState.emit(VideoUIState.Success(videos.toListDomain()))

        } catch (e: CustomException.OfflineError) {
            _listVideoUIState.emit(VideoUIState.Error("Tidak ada koneksi internet"))


        } catch (e: UnknownHostException) {
            _listVideoUIState.emit(VideoUIState.Error("Tidak ada koneksi internet"))

        }
    }

}