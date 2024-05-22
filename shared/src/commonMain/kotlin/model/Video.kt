package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("title"          ) val title         : String? = null,
    @SerialName("presenter_name" ) val presenterName : String? = null,
    @SerialName("description"    ) val videoDescription   : String? = null,
    @SerialName("thumbnail_url"  ) val thumbnailUrl  : String? = null,
    @SerialName("video_url"      ) val videoUrl      : String? = null,
    @SerialName("video_duration" ) val videoDuration : Long?    = null
)