package com.hurist.testapplication.ui.activity

import android.app.PendingIntent
import android.content.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.annotation.RequiresApi
import com.hurist.testapplication.R
import com.hurist.testapplication.receiver.NotifyBroadcastReceiver
import com.hurist.testapplication.service.*
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    private var isBind = false
    private val receiver = NotifyBroadcastReceiver()
    private val serviceIntent by lazy { Intent(this, MyService::class.java) }
    private val serviceConnection = object : ServiceConnection {

        /**
         * 只有以外终止才会被调用
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            isBind = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBind = true
            val binder = service as MyService.MyBinder
            binder.getService().showMsg()
        }

    }

    private var messenger: Messenger? = null
    private val msgServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBind = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBind = true
            messenger = Messenger(service)
            val message = Message.obtain(null, MSG_HELLO)
            messenger?.send(message)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        registerReceiver(receiver, IntentFilter("com.hurist.testapplication.notify"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(view: View) {
        when(view.id) {
            btnStart.id -> {
                startService(serviceIntent)
            }
            btnStop.id  -> {
                stopService(serviceIntent)
            }
            btnForeground.id -> {
                //startForegroundService(Intent(this, MyService::class.java))
            }
            btnBind.id -> {
                bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
            btnUnbind.id -> {
                if (isBind) {
                    unbindService(serviceConnection)
                    isBind = false
                }
            }
            btnBindMsg.id -> {
                val intent = Intent(this, MessageService::class.java)
                bindService(intent, msgServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}