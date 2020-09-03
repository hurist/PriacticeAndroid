package com.hurist.testapplication.util

import android.util.Log
import androidx.lifecycle.*
import com.hurist.testapplication.base.BaseActivity

/**
 * author: spike
 * version：1.0
 * create data：2020/6/28
 * Description：LifeCycleComponent
 */
class LifeCycleWatcher(private val activity: BaseActivity): LifecycleObserver  {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.d("${activity.tag} 生命周期函数==》 ", "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d("${activity.tag} 生命周期函数==》 ", "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d("${activity.tag} 生命周期函数==》 ", "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d("${activity.tag} 生命周期函数==》 ", "onPause")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d("${activity.tag} 生命周期函数==》 ", "onStop")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d("${activity.tag} 生命周期函数==》 ", "onDestroy")
    }

}