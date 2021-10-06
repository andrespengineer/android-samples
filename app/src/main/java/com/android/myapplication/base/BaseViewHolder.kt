package com.android.myapplication.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<Model: Any, Adapter : RecyclerView.Adapter<*>>(itemView: View, private val mAdapter: Adapter) : RecyclerView.ViewHolder(itemView) {
    protected val adapter: Adapter
        get() = mAdapter

    protected lateinit var model: Model

    open fun onBind(model: Model){
        this.model = model
    }

}