package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.presentation.Constants.UI_SHARED_FLOW_TIMEOUT
import com.example.myapplication.data.repository.OuterItemsRepository
import com.example.myapplication.domain.model.OuterItemDto
import com.example.myapplication.presentation.state.OuterItemsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val outerItemsRepository: OuterItemsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OuterItemsListState())
    val state = _state.asStateFlow()

    private val outerList: SharedFlow<List<OuterItemDto>> = flow {
        emitAll(outerItemsRepository.getOuterList())
    }.shareIn(
        scope = viewModelScope,
        started = WhileSubscribed(UI_SHARED_FLOW_TIMEOUT),
    )

    fun onReset() {
        _state.value = OuterItemsListState()
    }

    init {
        viewModelScope.launch {
            observeItemsWithPagination()
        }
    }

    private suspend fun observeItemsWithPagination() {
        outerList.collect { items ->

            _state.update { newState ->
                newState.copy(
                    items = items,
                    displayingItems = newState.getPaginatedItems()
                )
            }

        }
    }

    fun onLoadMoreItems() {
        viewModelScope.launch {
            _state.update { it.copy(isNewPageLoading = true) }

            delay(1000)
            _state.update { newState ->
                newState.copy(
                    page = newState.page + 1,
                    displayingItems = newState.getPaginatedItems(),
                    isNewPageLoading = false
                )
            }
        }
    }

    fun isAllItemsLoaded() = _state.value.items.size == _state.value.displayingItems.size
    fun inNewPageLoading() = _state.value.isNewPageLoading
}