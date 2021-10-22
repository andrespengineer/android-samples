package com.social.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.data.models.MenuModel
import com.social.databinding.FragmentSearchMenuItemsBinding
import com.social.presentation.base.BasePagingAdapter
import com.social.presentation.dialogfragments.DialogFragmentMenu
import androidx.fragment.app.FragmentManager
import com.social.presentation.menu.adapter.viewholder.SearchMenuAdapterViewHolder

class SearchMenuAdapter(var dialogFragmentMenu: DialogFragmentMenu) : BasePagingAdapter<MenuModel, SearchMenuAdapterViewHolder>(MenuModel.DIFF_ITEM_CALLBACK) {

    lateinit var fragmentManager: FragmentManager

    override fun onBindViewHolder(holder: SearchMenuAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMenuAdapterViewHolder {
        return SearchMenuAdapterViewHolder(FragmentSearchMenuItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
    }

}