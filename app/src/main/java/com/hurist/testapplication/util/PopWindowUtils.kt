package com.hurist.testapplication.util

import android.graphics.drawable.BitmapDrawable
import android.view.*
import android.widget.PopupWindow
import com.hurist.testapplication.base.BaseActivity

/**
 * 获取一个设置好了基本属性的popupWindow
 * @param baseActivity
 * @param layoutId      popupWindow布局ID
 * @param dismissTask   用于在popupWindow消失时执行的任务
 */
fun getAPopupWindow(baseActivity: BaseActivity, layoutId:Int, dismissTask: (()->Unit)? = null): PopupWindow {

    val popupWindow = object : PopupWindow(baseActivity) {
        override fun dismiss () {
            super.dismiss()
            val params = baseActivity.window.attributes
            params.alpha = 1.0f
            baseActivity.window.attributes = params
            dismissTask?.invoke()
        }

        override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
            super.showAtLocation(parent, gravity, x, y)
            val attributes = baseActivity.window.attributes
            attributes.alpha = 0.7f
            baseActivity.window.attributes = attributes
        }
    }

    val rootView = LayoutInflater.from(baseActivity).inflate(layoutId, null)
    popupWindow.apply {
        contentView = rootView
        setBackgroundDrawable(null)
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = true
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }
    popupWindow.setBackgroundDrawable(BitmapDrawable())

    return popupWindow
}