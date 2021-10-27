package com.oguzhanturkmen.newsapp.repository

import com.oguzhanturkmen.newsapp.API.RetrofitInstance
import com.oguzhanturkmen.newsapp.database.ArticleDatabase
import com.oguzhanturkmen.newsapp.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countyCode: String, pageNumber: Int)=
        RetrofitInstance.api.getBreakingNews(countyCode,pageNumber)

    suspend fun  searchNews(searchQery:String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQery,pageNumber)

    suspend fun  upsert (article : Article ) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun  deleteArticle(article: Article) = db.getArticleDao().deleteArtcile(article)
}