package com.example.mynews.ui.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.api.ApiUtil
import com.example.mynews.api.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel : ViewModel() {

    private val _categorizedNewsLiveData = MutableLiveData<MutableList<Article>>().apply {
        mutableListOf<Article>()
    }
    val categorizedNewsLiveData: MutableLiveData<MutableList<Article>> = _categorizedNewsLiveData

    var category = ""

    fun init() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getCategories()
            }
        }
    }

    private suspend fun getCategories() {
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