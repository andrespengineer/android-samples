package com.social.presentation.adapters.viewholders

import android.view.View
import coil.load
import com.social.R
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedRecyclerViewItemGridModeBinding
import com.social.presentation.adapters.FeedAdapterGrid

class FeedAdapterGridViewHolder (val binding: FragmentFeedRecyclerViewItemGridModeBinding, adapter: FeedAdapterGrid) : BaseViewHolder<FeedModel, FeedAdapterGrid>(binding.root, adapter), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onBind(model: FeedModel) {
        super.onBind(model)

        binding.ivFeedGridViewPreview.load(model.image){
            this.size(200, 200)
            this.memoryCacheKey(model.key)
        }
    }

    override fun onClick(view: View) {
        adapter.itemClickListener?.onItemClick(bindingAdapterPosition)
    }

}