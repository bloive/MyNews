package com.example.mynews.ui.chosen_category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.api.ApiUtil
import com.example.mynews.api.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChosenCategoryViewModel : ViewModel() {
    private val _categorizedNewsLiveData = MutableLiveData<MutableList<Article>>().apply {
        mutableListOf<Article>()
    }
    val categorizedNewsLiveData: MutableLiveData<MutableList<Article>> = _categorizedNewsLiveData

    fun init(category: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getNews(category)
            }
        }
    }

    private suspend fun getNews(category: String) {
        val response = ApiUtil.apiService().getArticlesByCategory(category)
        if (response.isSuccessful) {
            val news = response.body()
            _categorizedNewsLiveData.postValue(news!!.articles!!.toMutableList())
        } else {
            response.code()
            Log.d("Response", response.toString())
        }
    }
}