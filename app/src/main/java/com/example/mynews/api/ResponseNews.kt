package com.example.mynews.api

data class ResponseNews(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)

data class Article(
//    val source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
//    val url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
)
//
//data class Source(
//    val id: String?,
//    val name: String?
//)