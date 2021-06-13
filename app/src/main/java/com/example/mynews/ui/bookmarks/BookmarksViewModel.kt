package com.example.mynews.ui.bookmarks

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.api.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarksViewModel : ViewModel() {
    private var firebaseDatabase = FirebaseDatabase.getInstance()

    private val _bookmarksLiveData = MutableLiveData<MutableList<Article>>().apply {
        mutableListOf<Article>()
    }
    val bookmarksLiveData: MutableLiveData<MutableList<Article>> = _bookmarksLiveData

    fun init() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getBookmarks()
            }
        }
    }

    /**
     * Retrieves bookmarked articles from firebase
     */
    private fun getBookmarks() {
        val articles = mutableListOf<Article>()
        firebaseDatabase.getReference("bookmarks")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    articles.clear()
                    for (s in snapshot.children) {
                        var article = Article(
                            s.child("author").value.toString(),
                            s.child("title").value.toString(),
                            s.child("description").value.toString(),
                            s.child("urlToImage").value.toString(),
                            s.child("publishedAt").value.toString(),
                            s.child("content").value.toString()
                        )
                        articles.add(article)
                    }
                    bookmarksLiveData.postValue(articles)
                    d("articles", articles.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    d("cancelled: ", error.message)
                }
            })
    }
}