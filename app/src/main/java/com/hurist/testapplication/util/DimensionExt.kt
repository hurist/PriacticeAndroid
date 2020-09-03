package com.hurist.testapplication.util;

import android.util.TypedValue
import com.hurist.testapplication.TestApplication

val Int.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            TestApplication.appContext.resources.displayMetrics
        )
    }

val Int.sp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            TestApplication.appContext.resources.displayMetrics
        )
    }

val Int.px: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            this.toFloat(),
            TestApplication.appContext.resources.displayMetrics
        )
    }

val Float.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            TestApplication.appContext.resources.displayMetrics
        )
    }

val Float.sp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this,
            TestApplication.appContext.resources.displayMetrics
        )
    }

val Float.px: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            this,
            TestApplication.appContext.resources.displayMetrics
        )
    }