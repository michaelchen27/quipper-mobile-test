package com.quipper.test

import com.quipper.test.cache.DatabaseDriverFactory
import com.quipper.test.cache.VideoCache
import model.Video
import network.VideoAPI

class VideoSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = VideoCache(databaseDriverFactory)
    private val api = VideoAPI()

    @Throws(Exception::class)
    suspend fun getVideos(forceReload: Boolean): List<Video> {
        val cachedVideos = database.getAllVideos()
        return if(cachedVideos.isNotEmpty() && !forceReload) {
            cachedVideos

        } else {
            api.getAllVideos().also {
                database.clearVideos()
                database.createVideo(it)
            }
        }
    }
}