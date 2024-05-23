package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.util.rootCause
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import model.CustomException
import model.Video
import network.network_interface.VideoAPIInterface


class VideoAPI : VideoAPIInterface {
    @OptIn(InternalAPI::class)
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
                        is SocketTimeoutException,
                        is ConnectTimeoutException,
                        is HttpRequestTimeoutException,
                        is IOException,
                        is TimeoutCancellationException,
                        -> {
                            throw CustomException.OfflineError("Offline", cause)
                        }

                        else -> {
                            val rootCause = cause.rootCause
                            if (rootCause?.message?.contains(
                                    "offline",
                                    ignoreCase = true
                                ) == true
                            ) {
                                throw CustomException.OfflineError("Offline", cause)
                            } else {
                                throw CustomException.UnknownError()

                            }
                        }
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

        } catch (e: CustomException.NetworkError) {
            throw CustomException.NetworkError()

        } catch (e: Exception) {
            throw CustomException.UnknownError()
        }
    }
}