package com.hurist.testapplication.ui.activity

import android.os.Bundle
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.ui.adapter.MainListAdapter
import kotlinx.android.synthetic.main.activity_main_list.*
import kotlinx.android.synthetic.main.activity_toolbar.*

class MainListActivity : BaseActivity() {

    private val datas = listOf(
        Pair("CardView", CardViewActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerview.adapter = MainListAdapter(this, datas)
    }
}