package ru.chebertests.nasaphoto.view.nasa_services

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_mars.*
import ru.chebertests.nasaphoto.BuildConfig
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment
import java.util.*

private const val REFRESH_TIME = 300_000L
private const val MIN_DISTANCE = 100f
private const val REQUEST_CODE = 42

class EarthFragment : BaseFragment(R.layout.fragment_earth) {

    private val calendarDate = Calendar.getInstance()
    private lateinit var date: String
    private var lat: Double? = null
    private var lon: Double? = null
    private val locationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val locationListener by lazy {
        object : LocationListener {
            @SuppressLint("CheckResult")
            override fun onLocationChanged(location: Location) {
                lat = location.latitude
                lon = location.longitude

                val url = "https://api.nasa.gov/planetary/earth/imagery?" +
                        "lon=$lon&lat=$lat&date=${date}&api_key=${BuildConfig.NASA_API_KEY}&dim=0.25"
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarDate.timeInMillis = System.currentTimeMillis()
        date = DateFormat.format("yyyy-MM-dd", calendarDate.time).toString()
        earth_fragment_url.isEnabled = false
        earth_fragment_current_date.text = DateFormat.format("dd MMMM yyyy", calendarDate.time).toString()

        earth_fragment_button.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 42)
                return@setOnClickListener
            }
            requestLocation()
        }

        earth_fragment_current_date.setOnClickListener {
            setDate()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            REFRESH_TIME,
            MIN_DISTANCE,
            locationListener
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate() {
        val d = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendarDate.set(Calendar.YEAR, year)
            calendarDate.set(Calendar.MONTH, month)
            calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            earth_fragment_current_date.text = DateFormat.format("dd MMMM yyyy", calendarDate.time).toString()
            date = DateFormat.format("yyyy-MM-dd", calendarDate.time).toString()
        }
        context?.let {
            DatePickerDialog(
                it,
                d,
                calendarDate.get(Calendar.YEAR),
                calendarDate.get(Calendar.MONTH),
                calendarDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        requestLocation()
                    }
                }
                return
            }
        }
    }

    companion object {
        fun newInstance() = MarsFragment()
        private const val REQUEST_CODE = 123
    }
}