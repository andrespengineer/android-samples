package com.android.myapplication.ui.stories.contracts

import com.android.myapplication.base.BaseContract
import com.android.myapplication.data.models.Story

class FragmentStoriesContract {
    interface View : BaseContract.View {
        fun updateData(storyList: List<Story>)
    }

    interface Presenter : BaseContract.Presenter<View?> {
       fun getStories()
    }
}