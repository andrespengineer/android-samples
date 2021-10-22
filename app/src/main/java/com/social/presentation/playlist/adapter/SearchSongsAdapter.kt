package com.social.presentation.playlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentSearchSongsItemsBinding
import com.social.presentation.base.BasePagingAdapter
import com.social.presentation.dialogfragments.DialogFragmentRateSong
import androidx.fragment.app.FragmentManager
import com.social.presentation.playlist.adapter.viewholder.SearchSongsAdapterViewHolder

class SearchSongsAdapter(var dialogFragmentRateSong: DialogFragmentRateSong) : BasePagingAdapter<PlaylistModel, SearchSongsAdapterViewHolder>(PlaylistModel.DIFF_ITEM_CALLBACK) {

    lateinit var fragmentManager: FragmentManager

    override fun onBindViewHolder(holder: SearchSongsAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongsAdapterViewHolder {
        return SearchSongsAdapterViewHolder(FragmentSearchSongsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
    }
}