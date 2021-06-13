package com.example.mynews.ui

import com.example.mynews.api.Article

interface ClickListener {

    fun itemOnClick(article: Article)
    fun onBookmarkClick(article: Article)
}

interface CategoryClickListener {
    fun onCategoryClick(category: String)
}