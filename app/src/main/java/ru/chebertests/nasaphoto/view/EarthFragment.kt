package ru.chebertests.nasaphoto.view

import android.os.Bundle
import ru.chebertests.nasaphoto.R

class EarthFragment : BaseFragment(R.layout.fragment_earth) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}