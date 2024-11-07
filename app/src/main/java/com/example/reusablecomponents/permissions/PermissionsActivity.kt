package com.example.reusablecomponents.permissions

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.permisssions.PermissionHelper
import com.example.permisssions.PermissionHelper.arePermissionsGranted
import com.example.permisssions.PermissionHelper.requestPermissions
import com.example.reusablecomponents.R
import com.example.reusablecomponents.databinding.ActivityPermissionsBinding

class PermissionsActivity : AppCompatActivity() {

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

    lateinit var binding: ActivityPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        askPermissions()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun askPermissions() {
        binding.btnCameraPermission.setOnClickListener {
            requestPermissions(PermissionHelper.PermissionConfig(requireCamera = true), permissionLauncher)
        }
        binding.btnLocationPermission.setOnClickListener {
            requestPermissions(PermissionHelper.PermissionConfig(requireLocation = true), permissionLauncher)
        }
        binding.btnNotificationPermission.setOnClickListener {
            requestPermissions(PermissionHelper.PermissionConfig(requireNotification = true), permissionLauncher)
        }
        binding.btnManageAllFiles.setOnClickListener {
            requestPermissions(PermissionHelper.PermissionConfig(manageExternalStorage = true), permissionLauncher)
        }
        binding.btnMultiplePermissions.setOnClickListener {
            requestNMultiplePermissions()
        }
    }


    private fun requestNMultiplePermissions() {
        binding.apply {
            val config = PermissionHelper.PermissionConfig(
                requireNotification = true,
                requireLocation = true,
                requireCamera = true
            )

            if (arePermissionsGranted(config)) {
                requestPermissions(config, permissionLauncher)
            } else {
                Toast.makeText(
                    this@PermissionsActivity,
                    "All required permissions are already granted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}