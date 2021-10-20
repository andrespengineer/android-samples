package com.social.presentation.adapters.interfaces

interface SearchAdapter<Model> {
    val originalItems: MutableList<Model>
    fun setFixedItems(items: List<Model>) {
        originalItems.clear()
        originalItems.addAll(items)
    }
}