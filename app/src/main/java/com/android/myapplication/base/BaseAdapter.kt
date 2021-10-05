package com.android.myapplication.base

import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.ui.common.CommonAdapterImpl

abstract class BaseAdapter<Model,  ViewHolder: RecyclerView.ViewHolder> : RecyclerView.Adapter<ViewHolder>(), CommonAdapterImpl<List<Model>>