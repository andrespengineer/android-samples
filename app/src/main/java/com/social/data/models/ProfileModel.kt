package com.social.data.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileModel(
    val id: Long = 0L,
    var favoriteDrink: String = "",
    var favoriteSong: String = "",
    val thumbnail: String = "",
    var instagram: String = "",
    val username: String = "",
    val drinkCount: Int = 0,
    var key: String = "") : Parcelable {


    companion object {
        const val PROFILE = "PROFILE"
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProfileModel>() {
            override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}