package com.hurist.testapplication.viewmodel

import androidx.lifecycle.*
import com.hurist.testapplication.network.Retrofit
import com.hurist.wanandroid.data.responseData.UserInfo
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * author: spike
 * version：1.0
 * create data：2020/7/10
 * Description：NetTestViewModel
 */
class NetTestViewModel: ViewModel() {

    val userInfo = MutableLiveData<Response<UserInfo>>()

    fun login() {
        viewModelScope.launch {
            userInfo.value = Retrofit.wanAndroid.login("yanhe", "123456", "null")
        }
    }
}