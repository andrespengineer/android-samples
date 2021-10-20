package com.social.presentation.adapters.viewholders

import android.view.View
import coil.load
import com.social.R
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentPlaylistItemsBinding
import com.social.presentation.adapters.PlaylistAdapter
import com.social.presentation.dialogfragments.DialogFragmentRateSong

class PlaylistAdapterViewHolder(private val binding: FragmentPlaylistItemsBinding, adapter: PlaylistAdapter) : BaseViewHolder<PlaylistModel, PlaylistAdapter>(binding.root, adapter), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onBind(model: PlaylistModel) {
        super.onBind(model)

        binding.tvPlaylistSongArtist.text = model.artist
        binding.tvPlaylistSongName.text = model.name
        binding.ivPlaylistSongImage.load(model.image){
            crossfade(true)
            memoryCacheKey(model.key)
        }
    }

    override fun onClick(v: View) {

        if (adapter.dialogFragmentRateSong.dialog == null || adapter.dialogFragmentRateSong.dialog!!.isShowing.not()) {
            adapter.dialogFragmentRateSong.setItemData(model)
            // Show Popup
            adapter.dialogFragmentRateSong.show(adapter.fragmentManager, DialogFragmentRateSong::class.simpleName)
        }
    }

}