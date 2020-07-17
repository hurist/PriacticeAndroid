package com.hurist.testapplication.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AidlTestService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    inner class AidlBinder
}
