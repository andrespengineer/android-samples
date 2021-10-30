package com.social.presentation.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.AdvertiseModel
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedAdvertisingContainerBinding
import com.social.databinding.FragmentFeedRecyclerViewItemGridModeBinding
import com.social.presentation.base.BasePagingAdapter
import com.social.presentation.feed.adapter.viewholder.FeedAdvertisementViewHolder
import com.social.presentation.feed.adapter.viewholder.FeedAdapterGridViewHolder

class FeedAdapterGrid : BasePagingAdapter<FeedModel, BaseViewHolder<FeedModel, *>>(FeedModel.DIFF_ITEM_CALLBACK){

    var itemClickListener: ItemClickListener? = null

    val advertisementList: MutableList<AdvertiseModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FeedModel, *> {
        return if (viewType == ITEM_TYPE_HEADER) {
            FeedAdvertisementViewHolder(FragmentFeedAdvertisingContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        } else {
            FeedAdapterGridViewHolder(FragmentFeedRecyclerViewItemGridModeBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<FeedModel, *>, position: Int) {
        holder.onBind(getItem(position) ?: return)

    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) ITEM_TYPE_HEADER else position
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        const val ITEM_TYPE_HEADER = 0
        const val NUMBER_OF_COLUMNS = 3
    }

}