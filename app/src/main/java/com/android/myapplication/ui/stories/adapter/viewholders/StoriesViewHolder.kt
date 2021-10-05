package com.android.myapplication.ui.stories.adapter.viewholders

import com.android.myapplication.base.BaseViewHolder
import com.android.myapplication.data.models.Story
import com.android.myapplication.databinding.StoriesItemBinding
import com.android.myapplication.ui.stories.adapter.StoriesAdapter
import com.android.myapplication.util.MyGlideRequestOptions
import com.android.myapplication.util.MyKeySignature
import com.bumptech.glide.Glide

class StoriesViewHolder(private val binding: StoriesItemBinding, adapter: StoriesAdapter) : BaseViewHolder<Story, StoriesAdapter>(binding.root, adapter) {

    override fun onBind(model: Story) {
        super.onBind(model)
        binding.tvStory.text = model.username
        Glide.with(binding.root.context)
                .applyDefaultRequestOptions(
                    MyGlideRequestOptions.profileRequestOption
                        .signature(MyKeySignature(model.userId)))
                .load(model.profilePicture)
                .into(binding.ivStory)
                .clearOnDetach()
    }
}