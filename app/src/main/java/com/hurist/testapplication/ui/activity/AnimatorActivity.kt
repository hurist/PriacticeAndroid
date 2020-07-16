package com.hurist.testapplication.ui.activity

import android.animation.*
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_animator.*
import okhttp3.internal.platform.AndroidPlatform

class AnimatorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
        btnStart.setOnClickListener {
            startObjectAnimator()
        }
    }

    private fun startValueAnimator() {
        ValueAnimator.ofFloat(1f, 1000f).apply {
            duration = 1000
            interpolator = OvershootInterpolator()
            addUpdateListener {
                tvObject.translationX = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                    //动画重播
                }

                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    super.onAnimationEnd(animation, isReverse)
                    //动画终止，是否翻转
                }

                override fun onAnimationEnd(animation: Animator?) {
                    //动画终止
                }

                override fun onAnimationCancel(animation: Animator?) {
                    //动画取消
                }

                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                    super.onAnimationStart(animation, isReverse)
                    //动画开始，是否翻转
                }

                override fun onAnimationStart(animation: Animator?) {
                    //动画开始
                }
            })
            start()
        }
    }

    private fun startObjectAnimator() {
        ObjectAnimator.ofFloat(tvObject, "translationX", 0f, 1000f).apply {
            interpolator = DecelerateInterpolator()
            start()
        }

    }

}