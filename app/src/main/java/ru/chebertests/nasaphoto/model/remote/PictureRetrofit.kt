package ru.chebertests.nasaphoto.model.remote

interface PictureRetrofit {
    fun getRetrofitImpl(): PictureOfTheDayAPI
}