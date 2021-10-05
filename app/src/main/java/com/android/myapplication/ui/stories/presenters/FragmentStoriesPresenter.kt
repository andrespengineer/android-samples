package com.android.myapplication.ui.stories.presenters

import com.android.myapplication.ui.stories.contracts.FragmentStoriesContract
import com.android.myapplication.data.models.Story
import com.android.myapplication.data.network.RetrofitApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentStoriesPresenter(private val apiClient: RetrofitApiClient) : FragmentStoriesContract.Presenter {

    private var view: FragmentStoriesContract.View? = null

    override fun getStories() {
            with(apiClient) {
                story.enqueue(object : Callback<List<Story?>?> {
                    override fun onResponse(call: Call<List<Story?>?>, response: Response<List<Story?>?>) {
                        val storiesList = response.body() ?: return

                        storiesList[0]!!.isHeader = true
                        view!!.updateData(storiesList.requireNoNulls())
                    }

                    override fun onFailure(call: Call<List<Story?>?>, t: Throwable) {}
                })
            }
        }

    override fun attach(view: FragmentStoriesContract.View?) {
        this.view = view
    }
}