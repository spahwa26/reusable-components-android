package com.example.reusablecomponents

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.retrofitexample.ui.NetworkActivity
import com.example.adapter.GenericAdapter
import com.example.adapter.ItemComparator
import com.example.adapter.setup
import com.example.mlkitapp.MainActivityMlKit
import com.example.paymentapp.ui.ChoosePaymentGatewayActivity
import com.example.reusablecomponents.databinding.FragmentHomeBinding
import com.example.reusablecomponents.databinding.ItemModuleTypeBinding
import com.example.reusablecomponents.loaders.LoaderActivity
import com.example.reusablecomponents.permissions.PermissionsActivity
import com.example.reusablecomponents.utils.BaseFragment
import com.example.reusablecomponents.utils.ModuleTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    ModuleTypes.Loaders -> {
                        startActivity(Intent(requireContext(), LoaderActivity::class.java))
                    }
                    ModuleTypes.MlKit -> {
                        startActivity(Intent(requireContext(), MainActivityMlKit::class.java))
                    }

                    ModuleTypes.Payments -> {
                        startActivity(
                            Intent(
                                requireContext(),
                                ChoosePaymentGatewayActivity::class.java
                            )
                        )
                    }

                    ModuleTypes.MediaPicker -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectMediaFragment())
                    }

                    ModuleTypes.TabsExample -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTabExampleFragment())
                    }

                    ModuleTypes.Permissions -> {
                        requireContext().startActivity(Intent(requireContext(), PermissionsActivity::class.java))
                    }

                    ModuleTypes.SimpleList -> {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSimpleListFragment())
                    }

                    ModuleTypes.Network -> {
                        startActivity(Intent(requireContext(), NetworkActivity::class.java))
                    }

                    else -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage("${moduleType.name} module is not implemented yet.")
                            .setPositiveButton(
                                "ok"
                            ) { dialog, which -> dialog.dismiss() }
                            .show()
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