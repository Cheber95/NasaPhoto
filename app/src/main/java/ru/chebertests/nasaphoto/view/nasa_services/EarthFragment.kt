package ru.chebertests.nasaphoto.view.nasa_services

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_earth.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.appstate.AppStateEarth
import ru.chebertests.nasaphoto.view.BaseFragment
import ru.chebertests.nasaphoto.viewmodel.EarthFragmentViewModel
import java.util.*
import kotlin.properties.Delegates

private const val REFRESH_TIME = 300_000L
private const val MIN_DISTANCE = 100f

class EarthFragment : BaseFragment(R.layout.fragment_earth) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(EarthFragmentViewModel::class.java)
    }
    private val calendarDate = Calendar.getInstance()
    private lateinit var date: String
    private var dim by Delegates.notNull<Double>()
    private var lat: Double? = null
    private var lon: Double? = null
    private val locationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val locationListener by lazy {
        LocationListener { }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startUpdateLocation()
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

        calendarDate.timeInMillis = System.currentTimeMillis()
        date = DateFormat.format("yyyy-MM-dd", calendarDate.time).toString()
        dim = earth_fragment_range.valueFrom.toDouble()
        earth_fragment_url.isEnabled = false
        earth_fragment_current_date.text = "${
            DateFormat.format("dd MMMM yyyy", calendarDate.time)
        } (${getString(R.string.earth_fragment_press_me)})"

        earth_fragment_range.addOnChangeListener { slider, value, fromUser ->
            dim = value.toDouble()
        }

        earth_fragment_button.setOnClickListener {
            requestLocation()
        }

        earth_fragment_current_date.setOnClickListener {
            setDate()
        }
    }

    private fun renderData(state: AppStateEarth) {
        when (state) {
            is AppStateEarth.Success -> {
                Glide
                    .with(earth_fragment_image)
                    .load(state.drawable)
                    .into(earth_fragment_image)

                earth_fragment_loading_item.visibility = View.GONE
                earth_fragment_image.visibility = View.VISIBLE

                earth_fragment_url.apply {
                    isEnabled = true
                    setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(state.url)
                        })
                    }
                }
            }
            is AppStateEarth.Error -> {
                earth_fragment_loading_item.visibility = View.GONE
                earth_fragment_image.visibility = View.VISIBLE
                state.error.apply {
                    printStackTrace()
                    message?.let { toast(it) }
                }
            }
            is AppStateEarth.Loading -> {
                earth_fragment_loading_item.visibility = View.VISIBLE
                earth_fragment_image.visibility = View.GONE
            }
        }
    }

    private fun startUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 42
            )
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            REFRESH_TIME,
            MIN_DISTANCE,
            locationListener
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        location?.let { loc ->
            lat = loc.latitude
            lon = loc.longitude
            viewModel.getPictureOfEarth(lon!!, lat!!, date, dim)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate() {
        val d = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendarDate.set(Calendar.YEAR, year)
            calendarDate.set(Calendar.MONTH, month)
            calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = DateFormat.format("yyyy-MM-dd", calendarDate.time).toString()
            earth_fragment_current_date.text = "${
                DateFormat.format(
                    "dd MMMM yyyy",
                    calendarDate.time
                )
            } (${getString(R.string.earth_fragment_press_me)})"
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
                        startUpdateLocation()
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