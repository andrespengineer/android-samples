package com.social.presentation.adapters.viewholders

import android.text.format.DateFormat
import coil.load
import com.social.data.models.ChatMessageModel
import com.social.databinding.FragmentChatMessageRightBinding
import com.social.presentation.base.BaseViewHolder
import com.social.presentation.adapters.ChatMessagesAdapter

class ChatMessagesRViewHolder(val binding: FragmentChatMessageRightBinding, adapter: ChatMessagesAdapter) : BaseViewHolder<ChatMessageModel, ChatMessagesAdapter>(binding.root, adapter){

    override fun onBind(model: ChatMessageModel) {
        super.onBind(model)
        binding.tvChatMessageText.text = model.message
        binding.tvChatMessageTime.text = DateFormat.format("h:mm a", model.date)
        binding.ivChatMessageProfilePhoto.load(model.user.thumbnail){
            size(100, 100)
            memoryCacheKey(model.user.key)
        }
    }
}