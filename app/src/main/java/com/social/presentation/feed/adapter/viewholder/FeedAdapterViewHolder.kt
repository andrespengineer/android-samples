package com.social.presentation.feed.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import com.social.R
import com.social.data.models.FeedModel
import com.social.data.models.UserModel
import com.social.databinding.FragmentFeedRecyclerViewItemBinding
import com.social.presentation.post.activities.ActivityEditPost
import com.social.utils.DialogUtil
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import coil.load
import com.social.presentation.base.BaseViewHolder
import com.social.presentation.profile.activities.ActivityViewProfile
import com.social.presentation.dialogfragments.DialogFragmentReport
import com.social.presentation.feed.adapter.FeedAdapter
import java.text.DateFormat

class FeedAdapterViewHolder(val binding: FragmentFeedRecyclerViewItemBinding, adapter: FeedAdapter) : BaseViewHolder<FeedModel, FeedAdapter>(binding.root, adapter), View.OnClickListener {

    init {
        with(binding) {
            ivFeedProfilePhoto.setOnClickListener(this@FeedAdapterViewHolder)
            tvFeedProfileName.setOnClickListener(this@FeedAdapterViewHolder)
            ivFeedContent.setOnClickListener(this@FeedAdapterViewHolder)
            ivFeedOptions.setOnClickListener(this@FeedAdapterViewHolder)
        }
    }

    override fun onBind(model: FeedModel) {
        super.onBind(model)
        with(binding) {
            tvFeedDescriptionContent.text = model.caption
            tvFeedProfileName.text = model.user.name
            tvFeedPostDate.text = DateFormat.getDateInstance().format(model.key.toLong())
            ivFeedContent.load(model.image)
            ivFeedProfilePhoto.load(model.user.thumbnail)
        }
    }

    override fun onClick(v: View) {
        with(binding) {
            when (v.id) {
                ivFeedProfilePhoto.id, tvFeedProfileName.id -> {
                    val iViewProfile = Intent(root.context, ActivityViewProfile::class.java)
                    iViewProfile.putExtra(UserModel.USER, model.user)
                    root.context.startActivity(iViewProfile)
                }
                ivFeedContent.id -> {

                }
                ivFeedOptions.id -> {
                    val wrapper: Context = ContextThemeWrapper(root.context, root.context.theme)
                    val popup = PopupMenu(wrapper, ivFeedOptions)
                    popup.inflate(R.menu.timeline_menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.timeline_edit -> {
                                val iEditPost = Intent(root.context, ActivityEditPost::class.java)
                                iEditPost.putExtra(FeedModel.FEED, model)
                                root.context.startActivity(iEditPost)
                            }
                            R.id.timeline_report -> {
                                if (adapter.customReportDialog.dialog == null || adapter.customReportDialog.dialog!!.isShowing.not()) {
                                    adapter.customReportDialog.setItem(model)
                                    adapter.customReportDialog.show(adapter.fragmentManager, DialogFragmentReport::class.simpleName)
                                }
                            }
                            else -> DialogUtil.createCustomDialog(root.context,
                                R.layout.custom_alert_dialog,
                                root.resources.getString(R.string.timeline_alert_block_user) + model.user.name + "?")
                        }
                        true
                    }
                    popup.dismiss()
                    popup.show()
                }
            }

        }
    }
}