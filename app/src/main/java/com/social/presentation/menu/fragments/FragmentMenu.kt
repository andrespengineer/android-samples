package com.social.presentation.menu.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.social.R
import com.social.data.models.MenuModel
import com.social.databinding.FragmentMenuBinding
import com.social.presentation.base.BaseFragment
import com.social.presentation.menu.activities.ActivityMenuSearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMenu : BaseFragment<FragmentMenuBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate

    override fun setup() {
        binding.flMenuCategoryDrinks.setOnClickListener(this)
        binding.flMenuCategoryFood.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.flMenuCategoryDrinks -> {
                val iMenuSearchActivity = Intent(requireContext(), ActivityMenuSearch::class.java)
                iMenuSearchActivity.putExtra(MenuModel.CATEGORY, MenuModel.CATEGORY_DRINKS_INT)
                startActivity(iMenuSearchActivity)
            }
            R.id.flMenuCategoryFood -> {
                val iMenuSearchActivity = Intent(requireContext(), ActivityMenuSearch::class.java)
                iMenuSearchActivity.putExtra(MenuModel.CATEGORY, MenuModel.CATEGORY_FOOD_INT)
                startActivity(iMenuSearchActivity)
            }
        }
    }
}