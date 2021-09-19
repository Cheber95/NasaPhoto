package ru.chebertests.nasaphoto.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.chebertests.nasaphoto.R

private const val THEME_TAG = "THEME_TAG"
private const val THEME_DEFAULT = "DEFAULT"
private const val THEME_SECOND = "SECOND"

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_theme_selector_group.setOnCheckedChangeListener { group, checkedId ->
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            when(checkedId) {
                settings_theme_selector_one.id -> {
                    editor?.let {
                        it.putString(THEME_TAG, THEME_DEFAULT)
                        it.apply()
                    }
                }
                settings_theme_selector_two.id -> {
                    editor?.let {
                        it.putString(THEME_TAG, THEME_SECOND)
                        it.apply()
                    }
                }
            }
        }

        settings_save_button.setOnClickListener {
            requireActivity().recreate()
        }
    }

    companion object {

        fun newInstance() = SettingsFragment()

    }
}