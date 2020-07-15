package com.hurist.testapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hurist.testapplication.extension.dp
import com.hurist.testapplication.extension.sp
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class ChartView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val outPadding = 16.dp
    private val inPadding = 16.dp

    private val datas by lazy {
        val array = arrayListOf<Int>()
        repeat(50) {
            array.add(Random.nextInt(100))
        }
        return@lazy array
    }

    private val framePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GREEN
            strokeWidth = 1.dp
            style = Paint.Style.STROKE
        }
    }

    private val linePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            strokeWidth = 1.sp
            style = Paint.Style.STROKE
        }
    }

    private val fillPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }

    /**
     * 1、绘制XY轴
     *
     */

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFrame(canvas)
        drawLine(canvas)
    }

    private fun drawFrame(canvas: Canvas) {
        canvas.drawLine(16.dp, 16.dp, 16.dp, height - 16.dp, framePaint)
        canvas.drawLine(16.dp, height - 16.dp, 1000f, height - 16.dp, framePaint)
    }

    /**
     * Y轴
     * 1、先找出最低点和最高点，作为折现图的最低点和最高点，算出两者的差值
     * 2、算出其余点在这个差值区间的位置（减去最低值/差值区间）
     * 3、根据位置算出每个点的Y轴坐标
     *
     * X轴
     * 1、根据数据个数，算出每个转折点的间隔（（宽度-（左Padding+右Padding））/ 数据量）
     * 2、每个转折点的X轴坐标为：左Padding+（当前个数-1）*间隔
     */
    private fun drawLine(canvas: Canvas) {
        val line = Path()
        val maxValue = datas.max()
        val minValue = datas.min()
        val section = maxValue!! - minValue!! //最大最小差值

        val size = datas.size
        val xMargin = (width - (outPadding + inPadding) * 2) / size //点与点的间距
        val yHeight = height - (outPadding + inPadding) * 2 //图表高度 64dp是内边距
        var x = 0f
        var y = 0f
        var lastPointX = 0f
        for (index in 0..datas.lastIndex) {
            if (index == 0) {
                x = (outPadding + inPadding)
                y = height - (outPadding + inPadding) - (((datas[index] - minValue) / section.toFloat()) * yHeight)
                line.moveTo(x, y)
                println("X: $x ; Y:$y")
            } else {
                x = (outPadding + inPadding) + index * xMargin
                y = height - (outPadding + inPadding) - (((datas[index] - minValue) / section.toFloat()) * yHeight)
                lastPointX = x;
                line.lineTo(x, y)
                println("X: $x ; Y:$y")
            }

        }

        canvas.drawPath(line, linePaint)
        line.lineTo(lastPointX, height - (outPadding + inPadding))
        line.lineTo(outPadding + inPadding, height - (outPadding + inPadding))
        line.close()
        fillPaint.shader = LinearGradient(32.dp, 32.dp, height-32.dp, width-32.dp, Color.RED, Color.GREEN, Shader.TileMode.CLAMP)
        canvas.drawPath(line, fillPaint)
    }
}