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
        binding.rbFirst.setOnClickListener(this)
        binding.rbSecond.setOnClickListener(this)
        binding.rbThird.setOnClickListener(this)

        binding.btnSend.setOnClickListener {
            Toast.makeText(requireContext(), requireContext().resources.getString(R.string.action_report_success), Toast.LENGTH_LONG).show()
        }
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun setChecked(view: RadioButton) {

        binding.rbFirst.isChecked = false
        binding.rbSecond.isChecked = false
        binding.rbThird.isChecked = false

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
        setChecked(v as RadioButton)
    }

    companion object {
        fun newInstance(): DialogFragmentReport {
            return DialogFragmentReport()
        }
    }
}