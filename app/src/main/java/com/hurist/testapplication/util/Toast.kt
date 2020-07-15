package com.hurist.testapplication.util

import android.content.Context
import android.widget.Toast

/**
 * author: spike
 * version：1.0
 * create data：2020/7/9
 * Description：Toast
 */
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}