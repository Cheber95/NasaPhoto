package ru.chebertests.nasaphoto.view.nasa_services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.text.format.DateFormat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_earth.*
import ru.chebertests.nasaphoto.BuildConfig
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment
import java.util.*

class EarthFragment : BaseFragment(R.layout.fragment_earth) {

    private val calendarDate = Calendar.getInstance()
    private lateinit var date: String
    private var lat: Double? = null
    private var lon: Double? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarDate.timeInMillis = System.currentTimeMillis()
        date = DateFormat.format("yyyy-MM-dd", calendarDate.time).toString()
        earth_fragment_url.isEnabled = false
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {
            @SuppressLint("CheckResult")
            override fun onLocationChanged(location: Location) {
                lat = location.latitude
                lon = location.longitude
            }
        }

        earth_fragment_button.setOnClickListener {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000L,
                100f,
                locationListener
            )
            val url = "https://api.nasa.gov/planetary/earth/imagery?" +
                    //"lon=$lon&lat=$lat&date=${date}&api_key=${BuildConfig.NASA_API_KEY}&dim=0.25"
                    "lon=$lon&lat=$lat&date=2021-09-22&api_key=${BuildConfig.NASA_API_KEY}&dim=0.25"
            Glide
                .with(earth_fragment_image)
                .load(url)
                .into(earth_fragment_image)
            earth_fragment_url.apply {
                isEnabled = true
                setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    })
                }
            }
        }
    }

    companion object {
        fun newInstance() = MarsFragment()
        private const val REQUEST_CODE = 123
    }
}