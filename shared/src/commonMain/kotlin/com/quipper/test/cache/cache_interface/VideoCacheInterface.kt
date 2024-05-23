package com.quipper.test.cache.cache_interface

import model.Video

interface VideoCacheInterface {
    fun clearVideos()

    fun createVideo(videos: List<Video>)

    fun insertVideo(video: Video)

    fun getAllVideos(): List<Video>
}