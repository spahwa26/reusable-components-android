package com.example.reusablecomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adapter.GenericAdapter
import com.example.adapter.ItemComparator
import com.example.adapter.setup
import com.example.reusablecomponents.databinding.FragmentHomeBinding
import com.example.reusablecomponents.databinding.ItemModuleTypeBinding
import com.example.reusablecomponents.utils.BaseFragment
import com.example.reusablecomponents.utils.ModuleTypes

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var listAdapter: GenericAdapter<ModuleTypes, ItemModuleTypeBinding>

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() = requireBinding {
        listAdapter = rvModules.setup(
            initialItems = ModuleTypes.entries.toList(),
            bindingInflater = ItemModuleTypeBinding::inflate,
            itemComparator = ModuleComparator(),
            bindItem = { binding, item, position ->
                binding.tvModuleName.text = item.name
            },
            onItemClick = { moduleType, position ->
                when (moduleType) {
                    ModuleTypes.MediaPicker -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectMediaFragment())
                    }

                    else -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectMediaFragment())
                    }
                }
            })
    }

    class ModuleComparator : ItemComparator<ModuleTypes> {
        override fun areItemsTheSame(oldItem: ModuleTypes, newItem: ModuleTypes): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ModuleTypes, newItem: ModuleTypes): Boolean {
            return oldItem.name == newItem.name
        }
    }
}