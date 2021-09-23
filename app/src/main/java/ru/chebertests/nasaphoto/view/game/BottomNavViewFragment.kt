package ru.chebertests.nasaphoto.view.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_botton_nav_view.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment
import ru.chebertests.nasaphoto.viewmodel.SharedViewModel

private const val FRAGMENTS_COUNT = 4
private const val MAX_CHARACTER_COUNT = 2

class BottomNavViewFragment : BaseFragment(R.layout.fragment_botton_nav_view) {

    private val model: SharedViewModel by activityViewModels()

    private val SOCCER_ID by lazy {
        resources.getInteger(R.integer.soccer)
    }
    private val HOCKEY_ID by lazy {
        resources.getInteger(R.integer.hockey)
    }
    private val VOLLEY_ID by lazy {
        resources.getInteger(R.integer.volley)
    }
    private val BASKET_ID by lazy {
        resources.getInteger(R.integer.basket)
    }

    private val fragments = mutableListOf<SportFragment>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0 until FRAGMENTS_COUNT) {
            fragments.add(SportFragment.newInstance(i))
        }

        changeFragment(fragments.first())

        model.selected.observe(viewLifecycleOwner, { renderData(it) })

        game_bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.game_menu_item_soccer -> {
                    changeFragment(fragments[SOCCER_ID])
                }
                R.id.game_menu_item_hockey -> {
                    changeFragment(fragments[HOCKEY_ID])
                }
                R.id.game_menu_item_volley -> {
                    changeFragment(fragments[VOLLEY_ID])
                }
                R.id.game_menu_item_basket -> {
                    changeFragment(fragments[BASKET_ID])
                }
            }
            game_bottom_navigation.removeBadge(it.itemId)
            true
        }
    }

    private fun renderData(data: Int) {
        when (data) {
            SOCCER_ID -> {
                increaseBadge(R.id.game_menu_item_soccer, R.string.game_football)
            }
            HOCKEY_ID -> {
                increaseBadge(R.id.game_menu_item_hockey, R.string.game_hockey)
            }
            VOLLEY_ID -> {
                increaseBadge(R.id.game_menu_item_volley, R.string.game_volleyball)
            }
            BASKET_ID -> {
                increaseBadge(R.id.game_menu_item_basket, R.string.game_basket)
            }
        }
    }

    private fun changeFragment(fragment: BaseFragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.game_container, fragment)
            .commit()
    }

    private fun increaseBadge(resID: Int, stringResID: Int) {
        var badgeNumber = game_bottom_navigation.getOrCreateBadge(resID).number
        if (badgeNumber == 0) {
            game_bottom_navigation.getOrCreateBadge(resID).number = 1
            game_bottom_navigation.getOrCreateBadge(resID).maxCharacterCount = MAX_CHARACTER_COUNT
        } else {
            badgeNumber++
            game_bottom_navigation.getOrCreateBadge(resID).number = badgeNumber
            if (badgeNumber.toString().length == MAX_CHARACTER_COUNT) {
                toast("Самый лучший вид спорта - ${getString(stringResID)}")

            }
        }
    }

    companion object {
        fun newInstance() = BottomNavViewFragment()
    }
}