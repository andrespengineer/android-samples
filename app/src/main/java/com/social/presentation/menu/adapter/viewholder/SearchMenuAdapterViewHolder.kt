package com.social.presentation.menu.adapter.viewholder

import android.view.View
import com.social.presentation.base.BaseViewHolder
import com.social.data.models.MenuModel
import com.social.databinding.FragmentSearchMenuItemsBinding
import com.social.presentation.dialogfragments.DialogFragmentMenu
import com.social.presentation.menu.adapter.SearchMenuAdapter

class SearchMenuAdapterViewHolder(private val binding: FragmentSearchMenuItemsBinding, adapter: SearchMenuAdapter): BaseViewHolder<MenuModel, SearchMenuAdapter>(binding.root, adapter), View.OnClickListener{

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onBind(model: MenuModel) {
        super.onBind(model)
        binding.tvSearchMenuIngredients.visibility = if(model.ingredients.isEmpty()) View.GONE else View.VISIBLE
        binding.tvSearchMenuIngredients.text = model.ingredients
        binding.tvSearchMenuName.text = model.name
        binding.tvSearchMenuPrice.text = model.price.toString()
        binding.tvSearchMenuRating.text = model.rating.toString()
    }

    override fun onClick(v: View) {
        if (adapter.dialogFragmentMenu.dialog == null || adapter.dialogFragmentMenu.dialog!!.isShowing.not()) {
            adapter.dialogFragmentMenu.setItem(model)
            adapter.dialogFragmentMenu.show(adapter.fragmentManager, DialogFragmentMenu::class.simpleName)
        }
    }

}