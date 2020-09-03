package com.hurist.testapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hurist.testapplication.R
import kotlinx.android.synthetic.main.activity_my_line_chart.*
import kotlin.random.Random

class MyLineChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_line_chart)

        btnStart.setOnClickListener {
            chart.startAnimator(8000)
        }

        btnNew.setOnClickListener {
            val datas = mutableListOf<Float>()
            repeat(10) {
                datas.add(Random.nextFloat() * 100)
            }
            Log.d("MyLineChartActivity", "Datas: $datas")
            chart.datas = datas
        }
    }
}