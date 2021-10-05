package com.android.myapplication.ui.home.presenter

import com.android.myapplication.ui.home.contracts.ActivityHomeContract

class ActivityHomePresenter : ActivityHomeContract.Presenter {
    private var view: ActivityHomeContract.View? = null

    override fun attach(view: ActivityHomeContract.View?) {
        this.view = view
    }
}