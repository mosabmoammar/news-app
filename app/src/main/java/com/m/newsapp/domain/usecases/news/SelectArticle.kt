package com.m.newsapp.domain.usecases.news

import com.m.newsapp.domain.model.Article
import com.m.newsapp.domain.repository.NewsRepository

class SelectArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(url: String): Article?{
        return newsRepository.selectArticle(url)
    }

}