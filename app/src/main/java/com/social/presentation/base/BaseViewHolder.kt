package com.social.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<Model: Any, Adapter : RecyclerView.Adapter<out RecyclerView.ViewHolder>>(itemView: View, private val mAdapter: Adapter) : RecyclerView.ViewHolder(itemView) {
    protected val adapter: Adapter
        get() = mAdapter

    protected lateinit var model: Model

    open fun onBind(model: Model){
        this.model = model
    }

}