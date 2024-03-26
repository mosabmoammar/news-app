package com.m.newsapp.di

import android.app.Application
import androidx.room.Room
import com.m.newsapp.data.local.NewsDao
import com.m.newsapp.data.local.NewsDatabase
import com.m.newsapp.data.local.NewsTypeConvertor
import com.m.newsapp.data.manger.LocalUserMangerImpl
import com.m.newsapp.data.remote.NewsApi
import com.m.newsapp.data.repository.NewsRepositoryImpl
import com.m.newsapp.domain.manger.LocalUserManger
import com.m.newsapp.domain.repository.NewsRepository
import com.m.newsapp.domain.usecases.appentry.AppEntryUseCases
import com.m.newsapp.domain.usecases.appentry.ReadAppEntry
import com.m.newsapp.domain.usecases.appentry.SaveAppEntry
import com.m.newsapp.domain.usecases.news.DeleteArticle
import com.m.newsapp.domain.usecases.news.GetNews
import com.m.newsapp.domain.usecases.news.NewsUseCases
import com.m.newsapp.domain.usecases.news.SearchNews
import com.m.newsapp.domain.usecases.news.SelectArticle
import com.m.newsapp.domain.usecases.news.SelectArticles
import com.m.newsapp.domain.usecases.news.UpsertArticle
import com.m.newsapp.util.Constants.BASE_URL
import com.m.newsapp.util.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserMangerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi,newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao


}