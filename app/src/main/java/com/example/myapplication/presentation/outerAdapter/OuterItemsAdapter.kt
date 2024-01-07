package com.example.myapplication.presentation.outerAdapter

import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.myapplication.domain.model.OuterItemDto


class OuterItemsAdapter :
    ListAdapter<OuterItemDto, OuterItemViewHolder>(
        AsyncDifferConfig.Builder(
            OuterItemDiffCallback()
        ).build()
    ) {
    private val layoutManagerStates = hashMapOf<String, Parcelable?>()
    private val visibleScrollableViews = hashMapOf<Int, ViewHolderRef>()

    override fun onViewRecycled(holder: OuterItemViewHolder) {
        val state = holder.getLayoutManager()?.onSaveInstanceState()
        if (holder.adapterPosition != NO_POSITION) {
            val key = getItem(holder.adapterPosition).uuid.toString()
            layoutManagerStates[key] = state
        }
        visibleScrollableViews.remove(holder.hashCode())
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterItemViewHolder =
        OuterItemViewHolder(parent)

    override fun onBindViewHolder(holder: OuterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.getLayoutManager()?.let { layoutManager ->
            val key = getItem(holder.adapterPosition).uuid.toString()
            val state = layoutManagerStates[key]

            if (state != null) {
                layoutManager.onRestoreInstanceState(state)
            } else {
                // Reset the scroll position when no state needs to be restored
                layoutManager.scrollToPosition(0)
            }
            visibleScrollableViews[holder.hashCode()] = ViewHolderRef(
                key,
                (holder as NestedRecyclerViewViewHolder)
            )
        }
    }


    override fun submitList(list: List<OuterItemDto>?) {
        saveState()
        super.submitList(list)
    }

    private fun saveState() {
        visibleScrollableViews.values.forEach { item ->
            layoutManagerStates[item.id] = item.reference.getLayoutManager()?.onSaveInstanceState()
        }
        visibleScrollableViews.clear()
    }

    fun clearState() {
        layoutManagerStates.clear()
        visibleScrollableViews.clear()
    }

    private data class ViewHolderRef(
        val id: String,
        val reference: NestedRecyclerViewViewHolder
    )
}

interface NestedRecyclerViewViewHolder {
    fun getLayoutManager(): RecyclerView.LayoutManager?
}

