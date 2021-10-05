package com.android.myapplication.ui.home.contracts

import com.android.myapplication.base.BaseContract

class ActivityHomeContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter<View?>
}