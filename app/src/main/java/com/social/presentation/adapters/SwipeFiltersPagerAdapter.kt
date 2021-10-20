package com.social.presentation.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import com.social.databinding.SwipeFiltersLayoutBinding
import com.social.presentation.base.BaseViewHolder
import androidx.recyclerview.widget.RecyclerView
import coil.load

class SwipeFiltersPagerAdapter: RecyclerView.Adapter<SwipeFiltersPagerAdapter.SwipePagerAdapterViewHolder>() {

    inner class SwipePagerAdapterViewHolder(val binding: SwipeFiltersLayoutBinding, adapter: SwipeFiltersPagerAdapter) : BaseViewHolder<Bitmap, SwipeFiltersPagerAdapter>(binding.root, adapter) {
        override fun onBind(model: Bitmap) {
            super.onBind(model)
            binding.ivSwipeFilterImage.load(model)
        }
    }

    private val items: MutableList<Bitmap> = mutableListOf()

    fun setFilters(filters: MutableList<Bitmap>) {
        this.items.clear()
        this.items.addAll(filters)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipePagerAdapterViewHolder {
        return SwipePagerAdapterViewHolder(SwipeFiltersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), this)
    }

    override fun onBindViewHolder(holder: SwipePagerAdapterViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}