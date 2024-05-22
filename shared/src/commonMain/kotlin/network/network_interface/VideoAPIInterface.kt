package network.network_interface

import model.Video

interface VideoAPIInterface {
    suspend fun getAllVideos(): List<Video>
}