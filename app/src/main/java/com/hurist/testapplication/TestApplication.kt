package com.hurist.testapplication

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate
import timber.log.Timber


class TestApplication: Application() {

    private val localeAppDelegate = LocaleHelperApplicationDelegate()

    override fun onCreate() {
        super.onCreate()
        appContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeAppDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeAppDelegate.onConfigurationChanged(this)
    }

    companion object {
        lateinit var appContext:Application
            private set
    }
}