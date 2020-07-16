package com.hurist.testapplication.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.hurist.testapplication.R
import com.hurist.testapplication.service.MyService
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(view: View) {
        when(view.id) {
            btnStart.id -> {
                startService(Intent(this, MyService::class.java))
            }
            btnStop.id  -> {
                stopService(Intent(this, MyService::class.java))
            }
            btnForeground.id -> {
                startForegroundService(Intent(this, MyService::class.java))
            }
        }
    }
}