package com.example.myapplication.presentation.outerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.myapplication.R
import com.example.myapplication.databinding.ParentRvItemBinding
import com.example.myapplication.presentation.innerAdapter.InnerItemsAdapter
import com.example.myapplication.domain.model.OuterItemDto
import com.example.myapplication.presentation.utils.MarginItemDecoration
import com.example.myapplication.presentation.utils.StartLinearSnapHelper


class OuterItemViewHolder(
    private val parent: ViewGroup,
    private val binding: ParentRvItemBinding = ParentRvItemBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root), NestedRecyclerViewViewHolder {

    private val snapHelper: SnapHelper by lazy { StartLinearSnapHelper() }
    fun bind(outerItemDto: OuterItemDto) = with(binding) {
        innerRecyclerView.layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)

        if (innerRecyclerView.itemDecorationCount == 0) {
            innerRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    parent.resources.getDimensionPixelSize(
                        R.dimen.small
                    ),
                    isVertical = false
                )
            )
        }

        innerRecyclerView.adapter = InnerItemsAdapter().also { adapter ->
            if (innerRecyclerView.onFlingListener == null)
                snapHelper.attachToRecyclerView(innerRecyclerView)

            adapter.submitList(outerItemDto.innerItems)
        }
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager? =
        binding.innerRecyclerView.layoutManager

}