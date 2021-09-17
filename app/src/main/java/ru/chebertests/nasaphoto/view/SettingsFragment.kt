package ru.chebertests.nasaphoto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import ru.chebertests.nasaphoto.R

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.theme_set_group)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(group.checkedRadioButtonId) {
                R.id.theme_one -> {
                    Toast.makeText(context, "синяя", Toast.LENGTH_SHORT).show()
                    requireActivity().setTheme(R.style.Theme_NasaPhoto)
                }
                R.id.theme_two -> {
                    Toast.makeText(context, "красная", Toast.LENGTH_SHORT).show()
                    requireActivity().setTheme(R.style.Theme_NasaPhotoRed)
                }
            }
        }

        val saveBtn = view.findViewById<Button>(R.id.settings_save_button)
        saveBtn.setOnClickListener {
            activity?.recreate()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }


    companion object {
        fun newInstance() = SettingsFragment()
    }
}