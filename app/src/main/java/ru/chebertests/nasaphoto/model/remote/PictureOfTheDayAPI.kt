package ru.chebertests.nasaphoto.model.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val ENDPOINT_APOD = "planetary/apod/"
private const val API_KEY_FIELD = "api_key"
private const val DATE = "date"

interface PictureOfTheDayAPI {
    @GET(ENDPOINT_APOD)
    fun getPictureOfTheDay(@Query(API_KEY_FIELD) apiKey: String, @Query(DATE) date: String): Call<ServerResponsePOD>
}