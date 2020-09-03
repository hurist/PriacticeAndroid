package com.hurist.testapplication.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class ToolbarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->

        }

        lifecycleScope.launch {
            suspendCancellableCoroutine<Unit> {  }

        }
    }
}