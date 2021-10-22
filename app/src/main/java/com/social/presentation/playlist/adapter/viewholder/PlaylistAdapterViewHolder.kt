package com.social.presentation.playlist.adapter.viewholder

import android.view.View
import coil.load
import coil.request.CachePolicy
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentPlaylistItemsBinding
import com.social.presentation.playlist.adapter.PlaylistAdapter
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
            memoryCacheKey(model.image + model.key)
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