package com.example.myapplication.data.datasources

import com.example.myapplication.domain.model.InnerItemDto
import com.example.myapplication.domain.model.OuterItemDto
import javax.inject.Inject

class OuterItemsDataSource @Inject constructor() {
    fun getItemsList(generateInnerList: (range: IntRange) -> List<InnerItemDto>): List<OuterItemDto> =
        mutableListOf<OuterItemDto>().apply {
            (1..100).map {
                val intRange = (it * 10 .. it * 10 + 10)
                add(OuterItemDto(generateInnerList(intRange)))
            }
        }
}