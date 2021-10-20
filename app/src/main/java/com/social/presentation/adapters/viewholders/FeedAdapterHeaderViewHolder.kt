package com.social.presentation.adapters.viewholders

import android.os.CountDownTimer
import coil.load
import com.social.R
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedAdvertisingContainerBinding
import com.social.presentation.adapters.FeedAdapter

class FeedAdapterHeaderViewHolder(val binding: FragmentFeedAdvertisingContainerBinding, adapter: FeedAdapter) : BaseViewHolder<FeedModel, FeedAdapter>(binding.root, adapter) {

    companion object {
        private const val ADVERTISEMENT_AWAIT_TIME = 2000L
    }

    private val timer = object : CountDownTimer(ADVERTISEMENT_AWAIT_TIME, ADVERTISEMENT_AWAIT_TIME) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            val advertisement = adapter.advertisementList.randomOrNull()
            if(advertisement != null) {
                binding.ivFeedAdvertising.load(advertisement.image) {
                    crossfade(false)
                    placeholder(R.drawable.circular_progressbar)
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