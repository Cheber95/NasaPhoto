package ru.chebertests.nasaphoto.view.start

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.appstate.AppStatePOD
import ru.chebertests.nasaphoto.view.BaseFragment
import ru.chebertests.nasaphoto.view.MainActivity
import ru.chebertests.nasaphoto.viewmodel.PictureOfTheDayViewModel
import java.util.*

class PictureOfTheDayFragment : BaseFragment(R.layout.picture_of_the_day_fragment) {

    private lateinit var viewModel: PictureOfTheDayViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var date = Calendar.getInstance()
    private val beforeYesterday by lazy {
        getString(R.string.before_yesterday)
    }
    private val yesterday by lazy {
        getString(R.string.yesterday)
    }
    private val today by lazy {
        getString(R.string.today)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

        date.timeInMillis = System.currentTimeMillis()
        // для загрузки видео использовать дату "2021-09-17" - последняя дата, когда приходило видео
        viewModel.getPicture(null)

        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://${getString(R.string.wiki_lang)}.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        chip_group.setOnCheckedChangeListener { chip_group, position ->
            chip_group.findViewById<Chip>(position)?.let {
                image_view.setOnClickListener { null }
                viewModel.getPicture(dateFormatter(it.text as String))
            }
        }

        setBottomSheetBehavior(bottom_sheet_container)
        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                toast("В методичке предложено сделать раздел избранное")
            }
            R.id.app_bar_settings -> {
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack("tag")
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        image_view.setOnClickListener { null }
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        setHasOptionsMenu(false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun renderData(state: AppStatePOD?) {
        when (state) {
            is AppStatePOD.Success -> {
                val serverResponseData = state.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Glide
                        .with(image_view)
                        .load(R.drawable.ic_banner_foreground)
                        .into(image_view)
                    toast("image not found")
                } else {
                    if (url.substringAfterLast(".") == "jpg") {
                        Glide
                            .with(image_view)
                            .load(url)
                            .into(image_view)
                    } else {
                        val playIcon = context?.getDrawable(R.drawable.ic_baseline_play)
                        Glide
                            .with(image_view)
                            .load(playIcon)
                            .into(image_view)
                        image_view.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(url)
                            })
                        }
                    }
                    title.text = serverResponseData.title

                    bottom_sheet_title.text = serverResponseData.title
                    bottom_sheet_description.text = serverResponseData.explanation
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
                state.apply {
                    loading_item.visibility = View.GONE
                    image_view.visibility = View.VISIBLE
                    error.printStackTrace()
                    error.message?.let { toast(it) }
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }

    private fun dateFormatter(day: String): String? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.timeZone = TimeZone.getTimeZone("Etc/GMT-7")
        when (day) {
            beforeYesterday -> {
                calendar.add(Calendar.DAY_OF_MONTH, -2)
            }
            yesterday -> {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
            }
            today -> {
                return null
            }
        }
        return DateFormat.format("yyyy-MM-dd", calendar.time).toString()
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

}