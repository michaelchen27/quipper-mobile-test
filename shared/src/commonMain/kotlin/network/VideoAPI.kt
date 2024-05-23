package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.CustomException
import model.Video
import network.network_interface.VideoAPIInterface



class VideoAPI : VideoAPIInterface {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        HttpResponseValidator {
            validateResponse { response ->
                when (response.status.value) {
                    !in 200..300 -> throw CustomException.NetworkError(response.toString())
                }

                handleResponseExceptionWithRequest { cause, _ ->
                    println(cause.message)
                    when (cause) {
                        is SocketTimeoutException, is ConnectTimeoutException -> throw CustomException.OfflineError(
                            "SocketTimeOut", cause
                        )

                        else -> throw cause
                    }
                }
            }
        }
    }

    override suspend fun getAllVideos(): List<Video> {
        try {
            val videos: List<Video> =
                httpClient.get("https://quipper.github.io/native-technical-exam/playlist.json")
                    .body()

            return videos

        } catch (e: CustomException.OfflineError) {
            throw CustomException.OfflineError()
        }
    }
}