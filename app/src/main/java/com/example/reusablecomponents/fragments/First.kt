package com.example.reusablecomponents.fragments

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.adapter.GenericAdapter
import com.example.adapter.ItemComparator
import com.example.adapter.setup
import com.example.permissions.PermissionHelper
import com.example.permissions.PermissionHelper.checkAndShowSettingsDialogForPermission
import com.example.reusablecomponents.databinding.FragmentFirstBinding
import com.example.reusablecomponents.databinding.ItemViewBinding


class First : Fragment() {
    private lateinit var fragmentFirstBinding:FragmentFirstBinding
    private var list: ArrayList<NameData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFirstBinding= FragmentFirstBinding.inflate(inflater,container,false)
        return fragmentFirstBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.apply {
            add(NameData("ARSHPREET"))
            add(NameData("NICKELFOX"))
        }
        val adapter = fragmentFirstBinding.recyclerView.setup(
            initialItems = list,
            bindingInflater = ItemViewBinding::inflate,
            itemComparator = UserComparator(),
            bindItem = { itemBinding, nameData, position ->
                itemBinding.name.text = nameData.name
            },
            onItemClick = { nameData, position ->
                requireContext().checkAndShowSettingsDialogForPermission(Manifest.permission.CAMERA, PermissionHelper.PermissionType.CAMERA)
            }
        )

        // Test the comparator with different scenarios
        testComparator(adapter)
        super.onViewCreated(view, savedInstanceState)
    }
    private fun testComparator(adapter: GenericAdapter<NameData, ItemViewBinding>) {
        // Test 1: Update existing item
        Handler(Looper.getMainLooper()).postDelayed({
            val updatedList = ArrayList<NameData>().apply {
                add(NameData("ARSHPREET UPDATED"))  // Changed name
                add(NameData("NICKELFOX"))
            }
            Log.d("ComparatorTest", "Test 1: Updating ARSHPREET to ARSHPREET UPDATED")
            adapter.updateItems(updatedList)
        }, 2000)

        // Test 2: Add new item
        Handler(Looper.getMainLooper()).postDelayed({
            val updatedList = ArrayList<NameData>().apply {
                add(NameData("ARSHPREET UPDATED"))
                add(NameData("NICKELFOX"))
                add(NameData("NEW PERSON"))  // New item
            }
            Log.d("ComparatorTest", "Test 2: Adding NEW PERSON")
            adapter.updateItems(updatedList)
        }, 4000)

        // Test 3: Remove item
        Handler(Looper.getMainLooper()).postDelayed({
            val updatedList = ArrayList<NameData>().apply {
                add(NameData("ARSHPREET UPDATED"))
                add(NameData("NEW PERSON"))
                // Removed NICKELFOX
            }
            Log.d("ComparatorTest", "Test 3: Removing NICKELFOX")
            adapter.updateItems(updatedList)
        }, 6000)
    }

    data class NameData(val name: String?)

    class UserComparator : ItemComparator<NameData> {
        override fun areItemsTheSame(oldItem: NameData, newItem: NameData): Boolean {
            val result = oldItem.name == newItem.name
            Log.d("UserComparator", "areItemsTheSame: old=${oldItem.name}, new=${newItem.name}, result=$result")
            return result
        }

        override fun areContentsTheSame(oldItem: NameData, newItem: NameData): Boolean {
            val result = oldItem == newItem
            Log.d("UserComparator", "areContentsTheSame: old=${oldItem.name}, new=${newItem.name}, result=$result")
            return result
        }
    }
}