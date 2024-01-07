package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.MainViewModel
import com.example.myapplication.presentation.outerAdapter.OuterItemsAdapter
import com.example.myapplication.presentation.utils.MarginItemDecoration
import com.example.myapplication.presentation.utils.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val outerItemsAdapter by lazy {
        OuterItemsAdapter()
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        observeOuterListData()
        initResetButton()
    }

    private fun initResetButton() = with(binding) {
        resetButton.setOnClickListener {
            outerItemsAdapter.clearState()
            viewModel.onReset()
        }
    }

    private fun observeOuterListData() {
        lifecycleScope.launch {
            viewModel.state.collect {
                outerItemsAdapter.submitList(it.displayingItems)
                updateProgressCircular(it.isNewPageLoading)
            }
        }
    }

    private fun updateProgressCircular(isNewPageLoading: Boolean) = with(binding) {
        if (isNewPageLoading) {
            progressIndicator.visibility = View.VISIBLE
        } else {
            progressIndicator.visibility = View.GONE
            outerRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() = with(binding) {
        outerRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity, VERTICAL, false)
            setPaginationListener(linearLayoutManager)
            layoutManager = linearLayoutManager
            adapter = outerItemsAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.middle)))
        }
    }

    private fun RecyclerView.setPaginationListener(layoutManager: LinearLayoutManager) {
        this.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                viewModel.onLoadMoreItems()
            }

            override fun isLastPage(): Boolean = viewModel.isAllItemsLoaded()

            override fun isLoading(): Boolean = viewModel.inNewPageLoading()

        })
    }

}