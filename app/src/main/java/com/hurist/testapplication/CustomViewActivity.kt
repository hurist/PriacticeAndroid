package com.hurist.testapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.view.DoughnutEntry
import com.hurist.testapplication.view.randomColor
import kotlinx.android.synthetic.main.activity_custom_view.*
import java.util.*
import kotlin.random.Random

class CustomViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        btnNew.setOnClickListener {
            doughnutView.datas = generateData()
        }
    }

    private fun generateData(): MutableList<DoughnutEntry> {
        val datas = mutableListOf<DoughnutEntry>()

        for (i in 0..2) {
            val entry = DoughnutEntry(
                100f * i,
                randomColor
            )
            datas.add(entry)
        }
        Log.d(TAG, "generateData: angles $datas")
        return datas
    }
}