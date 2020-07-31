package com.hurist.testapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.image.GlideApp

class GlideActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)

        GlideApp.with(this)
            .load("myUrl")
            .placeholder(R.drawable.ic_launcher_background)
            .fitCenter()

    }
}