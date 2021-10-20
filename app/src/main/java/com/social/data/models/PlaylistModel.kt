package com.social.data.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistModel(
        val id: Long,
        val image: String,
        val name: String,
        var rating: Float,
        var artist: String,
        var spotify: String,
        var key: String): Parcelable {

    companion object {
        val DIFF_ITEM_CALLBACK = object:DiffUtil.ItemCallback<PlaylistModel>() {

            override fun areItemsTheSame(oldItem: PlaylistModel, newItem: PlaylistModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PlaylistModel, newItem: PlaylistModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

