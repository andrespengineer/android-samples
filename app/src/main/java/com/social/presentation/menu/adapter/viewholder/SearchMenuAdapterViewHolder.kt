package com.social.presentation.menu.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
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
        with(binding){
            tvSearchMenuIngredients.isVisible = model.ingredients.isEmpty().not()
            tvSearchMenuIngredients.text = model.ingredients
            tvSearchMenuName.text = model.name
            tvSearchMenuPrice.text = model.price.toString()
            tvSearchMenuRating.text = model.rating.toString()
        }

    }

    override fun onClick(v: View) {
        if (adapter.dialogFragmentMenu.dialog == null || adapter.dialogFragmentMenu.dialog!!.isShowing.not()) {
            adapter.dialogFragmentMenu.setItem(model)
            adapter.dialogFragmentMenu.show(adapter.fragmentManager, DialogFragmentMenu::class.simpleName)
        }
    }

}