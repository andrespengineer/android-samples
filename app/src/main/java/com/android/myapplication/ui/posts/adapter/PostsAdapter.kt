package com.android.myapplication.ui.posts.adapter

import com.android.myapplication.base.BaseAdapter
import com.android.myapplication.databinding.PostsItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.android.myapplication.R
import com.android.myapplication.ui.posts.adapter.viewholders.PostsAdapterViewHolder
import com.android.myapplication.data.models.Post
import com.android.myapplication.util.MyDiffUtil
import com.bumptech.glide.Glide
import java.util.*

open class PostsAdapter : BaseAdapter<Post, PostsAdapterViewHolder>() {

    private val mItems: MutableList<Post> = ArrayList()
    var lottieComposition = LottieComposition()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapterViewHolder {
        return PostsAdapterViewHolder(PostsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
    }

    override fun onBindViewHolder(holder: PostsAdapterViewHolder, position: Int) {
        val item = mItems[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun updateAdapterData(items: List<Post>) {
        val diffCallBack = MyDiffUtil(mItems, items)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        mItems.clear()
        mItems.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val factory = LottieCompositionFactory.fromRawRes(recyclerView.context, R.raw.liked)
        factory.addListener { composition: LottieComposition -> lottieComposition = composition }
    }

    override fun onViewRecycled(holder: PostsAdapterViewHolder) {
        super.onViewRecycled(holder)
        holder.cancelLikeAnimation()
        Glide.with(holder.binding.root.context).clear(holder.binding.ivPostItem)
        Glide.with(holder.binding.root.context).clear(holder.binding.ivPostItemProfile)
        if (holder.binding.tvPostItemDescription.editableText != null)
            holder.binding.tvPostItemDescription.editableText.clearSpans()
    }
}