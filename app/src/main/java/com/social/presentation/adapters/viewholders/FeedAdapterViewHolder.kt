package com.social.presentation.adapters.viewholders

import android.content.Context
import android.content.Intent
import android.view.View
import com.social.R
import com.social.data.models.FeedModel
import com.social.data.models.UserModel
import com.social.databinding.FragmentFeedRecyclerViewItemBinding
import com.social.presentation.activities.ActivityEditPost
import com.social.presentation.adapters.FeedAdapter
import com.social.utils.DialogUtil
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import coil.load
import com.social.presentation.base.BaseViewHolder
import com.social.presentation.activities.ActivityViewProfile
import com.social.presentation.dialogfragments.DialogFragmentReport
import java.text.DateFormat

class FeedAdapterViewHolder(val binding: FragmentFeedRecyclerViewItemBinding, adapter: FeedAdapter) : BaseViewHolder<FeedModel, FeedAdapter>(binding.root, adapter), View.OnClickListener {

    init {
        binding.ivFeedProfilePhoto.setOnClickListener(this)
        binding.tvFeedProfileName.setOnClickListener(this)
        binding.ivFeedContent.setOnClickListener(this)
        binding.ivFeedOptions.setOnClickListener(this)
    }

    override fun onBind(model: FeedModel) {
        super.onBind(model)
        binding.tvFeedDescriptionContent.text = model.caption
        binding.tvFeedProfileName.text = model.user.name
        binding.tvFeedPostDate.text = DateFormat.getDateInstance().format(model.key.toLong())
        binding.ivFeedProfilePhoto.load(model.user.thumbnail) {
            crossfade(true)
            memoryCacheKey(model.user.key)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivProfilePhoto, R.id.tvFeedProfileName -> {
                val iViewProfile = Intent(binding.root.context, ActivityViewProfile::class.java)
                iViewProfile.putExtra(UserModel.USER, model.user)
                binding.root.context.startActivity(iViewProfile)
            }
            R.id.ivFeedContent -> {

            }
            R.id.ivFeedOptions -> {
                val wrapper: Context = ContextThemeWrapper(binding.root.context, binding.root.context.theme)
                val popup = PopupMenu(wrapper, binding.ivFeedOptions)
                popup.inflate(R.menu.timeline_menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.timeline_edit -> {
                            val iEditPost = Intent(binding.root.context, ActivityEditPost::class.java)
                            iEditPost.putExtra(FeedModel.FEED, model)
                            binding.root.context.startActivity(iEditPost)
                        }
                        R.id.timeline_report -> {
                            if (adapter.customReportDialog.dialog == null || adapter.customReportDialog.dialog!!.isShowing.not()) {
                                adapter.customReportDialog.setItem(model)
                                adapter.customReportDialog.show(adapter.fragmentManager, DialogFragmentReport::class.simpleName)
                            }
                        }
                        else -> DialogUtil.createCustomDialog(binding.root.context,
                            R.layout.custom_alert_dialog,
                            binding.root.resources.getString(R.string.timeline_alert_block_user) + model.user.name + "?")
                    }
                    true
                }
            }
        }
    }
}