package ru.chebertests.nasaphoto.view

import android.os.Bundle
import ru.chebertests.nasaphoto.R

class InfoFragment : BaseFragment(R.layout.fragment_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance(param1: String, param2: String) = MarsFragment()
    }
}