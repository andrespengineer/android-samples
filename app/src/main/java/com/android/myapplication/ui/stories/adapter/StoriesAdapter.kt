package com.android.myapplication.ui.stories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.android.myapplication.ui.stories.adapter.viewholders.StoriesViewHolder
import com.android.myapplication.ui.stories.adapter.viewholders.StoriesHeaderViewHolder
import com.android.myapplication.base.BaseAdapter
import com.android.myapplication.base.BaseViewHolder
import com.android.myapplication.databinding.StoriesHeaderItemBinding
import com.android.myapplication.databinding.StoriesItemBinding
import com.android.myapplication.data.models.Story
import com.android.myapplication.util.MyDiffUtil
import java.util.*

class StoriesAdapter : BaseAdapter<Story, BaseViewHolder<Story, StoriesAdapter>>() {

    private val mItems: MutableList<Story> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Story, StoriesAdapter> {
        return when (viewType) {
            STORIES_HEADER -> StoriesHeaderViewHolder(StoriesHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
            else -> StoriesViewHolder(StoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Story, StoriesAdapter>, position: Int) {
        val item = mItems[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItems[position].isHeader) STORIES_HEADER else STORIES_BODY
    }

    override fun updateAdapterData(items: List<Story>) {
        val diffCallBack = MyDiffUtil(mItems, items)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        mItems.clear()
        mItems.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        const val STORIES_HEADER = 0
        const val STORIES_BODY = 1
    }
}