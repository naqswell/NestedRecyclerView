package com.example.myapplication.presentation.innerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ChildRvItemBinding
import com.example.myapplication.domain.model.InnerItemDto

class InnerItemViewHolder(
    private val parent: ViewGroup,
    private val binding: ChildRvItemBinding = ChildRvItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {

    private val context = parent.context

    fun bind(innerItem: InnerItemDto) = with(binding) {
        childNumber.text = innerItem.number.toString()
        if ((innerItem.prevNumber != null) && (innerItem.prevNumber != innerItem.number)) {
            childRoot.setCardBackgroundColor((ContextCompat.getColor(context, R.color.green)))
        }
    }

}