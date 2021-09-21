package ru.chebertests.nasaphoto.view.nasa_services

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlinx.android.synthetic.main.fragment_mars.loading_item
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.appstate.AppStateMars
import ru.chebertests.nasaphoto.view.BaseFragment
import ru.chebertests.nasaphoto.viewmodel.MarsFragmentViewModel
import java.util.*

class MarsFragment : BaseFragment(R.layout.fragment_mars) {

    private lateinit var viewModel: MarsFragmentViewModel
    private val adapter = MarsPicturesRecyclerViewAdapter()
    private val currentDate = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarsFragmentViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

        currentDate.timeInMillis = System.currentTimeMillis()
        current_earth_date.text = DateFormat.format("dd MMMM yyyy", currentDate.time).toString()
        current_earth_date.setOnClickListener {
            setDate()
        }

        viewModel.getPicturesFromMars(DateFormat.format("yyyy-MM-dd", currentDate.time).toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate() {
        val d = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            currentDate.set(Calendar.YEAR, year)
            currentDate.set(Calendar.MONTH, month)
            currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            current_earth_date.text = DateFormat.format("dd MMMM yyyy", currentDate.time).toString()
            viewModel.getPicturesFromMars(DateFormat.format("yyyy-MM-dd", currentDate.time).toString())
        }
        context?.let {
            DatePickerDialog(
                it,
                d,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun renderData(state: AppStateMars) {
        when (state) {
            is AppStateMars.Success -> {
                mars_recycler_view.visibility = View.VISIBLE
                loading_item.visibility = View.GONE
                val photos = state.serverResponseData.photos
                if (photos.size == 0) {
                    toast("За этот день нет фото")
                } else {
                    toast("За этот день есть ${photos.size} фото поверхности Марса")
                    adapter.setData(photos)
                    mars_recycler_view.adapter = adapter
                    mars_recycler_view.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
            is AppStateMars.Loading -> {
                if (loading_item.visibility != View.VISIBLE) {
                    mars_recycler_view.visibility = View.GONE
                    loading_item.visibility = View.VISIBLE
                }
            }
            is AppStateMars.Error -> {
                state.error.printStackTrace()
                state.error.message?.let { toast(it) }
            }
        }
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}