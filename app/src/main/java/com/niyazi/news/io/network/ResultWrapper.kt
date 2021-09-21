package com.niyazi.news.io.network

import com.niyazi.news.model.ArticleResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val value: ArticleResponse) : ResultWrapper<Nothing>()
    data class NetworkError(val errorMessage: String) : ResultWrapper<Nothing>()
}