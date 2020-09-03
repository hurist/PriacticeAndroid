package com.hurist.testapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hurist.testapplication.util.dp

/**
 * author: spike
 * version：1.0
 * create data：2020/8/11
 * Description：BezierView
 */
class BezierView : View {

    private val linePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 1.dp
            color = Color.BLACK
        }
    }

    private val controlPoint = PointF(100f, 100f)

    private val path = Path()

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_HARDWARE,null)
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        path.reset()
        path.moveTo(100f, 100f)
        path.quadTo(controlPoint.x, controlPoint.y, width - 100f, height - 100f)
        canvas?.drawPath(path, linePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                controlPoint.x = event.x
                controlPoint.y = event.y
                invalidate()
                return true
            }
        }
        return true
    }
}