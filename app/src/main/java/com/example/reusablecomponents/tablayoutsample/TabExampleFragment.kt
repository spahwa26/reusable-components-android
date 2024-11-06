package com.example.reusablecomponents.tablayoutsample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.permissions.PermissionHelper
import com.example.permissions.PermissionHelper.arePermissionsGranted
import com.example.permissions.PermissionHelper.requestPermissions
import com.example.reusablecomponents.databinding.FragmentTabExampleBinding
import com.example.reusablecomponents.utils.BaseFragment
import com.example.viewpageradapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TabExampleFragment : BaseFragment<FragmentTabExampleBinding>() {
    private val permissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.entries.forEach { entry ->
                val permissionName = entry.key
                val isGranted = entry.value
                if (isGranted) {
                    Log.d("permissionName", "$permissionName granted")
                } else {
                    Log.d("permissionName", "$permissionName denied")
                }
            }
        }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTabExampleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestNecessaryPermissions()
        setUpViewPager()
    }


    private fun requestNecessaryPermissions() = requireBinding {

        val config = PermissionHelper.PermissionConfig(
            requireStorage = true,
            manageExternalStorage = true,
            requireNotification = true,
            requireMicrophone = true,
            requireCamera = true

        )

        if (!requireContext().arePermissionsGranted(config)) {
            requireContext().requestPermissions(config, permissionLauncher)
        } else {
            Toast.makeText(
                requireContext(),
                "All required permissions are already granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpViewPager() = requireBinding {

        val adapter = ViewPagerAdapter(this@TabExampleFragment)
        adapter.addFragment(First(), "First")
        adapter.addFragment(Second(), "Second")

        viewPager2.adapter = adapter
        TabLayoutMediator(
            tabLayout,
            viewPager2
        ) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}