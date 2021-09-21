package com.niyazi.news.ui.news_list

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.niyazi.news.R
import com.niyazi.news.databinding.ActivityMainBinding
import com.niyazi.news.ui.news_list.adapter.LoadStateAdapter
import com.niyazi.news.ui.news_list.adapter.NewsPagingAdapter
import com.niyazi.news.utils.snackbar
import com.niyazi.news.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val newsPagingAdapter by lazy { NewsPagingAdapter() }
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setOnClickListener()
        initAdapter()
        initSearch(DEFAULT_QUERY)
        search(DEFAULT_QUERY)
    }

    private fun setOnClickListener() {
        binding.buttonRetry.setOnClickListener {
            newsPagingAdapter.retry()
        }
    }

    private fun initAdapter() {
        binding.recyclerViewNews.adapter = newsPagingAdapter.withLoadStateFooter(
            LoadStateAdapter { newsPagingAdapter.retry() }
        )

        lifecycleScope.launch {
            newsPagingAdapter.loadStateFlow.collect { loadState ->
                binding.apply {
                    progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading && newsPagingAdapter.itemCount == 0
                    buttonRetry.isVisible =
                        loadState.mediator?.refresh is LoadState.Error && newsPagingAdapter.itemCount == 0

                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.refresh as? LoadState.Error

                    errorState?.let {
                        it.error.message
                        binding.root.snackbar(getString(R.string.error, it.error.message))
                    }
                }
            }
        }
    }

    private fun initSearch(query: String) {
        binding.editTextSearch.setText(query)
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                inputChange()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            newsPagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.recyclerViewNews.scrollToPosition(0) }
        }
    }

    private fun inputChange() {
        binding.editTextSearch.text.trim().let {
            if (it.isNotEmpty()) search(it.toString())
        }
    }

    private fun search(text: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.getAllArticles(text)
                .collectLatest {
                    newsPagingAdapter.submitData(it)
                }
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "Android"
    }
}