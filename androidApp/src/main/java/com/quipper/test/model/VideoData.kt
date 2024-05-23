package com.quipper.test.model

import MyUtil
import com.quipper.test.core.base.DiffUtilCallbackItem
import model.Video

data class VideoData(
    val title: String? = null,
    val presenterName: String? = null,
    val videoDescription: String? = null,
    val thumbnailUrl: String? = null,
    val videoUrl: String? = null,
    val videoDuration: String? = null
) : DiffUtilCallbackItem {
    override fun diff(): String = "$title/$presenterName/$videoDuration"
}

// Mapper
fun Video.toDomain() = VideoData(
    title = title,
    presenterName = presenterName,
    videoDescription = videoDescription,
    thumbnailUrl = thumbnailUrl,
    videoUrl = videoUrl,
    videoDuration = MyUtil.formatMillisToStopwatch(videoDuration),
)

fun List<Video>.toListDomain(): List<VideoData> = map {
    it.toDomain()
}

