package com.social.presentation.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.social.R
import com.social.base.BaseDialogFragment
import com.social.databinding.FragmentSuggestSongDialogBinding

class DialogFragmentSuggestSong : BaseDialogFragment<FragmentSuggestSongDialogBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSuggestSongDialogBinding
        get() = FragmentSuggestSongDialogBinding::inflate

    override fun setup() {
        binding.btnSuggestSongSend.setOnClickListener(this)
        binding.btnSuggestSongCancel.setOnClickListener(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.drawable.shape_corner_radius_popup_suggest_song)
        return dialog
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnSuggestSongSend -> {
                dismiss()
            }
            R.id.btnSuggestSongCancel -> {
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(): DialogFragmentSuggestSong {
            return DialogFragmentSuggestSong()
        }
    }
}