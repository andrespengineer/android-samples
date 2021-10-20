package com.social.data.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedModel (val user: UserModel,
                          val date: Long = 0,
                          val deleted: Boolean,
                          val image: String,
                          val caption: String,
                          val key: String) : Parcelable {


    companion object {
        const val FEED = "FEED"

        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<FeedModel>() {
            override fun areItemsTheSame(oldItem: FeedModel, newItem: FeedModel): Boolean {
                return oldItem.user.id == newItem.user.id
            }

            override fun areContentsTheSame(oldItem: FeedModel, newItem: FeedModel): Boolean {
                return oldItem == newItem
            }
        }
    } }