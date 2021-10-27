package com.oguzhanturkmen.newsapp.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.newsapp.models.Article
import com.oguzhanturkmen.newsapp.models.NewsResponse
import com.oguzhanturkmen.newsapp.repository.NewsRepository
import com.oguzhanturkmen.newsapp.util.Resourcee
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(
    val newsRepository: NewsRepository
    ) :ViewModel() {
    val breakingNews: MutableLiveData<Resourcee<NewsResponse>> = MutableLiveData()
    var breakingNewsPage= 1
    var breakingNewsResponse : NewsResponse? = null

    val searchNews: MutableLiveData<Resourcee<NewsResponse>> = MutableLiveData()
    var searchNewsPage= 1
    var searchNewsResponse : NewsResponse? = null


    init {
        getBreakingNews("tr")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
            breakingNews.postValue(Resourcee.Loading())
            val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
            breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun searchNews(searchQuery : String) = viewModelScope.launch{
        searchNews.postValue(Resourcee.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    private fun  handleBreakingNewsResponse(response: Response<NewsResponse>) : Resourcee<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resourcee.Success(breakingNewsResponse?: resultResponse)
            }
        }
        return  Resourcee.Error(response.message())
    }
    private fun  handleSearchNewsResponse(response: Response<NewsResponse>) : Resourcee<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = resultResponse
                }else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resourcee.Success(searchNewsResponse?: resultResponse)
            }
        }
        return  Resourcee.Error(response.message())
    }
    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}




