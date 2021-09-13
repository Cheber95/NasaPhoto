package ru.chebertests.nasaphoto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chebertests.nasaphoto.BuildConfig
import ru.chebertests.nasaphoto.model.appstate.AppStatePOD
import ru.chebertests.nasaphoto.model.remote.PictureRetrofit
import ru.chebertests.nasaphoto.model.remote.PictureRetrofitImpl
import ru.chebertests.nasaphoto.model.remote.ServerResponsePOD

private const val API_KEY = BuildConfig.NASA_API_KEY

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppStatePOD> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<AppStatePOD> {
        return liveDataForViewToObserve
    }

    fun getPicture(date: String) {
        sendServerRequest(date)
    }

    private fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = AppStatePOD.Loading(null)
        if (API_KEY.isBlank()) {
            AppStatePOD.Error(Throwable("You API key is Empty"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(API_KEY, date).enqueue(object :
                Callback<ServerResponsePOD> {
                override fun onResponse(
                    call: Call<ServerResponsePOD>,
                    response: Response<ServerResponsePOD>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = AppStatePOD.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value = AppStatePOD.Error(Throwable("error"))
                        } else {
                            liveDataForViewToObserve.value = AppStatePOD.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<ServerResponsePOD>, t: Throwable) {
                    AppStatePOD.Error(t)
                }

            })
        }
    }

}