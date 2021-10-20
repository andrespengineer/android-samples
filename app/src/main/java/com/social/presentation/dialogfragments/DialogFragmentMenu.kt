package com.social.presentation.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RatingBar
import android.widget.Toast
import com.social.R
import com.social.base.BaseDialogFragment
import com.social.data.models.MenuModel
import com.social.databinding.DialogFragmentMenuBinding

class DialogFragmentMenu : BaseDialogFragment<DialogFragmentMenuBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFragmentMenuBinding
        get() = DialogFragmentMenuBinding::inflate

    private lateinit var item: MenuModel

    fun setItem(item: MenuModel) {
        this.item = item
    }

    override fun setup() {

        binding.PopupMenuRatingBar.rating = item.rating
        binding.PopupMenuRatingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(requireContext(), requireContext().resources.getString(R.string.popup_menu_rated).plus(" ").plus(rating.toString()), Toast.LENGTH_SHORT).show()
        }
        binding.tvPopupMenuItemName.text = item.name
        binding.tvPopupMenuItemIngredients.text = item.ingredients
        binding.tvPopupMenuItemIngredients.visibility = if(item.ingredients.isEmpty()) View.GONE else View.VISIBLE
        binding.btnPopupMenuAddToCart.setOnClickListener(this)
        binding.btnPopupMenuBack.setOnClickListener(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.shape_corner_radius_popup_song)
        return dialog
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btnPopupMenuAddToCart ->
            {
                Toast.makeText(requireActivity(), requireContext().resources.getString(R.string.popup_menu_added_to_cart), Toast.LENGTH_SHORT).show()
            }
        }

        dismiss()
    }

    companion object {
        fun newInstance(): DialogFragmentMenu {
            return DialogFragmentMenu()
        }
    }

}