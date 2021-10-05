package com.android.myapplication.ui.posts.presenters

import com.android.myapplication.ui.posts.contract.FragmentPostsContract
import com.android.myapplication.data.models.Post
import com.android.myapplication.data.network.RetrofitApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentPostsPresenter(private val apiClient: RetrofitApiClient) : FragmentPostsContract.Presenter {
    private var view: FragmentPostsContract.View? = null

    override fun getPosts() {
        with(apiClient) {
            posts.enqueue(object : Callback<List<Post?>?> {
            override fun onResponse(call: Call<List<Post?>?>, response: Response<List<Post?>?>) {
                val postList = response.body() ?: return
                view?.updateData(postList.requireNoNulls())
            }

            override fun onFailure(call: Call<List<Post?>?>, t: Throwable) {
                view?.showError()
            }
        })
        }
    }

    override fun attach(view: FragmentPostsContract.View?) {
        this.view = view
    }
}