package com.hurist.testapplication.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast

const val MSG_HELLO = 0x2333

class MessageService : Service() {

    /**
     * 这种方式相较于直接使用Binder的好处在于可以夸进程
     */
    private lateinit var message: Messenger

    internal class MyHandler(
        private val context: Context
    ): Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_HELLO) {
                Toast.makeText(context, "HELLOOOOOOOOOOOOOOOOOOO", Toast.LENGTH_SHORT).show()
            }
            super.handleMessage(msg)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        message = Messenger(MyHandler(this))
        return message.binder
    }
}
