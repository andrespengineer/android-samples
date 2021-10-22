package com.social.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.data.models.ChatMessageModel
import com.social.data.models.ProfileModel
import com.social.databinding.FragmentChatMessageLeftBinding
import com.social.databinding.FragmentChatMessageRightBinding
import com.social.presentation.base.BasePagingAdapter
import com.social.presentation.base.BaseViewHolder
import com.social.presentation.chat.adapter.viewholder.ChatMessagesLViewHolder
import com.social.presentation.chat.adapter.viewholder.ChatMessagesRViewHolder

class ChatMessagesAdapter : BasePagingAdapter<ChatMessageModel, BaseViewHolder<ChatMessageModel, *>>(ChatMessageModel.DIFF_ITEM_CALLBACK) {

    lateinit var user: ProfileModel

    override fun onBindViewHolder(holder: BaseViewHolder<ChatMessageModel, *>, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessageModel, *> {
        return if (viewType == ITEM_TYPE_EMITTER) {
            ChatMessagesRViewHolder(
                FragmentChatMessageRightBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        } else {
            ChatMessagesLViewHolder(FragmentChatMessageLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position)?.user?.id == user.id) ITEM_TYPE_EMITTER  else position
    }

    companion object {
        const val ITEM_TYPE_EMITTER = 0
    }

}