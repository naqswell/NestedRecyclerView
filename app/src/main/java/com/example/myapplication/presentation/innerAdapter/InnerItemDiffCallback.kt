package com.example.myapplication.presentation.innerAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.InnerItemDto

class InnerItemDiffCallback : DiffUtil.ItemCallback<InnerItemDto>() {
    override fun areItemsTheSame(
        oldItem: InnerItemDto,
        newItem: InnerItemDto
    ): Boolean = oldItem.uuid == newItem.uuid

    override fun areContentsTheSame(
        oldItem: InnerItemDto,
        newItem: InnerItemDto
    ) =
        oldItem == newItem
}