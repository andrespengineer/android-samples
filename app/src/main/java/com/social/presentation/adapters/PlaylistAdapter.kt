package com.social.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.presentation.base.BaseAdapter
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentPlaylistItemsBinding
import com.social.presentation.adapters.viewholders.PlaylistAdapterViewHolder
import com.social.presentation.dialogfragments.DialogFragmentRateSong
import androidx.fragment.app.FragmentManager

class PlaylistAdapter(var dialogFragmentRateSong: DialogFragmentRateSong) : BaseAdapter<PlaylistModel, PlaylistAdapterViewHolder>(PlaylistModel.DIFF_ITEM_CALLBACK) {

    lateinit var fragmentManager: FragmentManager

    override fun onBindViewHolder(holder: PlaylistAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapterViewHolder {
        return PlaylistAdapterViewHolder(FragmentPlaylistItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
    }
}