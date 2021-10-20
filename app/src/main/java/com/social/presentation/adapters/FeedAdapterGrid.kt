package com.social.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.AdvertiseModel
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedAdvertisingContainerBinding
import com.social.databinding.FragmentFeedRecyclerViewItemGridModeBinding
import com.social.presentation.adapters.viewholders.FeedAdapterGridHeaderViewHolder
import com.social.presentation.adapters.viewholders.FeedAdapterGridViewHolder
import com.social.presentation.base.BasePagingAdapter

class FeedAdapterGrid : BasePagingAdapter<FeedModel, BaseViewHolder<FeedModel, *>>(FeedModel.DIFF_ITEM_CALLBACK){

    var itemClickListener: ItemClickListener? = null

    val advertisementList: MutableList<AdvertiseModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FeedModel, *> {
        return if (viewType == ITEM_TYPE_HEADER) {
            FeedAdapterGridHeaderViewHolder(FragmentFeedAdvertisingContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        } else {
            FeedAdapterGridViewHolder(FragmentFeedRecyclerViewItemGridModeBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<FeedModel, *>, position: Int) {
        holder.onBind(getItem(position) ?: return)

    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        const val ITEM_TYPE_HEADER = 0
        const val NUMBER_OF_COLUMNS = 3
    }

}