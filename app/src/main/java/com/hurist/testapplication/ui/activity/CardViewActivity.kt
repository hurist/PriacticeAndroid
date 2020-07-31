package com.hurist.testapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.ui.adapter.MainListAdapter
import kotlinx.android.synthetic.main.activity_main_list.*

class CardViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)
    }
}