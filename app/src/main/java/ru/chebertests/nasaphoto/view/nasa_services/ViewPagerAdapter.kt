package ru.chebertests.nasaphoto.view.nasa_services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.chebertests.nasaphoto.view.BaseFragment

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private lateinit var fragments: Array<BaseFragment>

    fun setData(data: Array<BaseFragment>) {
        fragments = data
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (fragments[position]) {
            is InfoFragment -> {
                "Инфо"
            }
            is MarsFragment -> {
                "Марс"
            }
            is EarthFragment -> {
                "Земля"
            }
            else -> {
                "НЛО"
            }
        }
    }

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}