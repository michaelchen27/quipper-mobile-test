package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.Video
import network.network_interface.VideoAPIInterface

class VideoAPI: VideoAPIInterface {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getAllVideos(): List<Video> {
        val videos: List<Video> = httpClient.get("https://quipper.github.io/native-technical-exam/playlist.json").body()
        return videos
    }
}