package com.hurist.testapplication.receiver

import android.content.*
import android.widget.Toast

/**
 * author: spike
 * version：1.0
 * create data：2020/7/14
 * Description：NotifyBroadcastReceiver
 * receive: com.hurist.testapplication.notify
 */
class NotifyBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            Toast.makeText(context, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show()
        }
    }
}