package com.hurist.testapplication.ui.activity

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hurist.testapplication.R

class WindowActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        val button = Button(this).apply {
            text = "Window"
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            0,
            0,
            PixelFormat.TRANSPARENT
        )
        params.gravity = Gravity.START or Gravity.TOP
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        params.x = 100
        params.y = 300
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        button.setOnTouchListener { v, event ->
            val rawX = event.rawX
            val rawY = event.rawY
            when(event.actionMasked) {
                MotionEvent.ACTION_MOVE -> {
                    params.x = rawX.toInt()
                    params.y = rawY.toInt()
                    windowManager.updateViewLayout(button, params)
                }
            }
            false
        }
        button.setOnClickListener{
            windowManager.removeView(button)
        }
        windowManager.addView(button, params)
    }
}