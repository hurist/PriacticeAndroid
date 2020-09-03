package com.hurist.testapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.toRectF
import com.hurist.testapplication.R


/**
 * author: spike
 * version：1.0
 * create data：2020/7/1
 * Description：DoughnutView
 * 圆环图
 */
class DoughnutView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    /** 开始角度 */
    private var startAngle: Float = 0f

    /** 间隔角度 */
    private var intervalAngle = 0f

    /** 最小角度,默认最小一度，设置为0则按计算出来的真实度数显示 */
    private var minAngle = 0f

    /** 除去间隔后的角度 */
    private var remainAngle = 360f

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DoughnutView)
        startAngle = typedArray.getFloat(R.styleable.DoughnutView_startAngle, 0f)
        intervalAngle = typedArray.getFloat(R.styleable.DoughnutView_intervalAngle, 0f)
        minAngle = typedArray.getFloat(R.styleable.DoughnutView_minAngle, 1f)
        typedArray.recycle()
    }

    private val tag = "DoughnutView"


    var datas = listOf<DoughnutEntry>()
        set(value) {
            remainAngle = 360f
            field = value.filter { it.value > 0 }
            if (field.isNotEmpty()) {
                remainAngle = if (field.size == 1) 360f else 360f - field.size * intervalAngle
            }
            invalidate()
        }

    private val rectF by lazy {
        Rect(0, 0, width, height).toRectF()
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (datas.isEmpty()) {
            canvas.drawColor(Color.WHITE)
            return
        }
        for (p in getAngleAndColors()) {
            paint.color = p.second
            canvas.drawArc(rectF, startAngle, p.first, true, paint)
            startAngle += p.first + intervalAngle
        }

        //在中间画一个白色的圆实现空心的效果
        paint.color = Color.WHITE
        canvas.drawCircle(width / 2f, height / 2f, height / 4f, paint)
    }

    /**
     * 将扇形的角度和颜色匹配，为绘制提供数值
     */
    private fun getAngleAndColors(): MutableList<Pair<Float, Int>> {
        val angleAndColors = mutableListOf<Pair<Float, @ColorInt Int>>()
        val values = datas.map { it.value }
        val angles = calcAngles(values)
        for (i in calcAngles(values).indices) {
            angleAndColors.add(angles[i] to datas[i].color)
        }
        return angleAndColors
    }


    /**
     * 每个数字占总数的比值，去计算每个数字所占的角度，这里拿来分配的角度是[remainAngle],是一个
     * 扣除了各个扇形之间间隔的角度值，整个的计算思路是：
     *      1、计算所有数字之和[valueSum]、每一度扇形对应多少数值[perDegreeValue]
     *      2、计算每个数值和1度扇形对应的数值的比值，得到它的扇形角度
     *      3、处理误差
     */
    private fun calcAngles(values: List<Float>): MutableList<Float> {
        val valueSum = values.sum()
        val angles = mutableListOf<Float>()

        //每度等于总数的多少
        val perDegreeValue = valueSum / remainAngle

        for (data in values) {
            val angle = when {
                data <= 0f -> {
                    0f
                }
                data > perDegreeValue -> {
                    data / perDegreeValue
                }
                else -> {
                    if (minAngle != 0f) minAngle else data / perDegreeValue
                }
            }
            angles.add(angle)
        }

        return dealDiff(remainAngle, angles)
    }

    /**
     * 处理计算误差，[targetValue]是期望的总和，[sourceValues]是实际待处理误差的数据源，
     * 通过计算[sourceValues]的总和减去[targetValue]得到实际的误差，把误差从[sourceValues]
     * 中最大的数中抹平
     */
    private fun dealDiff(targetValue: Float, sourceValues: MutableList<Float>): MutableList<Float> {
        var sum = sourceValues.sum()
        Log.d(tag, "处理前的度数总和 $sum 目标度数总和 $remainAngle")
        /**
         * 因为最小显示度数的设置以及精度问题，最终计算出来的度数总和可能不等于[remainAngle]，
         * 所以在这里把误差从占比最大的角度里抹平
         */
        val diff = targetValue - sum
        Log.d(tag, "误差 $diff")
        val max = sourceValues.max()
        val maxIndex = sourceValues.indexOf(max)
        sourceValues[maxIndex] = sourceValues[maxIndex] + diff
        Log.d(tag, "处理后的度数总和: ${sourceValues.sum()}")
        return sourceValues
    }


    /**
     * [widthMeasureSpec], [heightMeasureSpec]是父view对当前view的宽高限制
     * 就是父view把开发者对当前view尺寸的要求进行处理计算之后得到的更精准的要求，
     * 开发者的要求就是那些在布局文件中以layout_开头的哪些属性，这些属性都是给父view看的，
     * 父view会去读取这些属性，用它们进行处理计算
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}


data class DoughnutEntry(val value: Float, val color: Int) : Comparable<DoughnutEntry> {
    override fun compareTo(other: DoughnutEntry): Int {
        return when {
            this.value == other.value -> {
                0
            }
            this.value > other.value -> {
                1
            }
            else -> {
                -1
            }
        }
    }
}
