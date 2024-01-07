package com.example.myapplication.data.datasources

import com.example.myapplication.domain.model.InnerItemDto
import javax.inject.Inject

class InnerItemsDataSource @Inject constructor() {
    fun getItemsList(range: IntRange): List<InnerItemDto> =
        mutableListOf<InnerItemDto>().apply {
            range.map {
                add(InnerItemDto(it))
            }
        }
}