package ru.chebertests.nasaphoto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getPicture(date: String?) {
        liveDataForViewToObserve.value = AppStatePOD.Loading(null)
        if (API_KEY.isBlank()) {
            AppStatePOD.Error(Throwable("You API key is Empty"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val res = retrofitImpl.getRetrofitImpl().getPictureOfTheDay(API_KEY, date)
                if (res.isSuccessful) {
                    liveDataForViewToObserve.postValue(AppStatePOD.Success(res.body()!!))
                } else {
                    if (res.message().isEmpty()) {
                        liveDataForViewToObserve.postValue(AppStatePOD.Error(Throwable("error")))
                    } else {
                        liveDataForViewToObserve.postValue(AppStatePOD.Error(Throwable(res.message())))
                    }
                }
            }
        }
    }
}