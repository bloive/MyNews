package com.example.mynews.ui.categories

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
import com.example.mynews.databinding.FragmentCategoriesBinding
import com.example.mynews.ui.CategoryClickListener

/**
 * This class shows the categories which we query in the database and initializes its recyclerview
 */
class CategoriesFragment : Fragment() {

    private lateinit var categoriesViewModel: CategoriesViewModel
    private var _binding: FragmentCategoriesBinding? = null
    private lateinit var adapter: CategoriesRecyclerAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesViewModel =
            ViewModelProvider(this).get(CategoriesViewModel::class.java)

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val categories = resources.getStringArray(R.array.categories)
        adapter =
            CategoriesRecyclerAdapter(categories.toMutableList(), object : CategoryClickListener {

                override fun onCategoryClick(category: String) {
                    val bundle = bundleOf("category" to category)
                    findNavController().navigate(
                        R.id.action_navigation_categories_to_chosenCategoryFragment,
                        bundle
                    )
                }

            })
        binding.categoryRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.categoryRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}