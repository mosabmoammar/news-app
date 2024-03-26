package com.m.newsapp.data.remote.datatransferobject

import com.m.newsapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)