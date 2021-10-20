package com.social.presentation.customviews

import android.view.ScaleGestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.graphics.drawable.Drawable
import android.view.View.OnTouchListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.abs

class TouchImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null): AppCompatImageView(context, attrs) {

    init {
        sharedConstructing(context)
    }
    private var mMatrix: Matrix? = null
    private var mMode = NONE

    // Remember some things for zooming
    private val last = PointF()
    private val start = PointF()
    private var minScale = 1f
    private var maxScale = 3f
    private lateinit var m: FloatArray
    private var viewWidth = 0
    private var viewHeight = 0
    private var saveScale = 1f
    protected var origWidth = 0f
    protected var origHeight = 0f
    private var oldMeasuredWidth = 0
    private var oldMeasuredHeight = 0
    private var mScaleDetector: ScaleGestureDetector? = null

    private fun sharedConstructing(context: Context) {
        super.setClickable(true)

        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
        mMatrix = Matrix()
        m = FloatArray(9)
        imageMatrix = mMatrix
        scaleType = ScaleType.MATRIX
        setOnTouchListener { _, event ->
            mScaleDetector!!.onTouchEvent(event)
            val curr = PointF(event.x, event.y)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    last.set(curr)
                    start.set(last)
                    mMode = DRAG
                }
                MotionEvent.ACTION_MOVE -> if (mMode == DRAG) {
                    val deltaX = curr.x - last.x
                    val deltaY = curr.y - last.y
                    val fixTransX = getFixDragTrans(
                        deltaX, viewWidth.toFloat(),
                        origWidth * saveScale
                    )
                    val fixTransY = getFixDragTrans(
                        deltaY, viewHeight.toFloat(),
                        origHeight * saveScale
                    )
                    mMatrix!!.postTranslate(fixTransX, fixTransY)
                    fixTrans()
                    last[curr.x] = curr.y
                }
                MotionEvent.ACTION_UP -> {
                    mMode = NONE
                    val xDiff = abs(curr.x - start.x).toInt()
                    val yDiff = abs(curr.y - start.y).toInt()
                    if (xDiff < CLICK && yDiff < CLICK) performClick()
                }
                MotionEvent.ACTION_POINTER_UP -> mMode = NONE
            }
            imageMatrix = mMatrix
            invalidate()
            true // indicate event was handled
        }
    }

    fun setMaxZoom(x: Float) {
        maxScale = x
    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mMode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val origScale = saveScale
            saveScale *= mScaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                mScaleFactor = maxScale / origScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                mScaleFactor = minScale / origScale
            }
            if (origWidth * saveScale <= viewWidth
                    || origHeight * saveScale <= viewHeight) mMatrix!!.postScale(mScaleFactor, mScaleFactor, (viewWidth / 2).toFloat(), (
                    viewHeight / 2).toFloat()) else mMatrix!!.postScale(mScaleFactor, mScaleFactor,
                    detector.focusX, detector.focusY)
            fixTrans()
            return true
        }
    }

    fun fixTrans() {
        mMatrix!!.getValues(m)
        val transX = m[Matrix.MTRANS_X]
        val transY = m[Matrix.MTRANS_Y]
        val fixTransX = getFixTrans(transX, viewWidth.toFloat(), origWidth * saveScale)
        val fixTransY = getFixTrans(transY, viewHeight.toFloat(), origHeight
                * saveScale)
        if (fixTransX != 0f || fixTransY != 0f) mMatrix!!.postTranslate(fixTransX, fixTransY)
    }

    private fun getFixTrans(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) {
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else {
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }
        if (trans < minTrans) return -trans + minTrans
        return if (trans > maxTrans) -trans + maxTrans else 0f
    }

    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
        return if (contentSize <= viewSize) {
            0f
        } else delta
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        //
        // Rescales image on rotation
        //
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight || viewWidth == 0 || viewHeight == 0) return
        oldMeasuredHeight = viewHeight
        oldMeasuredWidth = viewWidth
        if (saveScale == 1f) {
            // Fit to screen.
            val scale: Float
            val drawable: Drawable = drawable
            if (drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) return
            val bmWidth = drawable.intrinsicWidth
            val bmHeight = drawable.intrinsicHeight
            val scaleX = viewWidth.toFloat() / bmWidth.toFloat()
            val scaleY = viewHeight.toFloat() / bmHeight.toFloat()

            scale = scaleX.coerceAtMost(scaleY)

            mMatrix!!.setScale(scale, scale)

            // Center the image
            var redundantYSpace = (viewHeight.toFloat()
                    - scale * bmHeight.toFloat())
            var redundantXSpace = (viewWidth.toFloat()
                    - scale * bmWidth.toFloat())
            redundantYSpace /= 2.toFloat()
            redundantXSpace /= 2.toFloat()
            mMatrix!!.postTranslate(redundantXSpace, redundantYSpace)
            origWidth = viewWidth - 2 * redundantXSpace
            origHeight = viewHeight - 2 * redundantYSpace
            imageMatrix = mMatrix
        }
        fixTrans()
    }

    companion object {
        // We can be in one of these 3 states
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
        const val CLICK = 3
    }
}