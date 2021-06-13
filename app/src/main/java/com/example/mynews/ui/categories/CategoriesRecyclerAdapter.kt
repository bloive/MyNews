package com.example.mynews.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.databinding.ItemCategoryBinding
import com.example.mynews.ui.CategoryClickListener
import java.util.*

class CategoriesRecyclerAdapter(
    private var categories: MutableList<String>,
    private val clickListener: CategoryClickListener
) :
    RecyclerView.Adapter<CategoriesRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesRecyclerAdapter.MyViewHolder {
        return MyViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = categories.size

    inner class MyViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.textView.text = categories[adapterPosition].replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            binding.textView.setOnClickListener {
                clickListener.onCategoryClick(categories[adapterPosition])
            }
        }
    }

}