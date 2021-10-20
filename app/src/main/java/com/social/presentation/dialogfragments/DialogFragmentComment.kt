package com.social.presentation.dialogfragments

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import com.social.R
import com.social.base.BaseDialogFragment
import com.social.databinding.DialogFragmentCommentBinding
import java.io.Serializable

class DialogFragmentComment : BaseDialogFragment<DialogFragmentCommentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFragmentCommentBinding
        get() = DialogFragmentCommentBinding::inflate

    override fun setup() {

        caption = arguments?.getString(CAPTION, "")

        binding.etPostContentDescription.setText(caption)
        binding.etPostContentDescription.setSelection(binding.etPostContentDescription.text.length)
        binding.etPostContentDescription.setHorizontallyScrolling(false)
        binding.etPostContentDescription.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.etPostContentDescription.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_VARIATION_FILTER)
        binding.btnPostContentClearText.setOnClickListener { binding.etPostContentDescription.setText("") }
    }

    private var dialogCallback: DialogCallback? = null
    private var caption: String? = null

    interface DialogCallback : Serializable {
        fun getResults(results: String)
    }

    fun setCallBack(dialogCallback: DialogCallback?): DialogFragmentComment {
        this.dialogCallback = dialogCallback
        return this
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val colorDrawable: Drawable = ColorDrawable(Color.BLACK)
        colorDrawable.alpha = (130)

        dialog.window?.setBackgroundDrawable(colorDrawable)
        dialog.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogCallback?.getResults(binding.etPostContentDescription.text.toString())
    }

    companion object {
        const val CAPTION = "CAPTION"

        fun newInstance(): DialogFragmentComment {
            return DialogFragmentComment()
        }
    }
}