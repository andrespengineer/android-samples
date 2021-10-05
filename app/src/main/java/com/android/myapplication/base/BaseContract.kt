package com.android.myapplication.base

class BaseContract {
    interface Presenter<in V: View?> {
        fun attach(view: V?)
    }

    interface View
}