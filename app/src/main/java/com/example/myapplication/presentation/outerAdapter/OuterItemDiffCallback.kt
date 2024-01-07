package com.example.myapplication.presentation.outerAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.OuterItemDto

class OuterItemDiffCallback : DiffUtil.ItemCallback<OuterItemDto>() {
    override fun areItemsTheSame(oldItem: OuterItemDto, newItem: OuterItemDto): Boolean  = oldItem.uuid == newItem.uuid

    override fun areContentsTheSame(oldItem: OuterItemDto, newItem: OuterItemDto) = oldItem == newItem
}