package com.niyazi.news.io.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niyazi.news.model.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM article")
    fun getAllArticles(): Flow<Article>

    @Query("SELECT * FROM article WHERE title LIKE :query OR description LIKE :query")
    fun getAllArticlesByQuery(query: String): PagingSource<Int, Article>

    @Query("DELETE FROM article")
    suspend fun clearArticles()
}