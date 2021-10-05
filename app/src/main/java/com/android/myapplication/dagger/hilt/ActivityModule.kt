package com.android.myapplication.dagger.hilt

import com.android.myapplication.ui.home.contracts.ActivityHomeContract
import com.android.myapplication.ui.home.presenter.ActivityHomePresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideActivityHomePresenter() : ActivityHomeContract.Presenter = ActivityHomePresenter()
}