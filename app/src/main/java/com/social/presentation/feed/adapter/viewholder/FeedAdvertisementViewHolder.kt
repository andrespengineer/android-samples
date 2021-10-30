package com.social.presentation.feed.adapter.viewholder

import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.request.CachePolicy
import com.social.R
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.FeedModel
import com.social.databinding.FragmentFeedAdvertisingContainerBinding
import com.social.presentation.base.BasePagingAdapter
import com.social.presentation.feed.adapter.FeedAdapterGrid
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class FeedAdvertisementViewHolder(val binding: FragmentFeedAdvertisingContainerBinding, adapter: BasePagingAdapter<FeedModel, *>) : BaseViewHolder<FeedModel, BasePagingAdapter<FeedModel, *>>(binding.root, adapter) {

    companion object {
        private const val ADVERTISEMENT_AWAIT_TIME = 2000L
    }

    private val timer = (0..Int.MAX_VALUE)
        .asSequence()
        .asFlow()
        .onEach { delay(ADVERTISEMENT_AWAIT_TIME) }

    override fun onBind(model: FeedModel) {
        super.onBind(model)

    }

}