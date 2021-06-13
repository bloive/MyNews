package com.example.mynews.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.api.Article
import com.example.mynews.databinding.ItemNewsWithImageBinding
import com.example.mynews.databinding.ItemViewTextOnlyBinding
import com.example.mynews.ui.ClickListener

class NewsRecyclerAdapter(private var articles: MutableList<Article>, private val clickListener: ClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TEXT = 0
        private const val ITEM_IMAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TEXT) {
            TextViewHolder(
                ItemViewTextOnlyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        } else {
            ImageViewHolder(
                ItemNewsWithImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun setArticles(articles: MutableList<Article>) {
        this.articles = articles;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind()
            is ImageViewHolder -> holder.bind()
        }
    }

    override fun getItemCount() = articles.size

    override fun getItemViewType(position: Int): Int {
        val model = articles[position]
        return if (model.urlToImage == null)
            ITEM_TEXT
        else
            ITEM_IMAGE
    }



    inner class TextViewHolder(private val binding: ItemViewTextOnlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Article
        fun bind() {
            model = articles[adapterPosition]
            binding.tvTitle.text = model.title
            binding.tvDescription.text = model.description
            binding.bookmarkSign.setOnClickListener {
                clickListener.onBookmarkClick(model)
            }
            binding.tvTitle.setOnClickListener {
                clickListener.itemOnClick(model)
            }
            binding.tvDescription.setOnClickListener {
                clickListener.itemOnClick(model)
            }
        }
    }

    inner class ImageViewHolder(private val binding: ItemNewsWithImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Article
        fun bind() {
            model = articles[adapterPosition]
            binding.tvTitle.text = model.title
            binding.tvDescription.text = model.description
            Glide.with(binding.ibNewsImage.context).load(model.urlToImage).into(binding.ibNewsImage)
            binding.bookmarkSign.setOnClickListener {
                clickListener.onBookmarkClick(model)
            }
            binding.tvTitle.setOnClickListener {
                clickListener.itemOnClick(model)
            }
            binding.tvDescription.setOnClickListener {
                clickListener.itemOnClick(model)
            }

            binding.ibNewsImage.setOnClickListener {
                clickListener.itemOnClick(model)
            }
        }
    }
}