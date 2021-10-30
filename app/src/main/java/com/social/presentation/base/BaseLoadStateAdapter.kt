package com.social.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.social.databinding.CommonCircularProgressBarBinding
import com.social.databinding.CommonRecyclerViewLoadingBinding

class BaseLoadStateAdapter(
    private val adapter: BasePagingAdapter<*, *>
) : LoadStateAdapter<BaseLoadStateAdapter.BaseLoadStateAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        BaseLoadStateAdapterViewHolder(
            CommonRecyclerViewLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            , this) { adapter.retry() }

    override fun onBindViewHolder(holder: BaseLoadStateAdapterViewHolder, loadState: LoadState) =
        holder.onBind(loadState)

    inner class BaseLoadStateAdapterViewHolder(
        private val binding: CommonRecyclerViewLoadingBinding,
        adapter: BaseLoadStateAdapter,
        private val retryCallback: () -> Unit
    ) : BaseViewHolder<LoadState, BaseLoadStateAdapter>(binding.root, adapter) {

        init {
            binding.btnRetry.setOnClickListener { retryCallback() }
        }

        override fun onBind(model: LoadState) {
            super.onBind(model)
            with(binding) {
                pbLoading.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
            }
        }
    }
}