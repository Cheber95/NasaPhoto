package ru.chebertests.nasaphoto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.chebertests.nasaphoto.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, PictureOfTheDayFragment.newInstance())
            .commit()
    }
}