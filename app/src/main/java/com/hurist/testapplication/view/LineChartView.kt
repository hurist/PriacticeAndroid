package com.hurist.testapplication.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.*
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.hurist.testapplication.R


private const val TAG = "LineChartView"

/**
 * TODO: document your custom view class.
 */
class LineChartView : View {

    private var frameColor = Color.BLUE
    private var frameWidth = 1.dp
    private lateinit var framePaint: Paint
    private lateinit var linePaint: Paint
    private val shadowPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 1f
//            color = Color.RED
//            style = Paint.Style.STROKE

            shader = LinearGradient(
                0f,
                0f,
                0f,
                height.toFloat(),
                intArrayOf(
                    Color.parseColor("#BAEFE6"),
                    Color.parseColor("#D7F5F0"),
                    Color.parseColor("#F9FEFD")
                ),
                floatArrayOf(0.1f, 0.2f, 0.7f),
                Shader.TileMode.CLAMP
            )
        }
    }
    private lateinit var endPointPaint: Paint

    public var datas: MutableList<Float> = mutableListOf()
        set(value) {
            field = value
            postInvalidate()
        }

//            by lazy {
//        val list = mutableListOf<Float>()
//        repeat(10) {
//            list.add(Random.nextFloat() * 100)
//        }
//        Log.d(TAG, "Datas:$list ")
//        return@lazy list
//    }

    private var datasPoint = mutableListOf<PointF>()

    private var animatorScale = 1.0f
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.LineChartView, defStyle, 0
        )

        framePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = frameColor
            strokeWidth = frameWidth
            style = Paint.Style.STROKE
        }
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = frameColor
            strokeWidth = 2.dp
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.BEVEL
        }
        endPointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = frameColor
            strokeWidth = 1.dp
            style = Paint.Style.STROKE
        }

        a.recycle()
    }

    @SuppressLint("LogNotTimber")
    private fun calc(): MutableList<PointF> {
        val points = mutableListOf<PointF>()
        if (datas.size > 0) {
            val dis = datas.maxOrNull()!! - datas.minOrNull()!!
            //最小点到最大点的距离
            val ds = height - 10.dp * 4
            //每个点之间的间距 = （宽度 - 左右边框距离 + 左右边框到点的距离）/ (数据量 - 1)
            val xSpace = (width - 10.dp * 4) / (datas.size - 1)
            var index = 0
            datas.forEach {
                //当前数据与最大值的差
                val a = datas.maxOrNull()!! - it
                //数据点的Y坐标 = （差 / 最大最小值的差 * 最大最小点的距离） + （上边框距离 + 最高点到边框的距离）
                val y = (a / dis * ds) + (10.dp * 2)
                val x = 20.dp + index * xSpace
                index += 1
                points.add(PointF(x, y))
                Log.d(
                    TAG,
                    "calc: $x/$y isMax: ${datas.maxOrNull() == it} isMin：${datas.minOrNull() == it}"
                )
            }
        }
        return points
    }

    fun startAnimator(time: Long) {
        val animator = ObjectAnimator.ofFloat(this, "animatorScale", 0f, 1f)
        animator.duration = time
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE);
        canvas.drawFilter =
            PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        //边框
        canvas.drawLine(10.dp, 10.dp, 10.dp, height - 10.dp, framePaint)
        canvas.drawLine(10.dp, height - 10.dp, width - 10.dp, height - 10.dp, framePaint)
        //真实折线路径计算
        datasPoint = calc()
        if (datasPoint.size > 0) {
            val pointPath = Path()
            for (index in datasPoint.indices) {
                if (index == 0) {
                    pointPath.moveToPointF(datasPoint[index])
                } else {
                    pointPath.lineToPointF(datasPoint[index])
                }
            }
            //动画路径
            val pathMeasure = PathMeasure(pointPath, false)
            val desLength = pathMeasure.length * animatorScale
            val path = Path()
            pathMeasure.getSegment(0f, desLength, path, true)
            canvas.drawPath(path, linePaint)
            //路径终点
            val endpointPos = floatArrayOf(0f, 0f)
            pathMeasure.getPosTan(desLength, endpointPos, floatArrayOf(0.0f, 0.0f))
            val lastPointF1 = PointF(endpointPos[0], endpointPos[1])
            //圈出一个封闭区域绘制渐变
            path.lineTo(lastPointF1.x, height - 20.dp)
            path.lineTo(20.dp, height - 20.dp)
            path.close()
            canvas.drawPath(path, shadowPaint)

            //绘制终点圆圈
            endPointPaint.color = frameColor
            endPointPaint.style = Paint.Style.FILL
            canvas.drawCircle(lastPointF1.x, lastPointF1.y, 2.dp, endPointPaint)
            endPointPaint.color = Color.WHITE
            endPointPaint.style = Paint.Style.FILL
            canvas.drawCircle(lastPointF1.x, lastPointF1.y, 1.dp, endPointPaint)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }


    private fun Path.moveToPointF(pointF: PointF) {
        moveTo(pointF.x, pointF.y)
    }

    private fun Path.lineToPointF(pointF: PointF) {
        lineTo(pointF.x, pointF.y)
    }


    private val Int.dp: Float
        get() {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                context.resources.displayMetrics
            )
        }

}
