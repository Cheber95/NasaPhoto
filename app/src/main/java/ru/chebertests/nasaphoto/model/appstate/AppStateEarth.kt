package ru.chebertests.nasaphoto.model.appstate

import android.graphics.Bitmap

sealed class AppStateEarth {
    data class Success(val drawable: Bitmap, val url: String) : AppStateEarth()
    data class Error(val error: Throwable) : AppStateEarth()
    data class Loading(val progress: Int?) : AppStateEarth()
}
