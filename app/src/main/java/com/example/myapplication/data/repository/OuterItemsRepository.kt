package com.example.myapplication.data.repository

import com.example.myapplication.data.datasources.InnerItemsDataSource
import com.example.myapplication.data.datasources.OuterItemsDataSource
import com.example.myapplication.domain.utils.getUpdated
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val LIST_UPDATE_DELAY: Long = 3000

class OuterItemsRepository @Inject constructor(
    private val innerItemsDataSource: InnerItemsDataSource,
    private val outerItemsDataSource: OuterItemsDataSource,
) {
    fun getOuterList() = flow {
        var outerList = outerItemsDataSource.getItemsList { innerItemsDataSource.getItemsList(it) }

        emit(outerList)
        while (true) {
            delay(LIST_UPDATE_DELAY)
            outerList = outerList.getUpdated()
            emit(outerList)
        }
    }.flowOn(Dispatchers.IO)
}