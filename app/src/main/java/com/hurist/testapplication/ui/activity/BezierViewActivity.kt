package com.hurist.testapplication.ui.activity

import android.graphics.Point
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hurist.testapplication.R
import kotlinx.android.synthetic.main.activity_bezier_view.*
import java.util.*

class BezierViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bezier_view)

        val pointList: MutableList<Point> =
            ArrayList()
        pointList.add(Point(10, 200))
        pointList.add(Point(110, 300))
        pointList.add(Point(210, 100))
        pointList.add(Point(310, 400))
        pointList.add(Point(410, 100))
        pointList.add(Point(510, 200))
        pointList.add(Point(610, 500))
        bezier.setPointList(pointList)
    }

    fun onClick(v: View?) {
        try {
            bezier.setLineSmoothness(java.lang.Float.valueOf(editText.text.toString()))
        } catch (e: NumberFormatException) {
        }
        bezier.startAnimation(2000)
    }
}