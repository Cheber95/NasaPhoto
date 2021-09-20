package ru.chebertests.nasaphoto.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.chebertests.nasaphoto.R
import java.util.prefs.Preferences

private const val THEME_TAG = "THEME_TAG"
private const val THEME_DEFAULT = "DEFAULT"
private const val THEME_SECOND = "SECOND"


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (this.getPreferences(Context.MODE_PRIVATE).getString(THEME_TAG,THEME_DEFAULT)) {
            THEME_DEFAULT -> {
                setTheme(R.style.Theme_NasaPhoto)
            }
            THEME_SECOND -> {
                setTheme(R.style.Theme_NasaPhotoTwo)
            }
        }

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }

}