package com.hurist.testapplication.view

import android.graphics.Color
import kotlin.random.Random

/**
 * 随机获取一个颜色
 */

val randomColor: Int
    get() {
        return Color.rgb(
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255)
        )
    }