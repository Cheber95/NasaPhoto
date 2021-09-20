package ru.chebertests.nasaphoto.model.remote

import com.google.gson.annotations.SerializedName

private const val COPYRIGHT = "copyright"
private const val DATE = "date"
private const val EXPLANATION = "explanation"
private const val MEDIA_TYPE = "media_type"
private const val TITLE = "title"
private const val URL = "url"
private const val HD_URL = "hdurl"

private const val PHOTOS = "photos"
private const val MARS_ID = "id"
private const val MARS_SOL = "sol"
private const val MARS_EARTH_DATE = "earth_date"
private const val MARS_IMG_SOURCE = "img_src"

data class ServerResponsePOD(
    @field:SerializedName(COPYRIGHT) val copyright: String?,
    @field:SerializedName(DATE) val date: String?,
    @field:SerializedName(EXPLANATION) val explanation: String?,
    @field:SerializedName(MEDIA_TYPE) val mediaType: String?,
    @field:SerializedName(TITLE) val title: String?,
    @field:SerializedName(URL) val url: String?,
    @field:SerializedName(HD_URL) val hdurl: String?
)

data class MarsResponse(
    @SerializedName(PHOTOS) val photos: List<PhotoMarsResponse>
)

data class PhotoMarsResponse(
    @SerializedName(MARS_ID) val id: Int?,
    @SerializedName(MARS_SOL) val sol: Int?,
    @SerializedName(MARS_EARTH_DATE) val earthDate: String?,
    @SerializedName(MARS_IMG_SOURCE) val imgSrc: String?
)