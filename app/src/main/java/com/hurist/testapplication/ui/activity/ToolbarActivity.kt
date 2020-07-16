package com.hurist.testapplication.ui.activity

import android.os.Bundle
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolbarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}