package com.quipper.test.cache

import com.quipper.test.AppDatabase
import com.quipper.test.cache.cache_interface.VideoCacheInterface
import model.Video

class VideoCache(databaseDriverFactory: DatabaseDriverFactory) : VideoCacheInterface {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    override fun clearVideos() {
        dbQuery.removeAllVideos()
    }

    override fun createVideo(videos: List<Video>) {
        videos.forEach { video ->
            insertVideo(video)
        }
    }

    override fun insertVideo(video: Video) {
        dbQuery.insertVideo(
            id = null,
            title = video.title ?: "",
            presenter_name = video.presenterName,
            description = video.videoDescription,
            thumbnail_url = video.thumbnailUrl,
            video_url = video.videoUrl,
            video_duration = video.videoDuration
        )
    }

    override fun getAllVideos(): List<Video> {
        return dbQuery.selectAllVideos(::mapVideos).executeAsList()
    }

    private fun mapVideos(
        id: Long,
        title: String,
        presenter_name: String?,
        description: String?,
        thumbnail_url: String?,
        video_url: String?,
        video_duration: Long?
    ): Video {
        return Video(
            title = title,
            presenterName = presenter_name,
            videoDescription = description,
            thumbnailUrl = thumbnail_url,
            videoUrl = video_url,
            videoDuration = video_duration,
        )
    }


}