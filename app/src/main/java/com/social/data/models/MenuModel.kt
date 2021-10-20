package com.social.data.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuModel(val id: Int,
                     var ingredients: String,
                     var category: Int,
                     var name: String,
                     var price: Float,
                     var rating: Float,
                     var quantity: Int = 1) : Parcelable {
    companion object {

        const val CATEGORY_DRINKS_INT = 0
        const val CATEGORY_FOOD_INT = 1
        const val CATEGORY = "CATEGORY"

        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<MenuModel>() {
            override fun areItemsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}