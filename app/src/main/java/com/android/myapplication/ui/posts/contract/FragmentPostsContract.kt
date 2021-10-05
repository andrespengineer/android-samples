package com.android.myapplication.ui.posts.contract

import com.android.myapplication.base.BaseContract
import com.android.myapplication.data.models.Post

class FragmentPostsContract {
    interface View : BaseContract.View {
        fun updateData(postList: List<Post>)
        fun showError()
    }

    interface Presenter : BaseContract.Presenter<View?> {
        fun getPosts()
    }
}