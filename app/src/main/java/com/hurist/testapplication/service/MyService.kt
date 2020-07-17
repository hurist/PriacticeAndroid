package com.hurist.testapplication.service

import android.app.*
import android.content.ComponentName
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.hurist.testapplication.ui.activity.NotificationActivity

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    fun showMsg() {
        Toast.makeText(this, "这是一个通过binder被访问的service公告方法", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand: ")
        sendBroadcast(Intent("com.hurist.testapplication.notify").apply {
            putExtra("data", "这是service里来的广播")
        })

        val pendingIntent: PendingIntent =
            Intent(this, NotificationActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification: Notification = NotificationCompat.Builder(this, packageName)
            .setContentTitle("前台服务标题")
            .setContentText("前台服务内容")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return MyBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("MyService", "onDestroy: ")
        super.onDestroy()
    }

    inner class MyBinder: Binder() {
        fun getService() = this@MyService
    }

}
