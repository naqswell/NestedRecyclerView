package com.example.myapplication.presentation.innerAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.domain.model.InnerItemDto

class InnerItemsAdapter :
    ListAdapter<InnerItemDto, InnerItemViewHolder>(
        AsyncDifferConfig.Builder(InnerItemDiffCallback())
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerItemViewHolder =
        InnerItemViewHolder(parent)

    override fun onBindViewHolder(holder: InnerItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}