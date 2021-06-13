package com.example.mynews

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mynews.databinding.FragmentDetailedBinding

/**
 * This class opens the detailed article fragment
 */
class DetailedFragment : Fragment() {

    private lateinit var binding : FragmentDetailedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.get("title")
        val urlToImage = arguments?.get("urlToImage")
        val description = arguments?.get("description")
        val content = arguments?.get("content")

        binding.tvTitleDetailed.text = title.toString()
        binding.tvContentDetailed.text = content.toString()
        binding.tvDescriptionDetailed.text = description.toString()

        Glide.with(binding.imageView.context).load(urlToImage).into(binding.imageView)
    }

}