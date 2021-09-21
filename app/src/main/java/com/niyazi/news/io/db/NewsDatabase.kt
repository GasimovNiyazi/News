package com.niyazi.news.io.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.niyazi.news.io.db.converters.SourceConverter
import com.niyazi.news.io.db.dao.ArticleDao
import com.niyazi.news.io.db.dao.RemoteKeysDao
import com.niyazi.news.model.Article
import com.niyazi.news.model.RemoteKeys

@Database(entities = arrayOf(Article::class, RemoteKeys::class), version = 1, exportSchema = false)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}