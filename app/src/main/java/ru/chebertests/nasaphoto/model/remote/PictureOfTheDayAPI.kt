package ru.chebertests.nasaphoto.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val ENDPOINT_APOD = "planetary/apod/"
private const val API_KEY_FIELD = "api_key"
private const val DATE = "date"
private const val ENDPOINT_MARS = "mars-photos/api/v1/rovers/curiosity/photos/"
private const val MARS_EARTH_DATE = "earth_date"

interface PictureOfTheDayAPI {
    @GET(ENDPOINT_APOD)
    suspend fun getPictureOfTheDay(
        @Query(API_KEY_FIELD) apiKey: String,
        @Query(DATE) date: String?
    ): Response<ServerResponsePOD>

    @GET(ENDPOINT_MARS)
    suspend fun getPicturesFromMars(
        @Query(API_KEY_FIELD) apiKey: String,
        @Query(MARS_EARTH_DATE) earthDate: String
    ): Response<MarsResponse>
}