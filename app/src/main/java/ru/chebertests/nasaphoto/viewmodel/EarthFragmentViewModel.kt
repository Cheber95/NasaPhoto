package ru.chebertests.nasaphoto.viewmodel

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.chebertests.nasaphoto.BuildConfig
import ru.chebertests.nasaphoto.model.appstate.AppStateEarth
import java.net.HttpURLConnection
import java.net.URL

class EarthFragmentViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppStateEarth> = MutableLiveData()
) : ViewModel() {

    fun getData(): LiveData<AppStateEarth> {
        return liveDataForViewToObserve
    }

    fun getPictureOfEarth(lon: Double, lat: Double, date: String, dim: Double) {
        liveDataForViewToObserve.value = AppStateEarth.Loading(null)

        if (BuildConfig.NASA_API_KEY.isBlank()) {
            AppStateEarth.Error(Throwable("You API key is Empty"))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val urlConnection: HttpURLConnection?
                val url = URL(
                    "https://api.nasa.gov/planetary/earth/imagery?" +
                            "lon=$lon&lat=$lat&date=${date}&api_key=${BuildConfig.NASA_API_KEY}&dim=$dim"
                )
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"

                urlConnection.connect()

                try {
                    val input = urlConnection.inputStream
                    val drawable = BitmapFactory.decodeStream(input)
                    liveDataForViewToObserve.postValue(
                        AppStateEarth.Success(
                            drawable, url.toString()
                        )
                    )
                } catch (e: Exception) {
                    liveDataForViewToObserve.postValue(AppStateEarth.Error(Throwable(e.message)))
                } finally {
                    urlConnection.disconnect()
                }

            }
        }

    }
}