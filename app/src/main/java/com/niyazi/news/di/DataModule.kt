package com.niyazi.news.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.niyazi.news.io.db.NewsDatabase
import com.niyazi.news.io.db.converters.SourceConverter
import com.niyazi.news.io.db.dao.ArticleDao
import com.niyazi.news.io.network.HeaderInterceptor
import com.niyazi.news.io.network.NewsServices
import com.niyazi.news.utils.BASE_URL
import com.niyazi.news.utils.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesNewsServices(
        retrofit: Retrofit
    ): NewsServices {
        return retrofit.create(NewsServices::class.java)
    }


    @Singleton
    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    @AuthInterceptor
    fun provideHeaderInterceptor(): Interceptor = HeaderInterceptor()

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @LoggingInterceptor logging: Interceptor,
        @AuthInterceptor headerInterceptor: Interceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)
        okHttpClient.addInterceptor(headerInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesGson() = Gson()


    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        gson: Gson,
        sourceConverter: SourceConverter = SourceConverter(gson)
    ): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DB_NAME
        ).addTypeConverter(sourceConverter)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase): ArticleDao = newsDatabase.articleDao()


}