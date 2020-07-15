package com.hurist.testapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.hurist.testapplication.R

class Banner(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    init {
        //LayoutInflater.from(context).inflate(R.layout.view_banner, , true)
    }

    private fun addViewPager() {
        val viewPager = ViewPager2(context)

    }

}