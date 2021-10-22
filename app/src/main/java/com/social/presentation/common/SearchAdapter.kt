package com.social.presentation.common

interface SearchAdapter<Model> {
    val originalItems: MutableList<Model>
    fun setFixedItems(items: List<Model>) {
        originalItems.clear()
        originalItems.addAll(items)
    }
}