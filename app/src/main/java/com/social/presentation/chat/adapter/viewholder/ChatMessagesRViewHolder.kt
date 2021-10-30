package com.social.presentation.chat.adapter.viewholder

import android.text.format.DateFormat
import coil.load
import coil.request.CachePolicy
import com.social.R
import com.social.data.models.ChatMessageModel
import com.social.databinding.FragmentChatMessageRightBinding
import com.social.presentation.base.BaseViewHolder
import com.social.presentation.chat.adapter.ChatMessagesAdapter

class ChatMessagesRViewHolder(val binding: FragmentChatMessageRightBinding, adapter: ChatMessagesAdapter) : BaseViewHolder<ChatMessageModel, ChatMessagesAdapter>(binding.root, adapter){

    override fun onBind(model: ChatMessageModel) {
        super.onBind(model)
        with(binding){
            tvChatMessageText.text = model.message
            tvChatMessageTime.text = DateFormat.format("h:mm a", model.date)
            ivChatMessageProfilePhoto.load(model.user.thumbnail)
        }

    }
}