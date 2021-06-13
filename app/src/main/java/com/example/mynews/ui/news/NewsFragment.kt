package com.example.mynews.ui.news

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynews.R
import com.example.mynews.api.Article
import com.example.mynews.databinding.FragmentNewsBinding
import com.example.mynews.ui.ClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * This class shows us top articles without any category filters,
 * initializes its recyclerview and lets us save them in bookmarks
 */
class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentNewsBinding? = null
    private lateinit var adapter: NewsRecyclerAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel =
            ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observe()
        newsViewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        adapter = NewsRecyclerAdapter(mutableListOf(), object : ClickListener{
            override fun itemOnClick(article: Article) {
                val bundle = bundleOf("title" to article.title, "urlToImage" to article.urlToImage, "description" to article.description, "content" to article.content)
                findNavController().navigate(R.id.action_navigation_news_to_detailedFragment, bundle)
            }

            override fun onBookmarkClick(article: Article) {
                val user = FirebaseAuth.getInstance().currentUser?.uid
                FirebaseDatabase.getInstance().getReference("bookmarks").child(user.toString()).child(formatString(article.title.toString())).setValue(article)
            }

        })
        binding.newsRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.newsRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun observe() {
        //TODO
        newsViewModel.newsLiveData.observe(viewLifecycleOwner, {
            adapter.setArticles(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun formatString(title: String): String {
        var str = title
        val list = mutableListOf<Char>('.', '#', '$', '[', ']')
        for (c in list) {
            str = str.replace(c.toString(), "")
        }
        return str
    }
}