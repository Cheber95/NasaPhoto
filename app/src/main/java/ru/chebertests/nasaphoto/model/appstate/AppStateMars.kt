package ru.chebertests.nasaphoto.model.appstate

import ru.chebertests.nasaphoto.model.remote.MarsResponse

sealed class AppStateMars {
    data class Success(val serverResponseData: MarsResponse) : AppStateMars()
    data class Error(val error: Throwable) : AppStateMars()
    data class Loading(val progress: Int?) : AppStateMars()
}