package ru.chebertests.nasaphoto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.chebertests.nasaphoto.BuildConfig
import ru.chebertests.nasaphoto.model.appstate.AppStateMars
import ru.chebertests.nasaphoto.model.remote.PictureRetrofit
import ru.chebertests.nasaphoto.model.remote.PictureRetrofitImpl

private const val API_KEY = BuildConfig.NASA_API_KEY

class MarsFragmentViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppStateMars> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<AppStateMars> {
        return liveDataForViewToObserve
    }

    fun getPicturesFromMars(date: String) {
        liveDataForViewToObserve.value = AppStateMars.Loading(null)
        if (API_KEY.isBlank()) {
            AppStateMars.Error(Throwable("You API key is Empty"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val res = retrofitImpl.getRetrofitImpl().getPicturesFromMars(API_KEY, date)
                if (res.isSuccessful) {
                    liveDataForViewToObserve.postValue(AppStateMars.Success(res.body()!!))
                } else {
                    if (res.message().isEmpty()) {
                        liveDataForViewToObserve.postValue(AppStateMars.Error(Throwable("error")))
                    } else {
                        liveDataForViewToObserve.postValue(AppStateMars.Error(Throwable(res.message())))
                    }
                }
            }
        }
    }
}