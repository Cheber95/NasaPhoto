package ru.chebertests.nasaphoto.view.nasa_services

import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment

class InfoFragment : BaseFragment(R.layout.fragment_info) {

    companion object {
        fun newInstance() = MarsFragment()
    }
}