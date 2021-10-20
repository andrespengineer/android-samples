package com.social.data.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatMessageModel(var user: UserModel,
                            var message: String,
                            var date: Long) : Parcelable {
    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<ChatMessageModel>() {
            override fun areItemsTheSame(oldItem: ChatMessageModel, newItem: ChatMessageModel): Boolean {
                return oldItem.user.id == newItem.user.id
            }

            override fun areContentsTheSame(oldItem: ChatMessageModel, newItem: ChatMessageModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
