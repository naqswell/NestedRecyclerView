package com.example.myapplication.domain.utils

import com.example.myapplication.domain.model.InnerItemDto
import com.example.myapplication.domain.model.OuterItemDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

suspend fun List<OuterItemDto>.getUpdated(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
): List<OuterItemDto> = withContext(dispatcher) {
    val list: MutableList<Deferred<OuterItemDto>> = mutableListOf()

    this@getUpdated.forEach { item ->
        list.add(
            async {
                val rngIdx = item.innerItems.indices.random()
                item.copy(innerItems = item.updateRandomElementForEach(rngIdx))
            }
        )
    }
    return@withContext list.awaitAll()
}


fun OuterItemDto.updateRandomElementForEach(index: Int): List<InnerItemDto> =
    this.innerItems.toMutableList().also { list ->
        list[index] = list[index].copy(
            prevNumber = list[index].number,
            number = (0..100).random()
        )
    }
