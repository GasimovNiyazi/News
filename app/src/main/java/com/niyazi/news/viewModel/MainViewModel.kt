package com.niyazi.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.niyazi.news.io.repository.NewsRepository
import com.niyazi.news.model.Article
import com.niyazi.news.model.ArticleRequestMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    fun getAllArticles(query: String): Flow<PagingData<Article>> =
        newsRepository.getAllArticles(mapOf(ArticleRequestMap.QUERY to query)).cachedIn(viewModelScope)

}