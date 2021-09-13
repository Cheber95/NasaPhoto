package ru.chebertests.nasaphoto.model.remote

import com.google.gson.annotations.SerializedName

private const val COPYRIGHT = "copyright"
private const val DATE = "date"
private const val EXPLANATION = "explanation"
private const val MEDIA_TYPE = "media_type"
private const val TITLE = "title"
private const val URL = "url"
private const val HD_URL = "hdurl"

data class ServerResponsePOD(
    @field:SerializedName(COPYRIGHT) val copyright: String?,
    @field:SerializedName(DATE) val date: String?,
    @field:SerializedName(EXPLANATION) val explanation: String?,
    @field:SerializedName(MEDIA_TYPE) val mediaType: String?,
    @field:SerializedName(TITLE) val title: String?,
    @field:SerializedName(URL) val url: String?,
    @field:SerializedName(HD_URL) val hdurl: String?
)