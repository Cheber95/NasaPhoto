package ru.chebertests.nasaphoto.view.game

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_botton_nav_view.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment

class BottomNavViewFragment : BaseFragment(R.layout.fragment_botton_nav_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        game_bottom_navigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.game_menu_item_soccer -> {
                    changeFragment(SoccerFragment())
                }
                R.id.game_menu_item_hockey -> {
                    changeFragment(HockeyFragment())
                }
                R.id.game_menu_item_volley -> {
                    changeFragment(VolleyFragment())
                }
                R.id.game_menu_item_basket -> {
                    changeFragment(BasketFragment())
                }
            }
            true
        }
    }

    private fun changeFragment(fragment: BaseFragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.game_container, fragment)
            .commit()
    }

    companion object {
        fun newInstance() = BottomNavViewFragment()
    }
}