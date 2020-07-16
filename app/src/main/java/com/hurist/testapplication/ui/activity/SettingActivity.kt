package com.hurist.testapplication.ui.activity

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.util.LifeCycleWatcher
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : BaseActivity() {

    val a = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        btnChinese.setOnClickListener {
            updateLocale(Locale.CHINESE)
        }

        btnEnglish.setOnClickListener {
            updateLocale(Locale.ENGLISH)
        }

        lifecycle.addObserver(LifeCycleWatcher(this))
    }
}