package com.quipper.test.cache.cache_interface

import model.Video

interface VideoCacheInterface {
    fun clearVideos()

    fun createVideo(banners: List<Video>)

    fun insertVideo(banner: Video)

    fun getAllVideos(): List<Video>
}