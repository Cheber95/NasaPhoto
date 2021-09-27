package ru.chebertests.nasaphoto.view

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

open class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun toast(@StringRes resID: Int) {
        Toast.makeText(context, resources.getString(resID), Toast.LENGTH_SHORT).show()
    }

}
