package com.niyazi.news.io.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.niyazi.news.io.db.NewsDatabase
import com.niyazi.news.io.network.NewsServices
import com.niyazi.news.io.source.NewsRemoteMediator
import com.niyazi.news.model.Article
import com.niyazi.news.model.ArticleRequestMap
import com.niyazi.news.utils.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {

    fun getAllArticles(parameters: Map<String, Any>): Flow<PagingData<Article>>

}

class NewsRepositoryImpl @Inject constructor(
    private val newsServices: NewsServices,
    private val newsDatabase: NewsDatabase
) : NewsRepository {

    @ExperimentalPagingApi
    override fun getAllArticles(parameters: Map<String, Any>): Flow<PagingData<Article>> {
        val query = "$PERCENT_SIGN${
            parameters[ArticleRequestMap.QUERY].toString().replace(EMPTY, PERCENT_SIGN)
        }$PERCENT_SIGN"

        return Pager(
            config = PagingConfig(
                PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = NewsRemoteMediator(
                newsServices,
                newsDatabase,
                parameters.toMutableMap()
            ),
            pagingSourceFactory = {
                newsDatabase.articleDao().getAllArticlesByQuery(query)
            }
        ).flow
    }

    companion object{
        private const val PERCENT_SIGN = '%'
        private const val EMPTY = ' '
    }

}
