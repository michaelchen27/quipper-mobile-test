package model
sealed class CustomException(private val msg: String? = null,  throwable: Throwable? = null) : Exception(msg, throwable) {

    class NetworkError(msg: String? = null, throwable: Throwable? = null) : CustomException(msg, throwable)
    class OfflineError(msg: String? = null, throwable: Throwable? = null) : CustomException(msg, throwable)
    class UnknownError(msg: String? = null, throwable: Throwable? = null) : CustomException(msg, throwable)
}