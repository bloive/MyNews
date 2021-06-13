package com.example.mynews.ui.bookmarks

import android.os.Bundle
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
import com.example.mynews.databinding.FragmentBookmarksBinding
import com.example.mynews.ui.ClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * This class initializes the recycler for the bookmarked articles and
 * sets click handler to remove from bookmarked database
 */
class BookmarksFragment : Fragment() {

    private lateinit var adapter: BookmarksRecyclerAdapter
    private lateinit var bookmarksViewModel: BookmarksViewModel
    private var _binding: FragmentBookmarksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookmarksViewModel =
            ViewModelProvider(this).get(BookmarksViewModel::class.java)

        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observe()
        bookmarksViewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        bookmarksViewModel.bookmarksLiveData.observe(viewLifecycleOwner, {
            adapter.setArticles(it)
            adapter.notifyDataSetChanged()
        })
    }

    /**
     * Sets clickListeners and adapter parameters to recycler view
     */
    private fun initRecycler() {
        adapter = BookmarksRecyclerAdapter(mutableListOf(), object : ClickListener {
            override fun itemOnClick(article: Article) {
                val bundle = bundleOf(
                    "title" to article.title,
                    "urlToImage" to article.urlToImage,
                    "description" to article.description,
                    "content" to article.content
                )
                findNavController().navigate(
                    R.id.action_navigation_bookmarks_to_detailedFragment,
                    bundle
                )
            }

            /**
             * Removes from bookmarks
             */
            override fun onBookmarkClick(article: Article) {
                val user = FirebaseAuth.getInstance().currentUser?.uid
                FirebaseDatabase.getInstance().getReference("bookmarks").child(user.toString())
                    .child(formatString(article.title.toString())).removeValue()
            }

        })
        binding.bookmarksRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.bookmarksRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    /**
     * Firebase doesn't accept the given symbols
     * which urges us tho format the string before passing
     */
    private fun formatString(title: String): String {
        var str = title
        val list = mutableListOf('.', '#', '$', '[', ']')
        for (c in list) {
            str = str.replace(c.toString(), "")
        }
        return str
    }
}