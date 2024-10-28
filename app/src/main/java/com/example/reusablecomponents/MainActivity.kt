package com.example.reusablecomponents

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.permissions.PermissionHelper
import com.example.permissions.PermissionHelper.arePermissionsGranted
import com.example.permissions.PermissionHelper.requestPermissions
import com.example.reusablecomponents.databinding.ActivityMainBinding
import com.example.reusablecomponents.fragments.First
import com.example.reusablecomponents.fragments.Second
import com.example.viewpageradapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
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

        requestNecessaryPermissions()
        setUpViewPager()
    }

    private fun requestNecessaryPermissions() {

        val config = PermissionHelper.PermissionConfig(
            requireStorage = true,
            manageExternalStorage = true,
            requireNotification = true,
            requireMicrophone = true,
            requireCamera = true

        )

        if (!arePermissionsGranted(config)) {
          requestPermissions(config, permissionLauncher)
        } else {
            Toast.makeText(this, "All required permissions are already granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpViewPager() {

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(First(), "First")
        adapter.addFragment(Second(), "Second")

        activityMainBinding.viewPager2.adapter = adapter
        TabLayoutMediator(
            activityMainBinding.tabLayout,
            activityMainBinding.viewPager2
        ) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }

}