package com.example.englishqq.utils

import android.animation.Animator

interface AnimListener : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator?) {}

    override fun onAnimationEnd(p0: Animator?)

    override fun onAnimationCancel(p0: Animator?) {}

    override fun onAnimationRepeat(p0: Animator?) {}
}