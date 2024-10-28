package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bindItem: (VB, T, Int) -> Unit,
    private val itemComparator: ItemComparator<T>
) : RecyclerView.Adapter<GenericAdapter.ViewHolder<VB>>() {

    private val items = mutableListOf<T>()
    private var onItemClickListener: ((T, Int) -> Unit)? = null

    class ViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val binding = bindingInflater(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        val item = items[position]
        bindItem(holder.binding, item, position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item, position)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<T>) {
        val diffCallback = GenericDiffCallback(items, newItems, itemComparator)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: (T, Int) -> Unit) {
        onItemClickListener = listener
    }

    fun getItem(position: Int): T = items[position]
}

interface ItemComparator<T> {
    fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}


private class GenericDiffCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemComparator: ItemComparator<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemComparator.areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemComparator.areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
}

// Extension function for RecyclerView setup with ViewBinding
fun <T, VB : ViewBinding> RecyclerView.setup(
    initialItems: List<T>? = null,
    bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    bindItem: (VB, T, Int) -> Unit,
    itemComparator: ItemComparator<T>,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    onItemClick: ((T, Int) -> Unit)? = null
): GenericAdapter<T, VB> {

    val adapter = GenericAdapter(
        bindingInflater = bindingInflater,
        bindItem = bindItem,
        itemComparator = itemComparator
    )

    this.layoutManager = layoutManager
    this.adapter = adapter

    itemDecoration?.let {
        addItemDecoration(it)
    }

    onItemClick?.let {
        adapter.setOnItemClickListener(it)
    }

    initialItems?.let {
        adapter.updateItems(it)
    }

    return adapter
}