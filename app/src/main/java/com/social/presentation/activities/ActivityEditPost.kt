package com.social.presentation.activities

import android.text.format.DateFormat
import android.view.LayoutInflater
import coil.load
import com.social.R
import com.social.data.models.FeedModel
import com.social.databinding.ActivityEditPostBinding
import com.social.presentation.base.BaseActivity
import com.social.utils.DialogUtil

class ActivityEditPost : BaseActivity<ActivityEditPostBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityEditPostBinding
        get() = ActivityEditPostBinding::inflate

    override fun setup() {

        val feedModel = intent.getParcelableExtra<FeedModel>(FeedModel.FEED) ?: return

        binding.ivFeedContent.load(feedModel.image)
        binding.tvFeedDescriptionContent.setText(feedModel.caption)
        binding.tvFeedDescriptionContent.setSelection(feedModel.caption.length)
        binding.tvFeedProfileName.text = feedModel.user.name
        binding.tvFeedPostDate.text = DateFormat.format("dd:mm:yyyy", feedModel.date)
        binding.ivFeedProfilePhoto.load(feedModel.user.thumbnail)

    }

    override fun onBackPressed() {
        DialogUtil.createCustomDialog(this, R.layout.custom_alert_dialog, resources.getString(R.string.back_message), onClickYes = {
            finish()
        })
    }
}