package com.social.presentation.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.presentation.base.BasePagingAdapter
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.AdvertiseModel
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedAdvertisingContainerBinding
import com.social.databinding.FragmentFeedRecyclerViewItemBinding
import com.social.presentation.dialogfragments.DialogFragmentReport
import androidx.fragment.app.FragmentManager
import com.social.presentation.feed.adapter.viewholder.FeedAdapterHeaderViewHolder
import com.social.presentation.feed.adapter.viewholder.FeedAdapterViewHolder

class FeedAdapter(val customReportDialog: DialogFragmentReport) : BasePagingAdapter<FeedModel, BaseViewHolder<FeedModel, *>>(FeedModel.DIFF_ITEM_CALLBACK) {

    lateinit var fragmentManager : FragmentManager

    val advertisementList: MutableList<AdvertiseModel> = mutableListOf()

    override fun onBindViewHolder(holder: BaseViewHolder<FeedModel, *>, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FeedModel, *> {
        return if (viewType == ITEM_TYPE_HEADER) {
            FeedAdapterHeaderViewHolder(FragmentFeedAdvertisingContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        } else {
            FeedAdapterViewHolder(FragmentFeedRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) ITEM_TYPE_HEADER else position
    }

    companion object {
        const val ITEM_TYPE_HEADER = 0
    }

}