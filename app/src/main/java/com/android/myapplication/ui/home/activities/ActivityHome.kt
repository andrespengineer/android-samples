package com.android.myapplication.ui.home.activities

import android.view.LayoutInflater
import com.android.myapplication.base.BaseActivity
import com.android.myapplication.ui.home.contracts.ActivityHomeContract
import com.android.myapplication.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityHome : BaseActivity<ActivityHomeBinding, ActivityHomeContract.View, ActivityHomeContract.Presenter>(), ActivityHomeContract.View {

    @Inject
    override lateinit var presenter: ActivityHomeContract.Presenter

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setup() {

    }

}