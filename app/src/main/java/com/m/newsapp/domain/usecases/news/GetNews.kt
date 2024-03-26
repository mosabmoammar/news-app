package com.m.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.m.newsapp.domain.model.Article
import com.m.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Article>>{
        return newsRepository.getNews(sources = sources)
    }

}