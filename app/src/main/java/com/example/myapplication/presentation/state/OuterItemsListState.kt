package com.example.myapplication.presentation.state

import com.example.myapplication.domain.model.OuterItemDto

const val ITEMS_PER_PAGE = 10

data class OuterItemsListState(
    val page: Int = 1,
    val isNewPageLoading: Boolean = false,
    val items: List<OuterItemDto> = emptyList(),
    val displayingItems: List<OuterItemDto> = emptyList()
) {
    fun getPaginatedItems() = items.take(page * ITEMS_PER_PAGE)
}