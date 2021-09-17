package ru.chebertests.nasaphoto.model.appstate

import ru.chebertests.nasaphoto.model.remote.ServerResponsePOD

sealed class AppStatePOD {
    data class Success(val serverResponseData: ServerResponsePOD) : AppStatePOD()
    data class Error(val error: Throwable) : AppStatePOD()
    data class Loading(val progress: Int?) : AppStatePOD()
}