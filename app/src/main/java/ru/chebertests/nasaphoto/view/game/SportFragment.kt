package ru.chebertests.nasaphoto.view.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_sport.*
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.view.BaseFragment
import ru.chebertests.nasaphoto.viewmodel.SharedViewModel

private const val ON_MENU_ITEM_ID = R.id.game_menu_item_soccer

class SportFragment() : BaseFragment(R.layout.fragment_sport) {

    private val model : SharedViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sportID = arguments?.getInt("TAG")
        when(sportID) {
            SOCCER_ID -> {
                game_sport_title.text = getString(R.string.game_football)
                game_sport_button.text = getString(R.string.game_soccer_button_text)
            }
            HOCKEY_ID -> {
                game_sport_title.text = getString(R.string.game_hockey)
                game_sport_button.text = getString(R.string.game_hockey_button_text)
            }
            VOLLEY_ID -> {
                game_sport_title.text = getString(R.string.game_volleyball)
                game_sport_button.text = getString(R.string.game_volley_button_text)
            }
            BASKET_ID -> {
                game_sport_title.text = getString(R.string.game_basket)
                game_sport_button.text = getString(R.string.game_basket_button_text)
            }
        }
        game_sport_imageview.visibility = View.GONE

        val arrayToRand = arrayOf(SOCCER_ID, HOCKEY_ID, VOLLEY_ID, BASKET_ID)
        game_sport_button.setOnClickListener {
            val index = (Math.random()*4).toInt()
            val transmitData = arrayToRand[index]
            model.select(transmitData)
        }
    }

    companion object {
        fun newInstance(sportID: Int) : SportFragment {
            val fragment = SportFragment()
            val bundle = Bundle()
            bundle.putInt("TAG", sportID)
            fragment.arguments = bundle
            return fragment
        }
    }
}