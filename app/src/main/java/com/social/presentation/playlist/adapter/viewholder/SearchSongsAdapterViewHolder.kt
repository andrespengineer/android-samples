package com.social.presentation.playlist.adapter.viewholder

import android.view.View
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentSearchSongsItemsBinding
import com.social.presentation.playlist.adapter.SearchSongsAdapter
import com.social.presentation.dialogfragments.DialogFragmentRateSong

class SearchSongsAdapterViewHolder(private val binding: FragmentSearchSongsItemsBinding, adapter: SearchSongsAdapter): BaseViewHolder<PlaylistModel, SearchSongsAdapter>(binding.root, adapter), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onBind(model: PlaylistModel) {
        super.onBind(model)
        with(binding){
            tvSearchSongArtist.text = model.artist
            tvSearchSongName.text = model.name
        }

    }

    override fun onClick(v: View) {
        if (adapter.dialogFragmentRateSong.dialog == null || !adapter.dialogFragmentRateSong.dialog!!.isShowing) {
            adapter.dialogFragmentRateSong.setItemData(model)
            adapter.dialogFragmentRateSong.show(adapter.fragmentManager, DialogFragmentRateSong::class.simpleName)
        }
    }
}
