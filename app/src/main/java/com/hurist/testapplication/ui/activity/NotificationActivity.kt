package com.hurist.testapplication.ui.activity

import android.app.*
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hurist.testapplication.MainActivity
import com.hurist.testapplication.R
import com.hurist.testapplication.receiver.NotifyBroadcastReceiver
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    private val channelId by lazy { packageName }
    private val action by lazy { "$packageName.notify" }
    private val receiver =
        MyNotifyReceiver()

    class MyNotifyReceiver :BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Toast.makeText(context, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        btnCreateChannel.setOnClickListener {
            createNotificationChannel()
        }
        btnNotifyNormal.setOnClickListener {
            notifyNormal()
        }
        btnNotifyPending.setOnClickListener {
            pendingIntentNotify()
        }

        registerReceiver(receiver, IntentFilter(action))
    }


    /**
     * 发送一个可以打开activity的通知
     */
    private fun pendingIntentNotify() {
        val requestCode = 0
        val flag = 0
        //------------------------添加操作按钮start------------------
        //发条广播，貌似只能给静态注册的广播接收器发
        val actionIntent = Intent(this, NotifyBroadcastReceiver::class.java).apply {
            action = this@NotificationActivity.action
            putExtra("data", "静态注册广播通知")
        }
        val actionPendingIntent = PendingIntent.getBroadcast(this, 0, actionIntent, 0)
        //------------------------添加操作按钮end--------------------

        //------------------------通知点击操作start------------------
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, flag)
        //------------------------通知点击操作end--------------------
        val notify = NotificationCompat.Builder(this, channelId).run {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("这是可以点击的通知标题")
            setContentText("这是可以点击的通知内容")
            setContentIntent(pendingIntent) //设置通知点击
            addAction(R.mipmap.ic_launcher, "按钮", actionPendingIntent)  //添加一个操作按钮
            setAutoCancel(true)
            build()
        }

        NotificationManagerCompat.from(this).notify(1, notify)
    }

    private fun notifyNormal() {
        val channelId = packageName
        val notify = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("这是标题的说")
            .setContentText("这是内容的说")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        NotificationManagerCompat.from(this).notify(1, notify)
    }

    /**
     * 创建通知渠道，8.0及以上必须创建渠道才能显示通知，只需要创建一次就可以
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "渠道名称"
            val descriptionText = "渠道描述"
            val channelId = packageName
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}