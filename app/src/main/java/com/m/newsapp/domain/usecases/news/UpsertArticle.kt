package com.m.newsapp.domain.usecases.news

import com.m.newsapp.domain.model.Article
import com.m.newsapp.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article){
        newsRepository.upsertArticle(article)
    }

}