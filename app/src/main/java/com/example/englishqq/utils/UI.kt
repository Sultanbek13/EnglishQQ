package com.example.englishqq.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Pulse
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit

val load: Sprite = Pulse()

fun toast(context: Context, str: String) {
    Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
}

fun loading(loadingView: SpinKitView, isLoading: Boolean) {
    loadingView.setIndeterminateDrawable(load)
    loadingView.isVisible = isLoading
}

fun setDots(dotsView: DotsIndicator, pager: ViewPager2) {
    dotsView.attachTo(pager)
}

fun playConfettiAnim(anim: KonfettiView) {
    val party = Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 360,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        emitter = Emitter(duration = 1000, TimeUnit.MILLISECONDS).max(100),
        position = Position.Relative(0.5, 0.3)
    )
    anim.start(party)
}