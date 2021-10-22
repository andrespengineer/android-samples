package com.social.presentation.feed.adapter.viewholder

import android.os.CountDownTimer
import coil.load
import coil.request.CachePolicy
import com.social.R
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedAdvertisingContainerBinding
import com.social.presentation.feed.adapter.FeedAdapterGrid

class FeedAdapterGridHeaderViewHolder(val binding: FragmentFeedAdvertisingContainerBinding, adapter: FeedAdapterGrid) : BaseViewHolder<FeedModel, FeedAdapterGrid>(binding.root, adapter) {

    companion object {
        private const val ADVERTISEMENT_AWAIT_TIME = 2000L
    }

    private val timer = object : CountDownTimer(ADVERTISEMENT_AWAIT_TIME, ADVERTISEMENT_AWAIT_TIME) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            val advertisement = adapter.advertisementList.randomOrNull()
            if(advertisement != null) {
                binding.ivFeedAdvertising.load(advertisement.image) {
                    memoryCacheKey(advertisement.image + advertisement.key)
                }
                this.start()
            }
        }
    }

    override fun onBind(model: FeedModel) {
        timer.cancel()
        timer.start()
    }

}