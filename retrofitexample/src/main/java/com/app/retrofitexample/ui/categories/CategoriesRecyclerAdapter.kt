package com.app.retrofitexample.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.retrofitexample.R
import com.bumptech.glide.Glide
import com.app.retrofitexample.data.models.Categories
import com.app.retrofitexample.databinding.ListItemCategoryBinding

class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder>() {

    private var list = mutableListOf<Categories>()
    private var listener: InteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    fun serInteractionListener(interactionListener: InteractionListener?) {
        listener = interactionListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    fun addAll(list: List<Categories>?) {
        list?.let {
            this.list = it.toMutableList()
        }
    }

    inner class ViewHolder(private val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categories: Categories, position: Int) {
            binding.tvText.text = itemView.context.getString(R.string.sample_text, position)
            Glide.with(binding.ivImage)
                .load(categories.imageUrl)
                .placeholder(R.drawable.placeholder_icon)
                .into(binding.ivImage)
            itemView.setOnClickListener { listener?.onCategoryClick(categories) }
        }
    }

    interface InteractionListener {
        fun onCategoryClick(category: Categories)
    }
}
