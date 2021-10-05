package com.android.myapplication.extensions

import android.view.View
import com.airbnb.lottie.LottieAnimationView


fun View.setOnTouchListenerWithoutPerformClick(onTouchListener: View.OnTouchListener?){
    this.setOnTouchListener(onTouchListener)
}