package com.example.mynews.ui.chosen_category

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
import com.example.mynews.databinding.FragmentChosenCategoryBinding
import com.example.mynews.ui.ClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * This class shows us the articles which we have queried
 * from the categories, initializes its recyclerview and lets us save them in bookmarks
 */
class ChosenCategoryFragment : Fragment() {

    private lateinit var binding: FragmentChosenCategoryBinding
    private lateinit var adapter: ChosenCategoryRecyclerAdapter
    private lateinit var chosenViewModel: ChosenCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chosenViewModel =
            ViewModelProvider(this).get(ChosenCategoryViewModel::class.java)
        binding = FragmentChosenCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.get("category")

        initRecycler()
        observe()
        chosenViewModel.init(category.toString())


    }

    private fun initRecycler() {
        adapter = ChosenCategoryRecyclerAdapter(mutableListOf(), object : ClickListener {
            override fun itemOnClick(article: Article) {
                val bundle = bundleOf(
                    "title" to article.title,
                    "urlToImage" to article.urlToImage,
                    "description" to article.description,
                    "content" to article.content
                )
                findNavController().navigate(
                    R.id.action_chosenCategoryFragment_to_detailedFragment,
                    bundle
                )
            }

            override fun onBookmarkClick(article: Article) {
                val user = FirebaseAuth.getInstance().currentUser?.uid
                FirebaseDatabase.getInstance().getReference("bookmarks").child(user.toString())
                    .child(formatString(article.title.toString())).setValue(article)
            }
        })
        binding.chosenCategoryrecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.chosenCategoryrecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun observe() {
        //TODO
        chosenViewModel.categorizedNewsLiveData.observe(viewLifecycleOwner, {
            adapter.setArticles(it)
            adapter.notifyDataSetChanged()
        })
    }

    /**
     * Firebase doesn't accept the given symbols
     * which urges us tho format the string before passing
     */
    private fun formatString(title: String): String {
        var str = title
        val list = mutableListOf<Char>('.', '#', '$', '[', ']')
        for (c in list) {
            str = str.replace(c.toString(), "")
        }
        return str
    }

}