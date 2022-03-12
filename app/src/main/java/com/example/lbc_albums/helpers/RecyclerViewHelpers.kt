package com.example.lbc_albums.helpers

import android.view.View
import android.view.animation.AlphaAnimation

const val CELL_APPARITION_FADE_DURATION = 200L

fun setFadeAnimation(view: View) {
    val anim = AlphaAnimation(0.0f, 1.0f)
    anim.duration = CELL_APPARITION_FADE_DURATION
    view.startAnimation(anim)
}