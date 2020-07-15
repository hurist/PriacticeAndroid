package com.hurist.testapplication.extension

import android.app.Activity
import android.content.Intent

inline fun <reified T: Activity> Activity.startActivity() {
    this.startActivity(Intent(this, T::class.java))
}