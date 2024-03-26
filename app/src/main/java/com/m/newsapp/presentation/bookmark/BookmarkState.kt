package com.m.newsapp.presentation.bookmark

import com.m.newsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
