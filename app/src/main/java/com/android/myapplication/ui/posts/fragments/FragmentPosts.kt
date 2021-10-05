package com.android.myapplication.ui.posts.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.ui.posts.adapter.PostsAdapter
import com.android.myapplication.base.BaseFragment
import com.android.myapplication.databinding.FragmentPostsBinding
import com.android.myapplication.ui.posts.contract.FragmentPostsContract
import com.android.myapplication.data.models.Post
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentPosts : BaseFragment<FragmentPostsBinding, FragmentPostsContract.View, FragmentPostsContract.Presenter>(), FragmentPostsContract.View {

    @Inject
    override lateinit var presenter: FragmentPostsContract.Presenter

    lateinit var postsAdapter: PostsAdapter
        @Inject set

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentPostsBinding get() = FragmentPostsBinding::inflate

    override fun setup(view: View) {

        val linearLayoutManagerVertical = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.rvPosts.setItemViewCacheSize(15)
        binding.rvPosts.layoutManager = linearLayoutManagerVertical
        binding.rvPosts.adapter = postsAdapter

        presenter.getPosts()
    }

    override fun updateData(postList: List<Post>) {
        postsAdapter.updateAdapterData(postList)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error fetching data", Toast.LENGTH_LONG).show()
    }
}