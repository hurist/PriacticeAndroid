package com.hurist.testapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    val data: MutableLiveData<Int> = MutableLiveData(0)

    fun addNew() {
        data.value = data.value?.plus(1)
    }
}