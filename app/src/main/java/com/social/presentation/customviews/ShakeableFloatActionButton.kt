package com.social.presentation.customviews

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.social.R
import kotlin.math.abs
import androidx.window.layout.WindowMetricsCalculator


class ShakeableFloatActionButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ExtendedFloatingActionButton(context, attrs, defStyleAttr), View.OnTouchListener {

    private var dX: Float = 0f
    private var dY: Float = 0f
    private var shake: Animation? = null
    private var xMax: Float = 0.0f
    private var yMax: Float = 0.0f
    private var isMoving: Boolean = false
    private var oldMovementX: Float = 0.0f
    private var oldMovementY: Float = 0.0f
    private val movementThreshold = 50

    override fun onFinishInflate() {
        super.onFinishInflate()

        /*val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context as Activity)
        val currentBounds = windowMetrics.bounds
        xMax = currentBounds.width() * 1f
        post {
            if(yMax.compareTo(0.0f) == 0) {
                yMax = y + height
                setOnTouchListener(this)
            }
        }*/

        setOnClickListener { animateFab() }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean
    {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = x - event.rawX
                dY = y - event.rawY
                oldMovementX = event.rawX + dX
                oldMovementY = event.rawY + dY
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.rawX + dX > 0 && ((event.rawX + dX) + measuredWidth < xMax) && (event.rawY + dY > 0) && ((event.rawY + dY) + measuredHeight < yMax)) {
                    animate()
                    .x(event.rawX + dX)
                    .y(event.rawY + dY)
                    .setDuration(0)
                    .start()
                }

                if (abs((event.rawX + dX) - oldMovementX) > movementThreshold || abs((event.rawY + dY) - oldMovementY) > movementThreshold) {
                    isMoving = true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isMoving) {
                    animateFab()
                }
                isMoving = false
            }
        }
        return true
    }

    private fun animateFab(){
        if(shake == null)
            shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
        startAnimation(shake)
    }

}