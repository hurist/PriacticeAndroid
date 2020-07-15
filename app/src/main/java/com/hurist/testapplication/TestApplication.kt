package com.hurist.testapplication

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate

class TestApplication: Application() {

    private val localeAppDelegate = LocaleHelperApplicationDelegate()

    override fun onCreate() {
        super.onCreate()
        appContext = this
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