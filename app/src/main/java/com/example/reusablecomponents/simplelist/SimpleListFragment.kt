package com.example.reusablecomponents.simplelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.adapter.GenericAdapter
import com.example.adapter.ItemComparator
import com.example.adapter.setup
import com.example.reusablecomponents.databinding.FragmentSimpleListBinding
import com.example.reusablecomponents.databinding.ItemViewBinding
import com.example.reusablecomponents.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleListFragment : BaseFragment<FragmentSimpleListBinding>() {
    private lateinit var listAdapter: GenericAdapter<ListData, ItemViewBinding>
    private var list: ArrayList<ListData> = ArrayList()

    data class ListData(val name: String?)

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSimpleListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() = requireBinding {
        list.apply {
            add(ListData("Item 1"))
            add(ListData("Item 2"))
            add(ListData("Item 3"))
            add(ListData("Item 4"))
            add(ListData("Item 5"))
            add(ListData("Item 6"))
            add(ListData("Item 7"))
            add(ListData("Item 8"))
            add(ListData("Item 9"))
            add(ListData("Item 10"))
            add(ListData("Item 11"))
            add(ListData("Item 12"))
            add(ListData("Item 13"))
            add(ListData("Item 14"))
            add(ListData("Item 15"))
            add(ListData("Item 16"))
        }
        listAdapter = rvModules.setup(
            initialItems = list,
            bindingInflater = ItemViewBinding::inflate,
            itemComparator = UserComparator(),
            bindItem = { itemBinding, listData, position ->
                itemBinding.name.text=listData.name
            },
            onItemClick = { item, position ->
                Toast.makeText(requireContext(), "${item.name} clicked!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    class UserComparator : ItemComparator<ListData> {
        override fun areItemsTheSame(oldItem: ListData, newItem: ListData): Boolean {
            val result = oldItem.name == newItem.name
            Log.d(
                "UserComparator",
                "areItemsTheSame: old=${oldItem.name}, new=${newItem.name}, result=$result"
            )
            return result
        }

        override fun areContentsTheSame(oldItem: ListData, newItem: ListData): Boolean {
            val result = oldItem == newItem
            Log.d(
                "UserComparator",
                "areContentsTheSame: old=${oldItem.name}, new=${newItem.name}, result=$result"
            )
            return result
        }
    }
}