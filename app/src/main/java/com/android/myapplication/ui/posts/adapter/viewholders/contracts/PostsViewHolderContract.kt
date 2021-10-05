package com.android.myapplication.ui.posts.adapter.viewholders.contracts

import android.view.GestureDetector
import android.view.MotionEvent
import com.android.myapplication.base.BaseContract

class PostsViewHolderContract {
    interface View : BaseContract.View, android.view.View.OnTouchListener, android.view.View.OnClickListener, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            return false
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return false
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            return false
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent) {}
        override fun onLongPress(e: MotionEvent) {}

        fun onLikePost()
    }
}