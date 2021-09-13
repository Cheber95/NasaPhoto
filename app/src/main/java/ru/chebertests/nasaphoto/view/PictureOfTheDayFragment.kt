package ru.chebertests.nasaphoto.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.appstate.AppStatePOD
import ru.chebertests.nasaphoto.viewmodel.PictureOfTheDayViewModel
import java.lang.Appendable

class PictureOfTheDayFragment : Fragment() {

    private lateinit var viewModel: PictureOfTheDayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

        return inflater.inflate(R.layout.picture_of_the_day_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun renderData(state: AppStatePOD?) {
        when (state) {
            is AppStatePOD.Success -> {
                val serverResponseData = state.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    // обработать ошибку
                } else {
                    // отобразить фото
                    Glide
                        .with(image_view)
                        .load(url)
                        .into(image_view)
                }
                loading_item.visibility = View.GONE
                image_view.visibility = View.VISIBLE
            }
            is AppStatePOD.Loading -> {
                if (loading_item.visibility != View.VISIBLE) {
                    image_view.visibility = View.GONE
                    loading_item.visibility = View.VISIBLE
                }
            }
            is AppStatePOD.Error -> {
                state.error.printStackTrace()
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

}