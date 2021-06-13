package com.example.mynews.ui.news

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel : ViewModel() {

    private val _newsLiveData = MutableLiveData<MutableList<Article>>().apply {
        mutableListOf<Article>()
    }
    val newsLiveData: MutableLiveData<MutableList<Article>> = _newsLiveData

    fun init() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNews()
            }
        }
    }

    private suspend fun getNews() {
        val response = ApiUtil.apiService().getAllArticles()
        if (response.isSuccessful) {
            val news = response.body()
            _newsLiveData.postValue(news!!.articles!!.toMutableList())
        } else {
            response.code()
            d("Response", response.toString())
        }
    }
}