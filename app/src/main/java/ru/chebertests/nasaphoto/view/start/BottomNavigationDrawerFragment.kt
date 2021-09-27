package ru.chebertests.nasaphoto.view.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.navigation_drawer.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.game.BottomNavViewFragment
import ru.chebertests.nasaphoto.view.nasa_services.ViewPagerFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.navigation_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { it ->
            val manager = parentFragmentManager
            when (it.itemId) {
                R.id.navigation_one -> {
                    manager
                        .beginTransaction()
                        .addToBackStack("tag")
                        .replace(R.id.container, ViewPagerFragment())
                        .commit()
                }
                R.id.navigation_two -> {
                    manager
                        .beginTransaction()
                        .addToBackStack("tag")
                        .replace(R.id.container, BottomNavViewFragment())
                        .commit()
                }
            }
            this.dismiss()
            true
        }
    }
}