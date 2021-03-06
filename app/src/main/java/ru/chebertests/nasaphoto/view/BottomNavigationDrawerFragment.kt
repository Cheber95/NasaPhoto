package ru.chebertests.nasaphoto.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.navigation_drawer.*
import ru.chebertests.nasaphoto.R

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.navigation_drawer, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_one -> {
                    Toast.makeText(context, "Выбран экран 1", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_two -> {
                    Toast.makeText(context, "Выбран экран 2", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

}