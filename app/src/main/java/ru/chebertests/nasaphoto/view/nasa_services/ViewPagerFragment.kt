package ru.chebertests.nasaphoto.view.nasa_services

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_view_pager.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment

class ViewPagerFragment : BaseFragment(R.layout.fragment_view_pager) {

    private val fragments = arrayOf(InfoFragment(), MarsFragment(), EarthFragment())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = ViewPagerAdapter(this.childFragmentManager)
        viewPagerAdapter.setData(fragments)
        view_pager.adapter = viewPagerAdapter

        view_pager_indicator.setViewPager(view_pager)
        viewPagerAdapter.registerDataSetObserver(view_pager_indicator.dataSetObserver)

        tab_layout.setupWithViewPager(view_pager)
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}