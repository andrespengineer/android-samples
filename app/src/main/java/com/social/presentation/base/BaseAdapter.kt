package com.social.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Model : Any,  ViewHolder: RecyclerView.ViewHolder>(callback: DiffUtil.ItemCallback<Model>) : ListAdapter<Model, ViewHolder>(callback)