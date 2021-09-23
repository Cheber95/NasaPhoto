package ru.chebertests.nasaphoto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<Int>()

    fun select(id: Int) {
        selected.value = id
    }
}