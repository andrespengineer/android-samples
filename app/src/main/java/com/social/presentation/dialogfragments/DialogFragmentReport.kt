package com.social.presentation.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.Toast
import com.social.R
import com.social.base.BaseDialogFragment
import com.social.data.models.FeedModel
import com.social.databinding.CustomReportDialogBinding

class DialogFragmentReport : BaseDialogFragment<CustomReportDialogBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CustomReportDialogBinding
        get() = CustomReportDialogBinding::inflate

    private lateinit var item: FeedModel

    fun setItem(item: FeedModel){
        this.item = item
    }

    override fun setup() {

        with(binding) {
            rbFirst.setOnClickListener(this@DialogFragmentReport)
            rbSecond.setOnClickListener(this@DialogFragmentReport)
            rbThird.setOnClickListener(this@DialogFragmentReport)
            btnSend.setOnClickListener(this@DialogFragmentReport)
            btnCancel.setOnClickListener(this@DialogFragmentReport)
        }

    }

    private fun setChecked(view: RadioButton) {

        with(binding) {
            rbFirst.isChecked = false
            rbSecond.isChecked = false
            rbThird.isChecked = false
        }

        view.isChecked = true

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.drawable.shape_corner_radius_dialog_fragment)
        return dialog
    }

    override fun onClick(v: View) {
        with(binding) {
            when(v.id) {
                rbFirst.id,rbSecond.id, rbThird.id -> setChecked(v as RadioButton)
                btnSend.id -> {
                    Toast.makeText(requireContext(), requireContext().resources.getString(R.string.action_report_success), Toast.LENGTH_LONG).show()
                    dismiss()
                }
                btnCancel.id -> {
                    dismiss()
                }

            }
        }

    }

    companion object {
        fun newInstance(): DialogFragmentReport {
            return DialogFragmentReport()
        }
    }
}