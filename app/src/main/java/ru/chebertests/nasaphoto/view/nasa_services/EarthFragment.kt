package ru.chebertests.nasaphoto.view.nasa_services

import android.os.Bundle
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment

class EarthFragment : BaseFragment(R.layout.fragment_earth) {

    companion object {
        fun newInstance() = MarsFragment()
    }
}