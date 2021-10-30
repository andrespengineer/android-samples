package com.social.presentation.dialogfragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.social.R
import com.social.base.BaseDialogFragment
import com.social.presentation.camera.ActivityCamera
import com.social.presentation.post.activities.ActivityCreatePost
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.social.databinding.DialogFragmentChooserBinding

class DialogFragmentChooser : BaseDialogFragment<DialogFragmentChooserBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFragmentChooserBinding
        get() = DialogFragmentChooserBinding::inflate

    private val cameraLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result != null && result.data != null && result.resultCode == Activity.RESULT_OK) {

            val bundle = result.data?.extras
            val path = bundle?.getString(PATH) ?: return@registerForActivityResult
            val uri = Uri.parse(path)
            startActivityPostContent(uri.toString())
        }
    }

    private val galleryLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result != null && result.data != null && result.resultCode == Activity.RESULT_OK) {
            val clipData = result.data

            if (clipData == null) {
                val returnUri = result.data?.data ?: return@registerForActivityResult
                startActivityPostContent(returnUri.toString())
            }
        }
    }

    override fun setup() {
        binding.ivPickUpCameraIntent.setOnClickListener(this)
        binding.ivPickUpGalleryIntent.setOnClickListener(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.drawable.shape_corner_radius_dialog_fragment)
        return dialog
    }

    override fun onClick(v: View) {
        if (v.id == R.id.ivPickUpCameraIntent) {
            val iCamera = Intent(requireContext(), ActivityCamera::class.java)
            cameraLauncher.launch(iCamera)
        } else {
            val iGallery = Intent()
            iGallery.action = Intent.ACTION_VIEW
            iGallery.type = "image/*"
            iGallery.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            galleryLauncher.launch(iGallery)
        }
    }

    private fun startActivityPostContent(path: String){
        val iPostContent = Intent(requireContext(), ActivityCreatePost::class.java)
        iPostContent.putExtra(PATH, path)
        startActivity(iPostContent)
        dismiss()
    }


    companion object {
        const val PATH = "PATH"
        fun newInstance(): DialogFragmentChooser {
            return DialogFragmentChooser()
        }
    }
}