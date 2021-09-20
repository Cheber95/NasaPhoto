package ru.chebertests.nasaphoto.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_view_pager.*
import ru.chebertests.nasaphoto.R

class ViewPagerFragment : BaseFragment(R.layout.fragment_view_pager) {

    private val fragments = arrayOf(InfoFragment(), MarsFragment(), EarthFragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = ViewPagerAdapter(this.childFragmentManager)
        viewPagerAdapter.setData(fragments)
        view_pager.adapter = viewPagerAdapter
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}