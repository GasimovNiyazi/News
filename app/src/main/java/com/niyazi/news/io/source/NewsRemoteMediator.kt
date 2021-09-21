package com.niyazi.news.io.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.google.gson.Gson
import com.niyazi.news.io.db.NewsDatabase
import com.niyazi.news.io.network.NewsServices
import com.niyazi.news.model.Article
import com.niyazi.news.model.ArticleRequestMap
import com.niyazi.news.model.ErrorResponse
import com.niyazi.news.model.RemoteKeys

private const val NEWS_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class NewsRemoteMediator(
    private val newsServices: NewsServices,
    private val newsDatabase: NewsDatabase,
    private val paramsMap: MutableMap<String, Any>
) : RemoteMediator<Int, Article>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.previousKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: NEWS_STARTING_PAGE_INDEX
            }
        }
        paramsMap[ArticleRequestMap.PAGE] = page
        paramsMap[ArticleRequestMap.PAGE_SIZE] = state.config.pageSize

        return try {
            val response = newsServices.getAllArticles(paramsMap)
            if (response.body() != null) {
                val articles = response.body()?.articles
                articles?.let {
                    val endOfPaginationReached = articles.isEmpty()
                    newsDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            newsDatabase.remoteKeysDao().clearRemoteKeys()
                            newsDatabase.articleDao().clearArticles()
                        }

                        val prevKey = if (page == NEWS_STARTING_PAGE_INDEX) null else page.minus(1)
                        val nextKey = if (endOfPaginationReached) null else page.plus(1)
                        val keys = articles.map {
                            RemoteKeys(id = it.title, previousKey = prevKey, nextKey = nextKey)
                        }
                        newsDatabase.remoteKeysDao().insertAll(keys)
                        newsDatabase.articleDao().insertArticles(articles)
                    }
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                } ?: kotlin.run {
                    MediatorResult.Error(Throwable("Something went wrong"))
                }
            } else {
                val errorBody =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                MediatorResult.Error(Throwable(errorBody.errorMessage))
            }
        } catch (throwable: Throwable) {
            MediatorResult.Error(throwable)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                newsDatabase.remoteKeysDao().remoteKeysId(article.title)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                newsDatabase.remoteKeysDao().remoteKeysId(article.title)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { articleId ->
                newsDatabase.remoteKeysDao().remoteKeysId(articleId)
            }
        }
    }
}