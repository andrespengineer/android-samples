package com.social.presentation.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingAdapter<Model : Any,  ViewHolder: RecyclerView.ViewHolder>(callback: DiffUtil.ItemCallback<Model>) : PagingDataAdapter<Model, ViewHolder>(callback)