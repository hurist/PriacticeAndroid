package com.hurist.testapplication.util

import android.graphics.*

/**
 * author: spike
 * version：1.0
 * create data：2020/8/11
 * Description：DrawUtils
 */
object DrawUtil {
    /**
     * 绘制穿过多边形顶点的平滑曲线
     * 用三阶贝塞尔曲线实现
     * @param canvas 画布
     * @param points 多边形的顶点
     * @param k 控制点系数，系数越小，曲线越锐利
     * @param color 线条颜色
     * @param strokeWidth 线条宽度
     */
    fun drawCurvesFromPoints(
        canvas: Canvas,
        points: List<Point>,
        k: Double,
        color: Int,
        strokeWidth: Float
    ) {
        val size = points.size
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // 计算中点
        val midPoints = arrayOfNulls<Point>(size)
        for (i in 0 until size) {
            val p1 = points[i]
            val p2 = points[(i + 1) % size]
            midPoints[i] = Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2)
        }

        // 计算比例点
        val ratioPoints = arrayOfNulls<Point>(size)
        for (i in 0 until size) {
            val p1 = points[i]
            val p2 = points[(i + 1) % size]
            val p3 = points[(i + 2) % size]
            val l1 = distance(p1, p2)
            val l2 = distance(p2, p3)
            val ratio = l1 / (l1 + l2)
            val mp1 = midPoints[i]
            val mp2 = midPoints[(i + 1) % size]
            ratioPoints[i] = ratioPointConvert(mp2, mp1, ratio)
        }

        // 移动线段，计算控制点
        val controlPoints = arrayOfNulls<Point>(size * 2)
        run {
            var i = 0
            var j = 0
            while (i < size) {
                val ratioPoint = ratioPoints[i]
                val verPoint = points[(i + 1) % size]
                val dx = ratioPoint!!.x - verPoint.x
                val dy = ratioPoint.y - verPoint.y
                val controlPoint1 = Point(midPoints[i]!!.x - dx, midPoints[i]!!.y - dy)
                val controlPoint2 = Point(
                    midPoints[(i + 1) % size]!!.x - dx,
                    midPoints[(i + 1) % size]!!.y - dy
                )
                controlPoints[j++] = ratioPointConvert(controlPoint1, verPoint, k)
                controlPoints[j++] = ratioPointConvert(controlPoint2, verPoint, k)
                i++
            }
        }

        // 用三阶贝塞尔曲线连接顶点
        val path = Path()
        paint.color = color
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
        for (i in 0 until size) {
            val startPoint = points[i]
            val endPoint = points[(i + 1) % size]
            val controlPoint1 =
                controlPoints[(i * 2 + controlPoints.size - 1) % controlPoints.size]
            val controlPoint2 = controlPoints[i * 2 % controlPoints.size]
            path.reset()
            path.moveTo(startPoint.x.toFloat(), startPoint.y.toFloat())
            path.cubicTo(
                controlPoint1!!.x.toFloat(),
                controlPoint1.y.toFloat(),
                controlPoint2!!.x.toFloat(),
                controlPoint2.y.toFloat(),
                endPoint.x.toFloat(),
                endPoint.y.toFloat()
            )
            canvas.drawPath(path, paint)
        }
    }

    /**
     * 计算两点之间的距离
     */
    private fun distance(p1: Point, p2: Point): Double {
        return Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)).toDouble())
    }

    /**
     * 比例点转换
     */
    private fun ratioPointConvert(p1: Point?, p2: Point?, ratio: Double): Point {
        val p = Point()
        p.x = (ratio * (p1!!.x - p2!!.x) + p2.x).toInt()
        p.y = (ratio * (p1.y - p2.y) + p2.y).toInt()
        return p
    }
}