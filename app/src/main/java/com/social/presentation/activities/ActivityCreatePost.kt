package com.social.presentation.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ComponentActivity
import coil.load
import com.social.R
import com.social.databinding.ActivityPostContentBinding
import com.social.presentation.adapters.SwipeFiltersPagerAdapter
import com.social.presentation.base.BaseActivity
import com.social.presentation.dialogfragments.DialogFragmentChooser
import com.social.presentation.dialogfragments.DialogFragmentComment
import com.social.utils.BitmapUtil
import com.social.utils.BitmapUtil.PNG_EXT
import com.social.utils.DialogUtil
import com.social.utils.UriUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class ActivityCreatePost : BaseActivity<ActivityPostContentBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivityPostContentBinding
        get() = ActivityPostContentBinding::inflate

    @Inject
    lateinit var dialogFragmentComment: DialogFragmentComment

    @Inject
    lateinit var swipeFiltersPagerAdapter: SwipeFiltersPagerAdapter

    private var caption: String = ""

    private val dialogCallback: DialogFragmentComment.DialogCallback = object : DialogFragmentComment.DialogCallback {

        override fun getResults(results: String) {
            caption = results
            val newString = StringBuilder()
            val strings: Array<String> = results.split(" ").toTypedArray()
            for (i in strings.indices) {
                if (strings[i].trim { it <= ' ' } != "") {
                    newString.append(strings[i] + " ")
                }
            }
            caption = newString.toString()
        }
    }

    override fun setup() {
        val path = intent?.extras?.getString(DialogFragmentChooser.PATH) ?: ""

        binding.ivPostContentImage.load(path)
        binding.ibActionForward.setOnClickListener(this)
        binding.ibActionComment.setOnClickListener(this)
        binding.ibActionSaveImage.setOnClickListener(this)

        binding.viewPager.adapter = swipeFiltersPagerAdapter
        binding.ibActionBack.setOnClickListener(this)
        swipeFiltersPagerAdapter.setFilters(mutableListOf())
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.ibActionBack -> {
                createConfirmationDialog()
            }
            R.id.ibActionForward -> {
                //val bitmap = BitmapUtil.mergeBitmaps(binding.ivPostContentImage.drawable.toBitmap(), filters.get(binding.viewPager.currentItem))
                finish()
            }
            R.id.ibActionComment -> {

                val arguments = Bundle()
                arguments.putString(DialogFragmentComment.CAPTION, caption)
                dialogFragmentComment.arguments = arguments
                dialogFragmentComment.setCallBack(dialogCallback)
                dialogFragmentComment.show(supportFragmentManager, DialogFragmentComment::class.simpleName)

            }
            R.id.ibActionSaveImage -> {
                //val bitmap: Bitmap = BitmapUtil.mergeBitmaps((binding.ivPostContentImage.drawable.toBitmap(), filters.get(binding.viewPager.currentItem))
                //BitmapUtil.saveBitmapAsNewFile(this, imageToSave, Environment.DIRECTORY_PICTURES, UriUtils.generateRandomFileName() + PNG_EXT)
            }
        }
    }


    private fun createConfirmationDialog(){
        DialogUtil.createCustomDialog(this, R.layout.custom_alert_dialog, resources.getString(R.string.back_message), onClickYes = {
            finish()
        })
    }
    override fun onBackPressed() {
        createConfirmationDialog()
    }
}